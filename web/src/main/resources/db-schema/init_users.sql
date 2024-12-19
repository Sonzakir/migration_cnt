-- https://aws.amazon.com/de/blogs/database/managing-postgresql-users-and-roles/
DO
$$
BEGIN
        IF NOT EXISTS(SELECT * FROM pg_catalog.pg_roles WHERE rolname = '$DB_APP_ADMIN_USER') THEN
            CREATE USER $DB_APP_ADMIN_USER WITH PASSWORD '$DB_APP_ADMIN_PASS';
            GRANT CONNECT ON DATABASE postgres TO $DB_APP_ADMIN_USER;
            GRANT USAGE, CREATE ON SCHEMA $SCHEMA TO $DB_APP_ADMIN_USER;
            GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA $SCHEMA TO $DB_APP_ADMIN_USER;
ALTER DEFAULT PRIVILEGES IN SCHEMA $SCHEMA GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO $DB_APP_ADMIN_USER;
GRANT USAGE ON ALL SEQUENCES IN SCHEMA $SCHEMA TO $DB_APP_ADMIN_USER;
            ALTER DEFAULT PRIVILEGES IN SCHEMA $SCHEMA GRANT USAGE ON SEQUENCES TO $DB_APP_ADMIN_USER;
END IF;

        IF NOT EXISTS(SELECT * FROM pg_catalog.pg_roles WHERE rolname = '$DB_APP_USER') THEN
            CREATE USER $DB_APP_USER WITH PASSWORD '$DB_APP_PASS';
            GRANT CONNECT ON DATABASE postgres TO $DB_APP_USER;
            GRANT USAGE ON SCHEMA $SCHEMA TO $DB_APP_USER;
            -- Hier kein ALTER DEFAULT PRIVILEGES, weil das für diesen User keinen Effekt hat - siehe update_users.sql
            GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA $SCHEMA TO $DB_APP_USER;
GRANT USAGE ON ALL SEQUENCES IN SCHEMA $SCHEMA TO $DB_APP_USER;
END IF;
END
$$;


-- Falls das Script auf einem bereits existenten Schema ausgeführt wird, muss der Owner der bereits existenten Tabellen
-- auf den DB_APP_ADMIN_USER geändert werden, damit dieser später DDL wie ALTER Table etc. durchführen darf.
DO
$$
    DECLARE
existing_tables CURSOR FOR select tablename from pg_catalog.pg_tables
                           where schemaname = '$SCHEMA'
                             and tableowner <> '$DB_APP_ADMIN_USER';
BEGIN
FOR existing_table IN existing_tables LOOP
            EXECUTE format('ALTER TABLE $SCHEMA.%I OWNER TO $DB_APP_ADMIN_USER', existing_table.tablename);
END LOOP;
END;
$$;

-- Dasselbe für die Sequences
DO
$$
DECLARE
    existing_sequences CURSOR FOR select sequencename from pg_catalog.pg_sequences
                              where schemaname = '$SCHEMA'
                                and sequenceowner <> '$DB_APP_ADMIN_USER';
BEGIN
FOR existing_sequence IN existing_sequences LOOP
            EXECUTE format('ALTER SEQUENCE $SCHEMA.%I OWNER TO $DB_APP_ADMIN_USER', existing_sequence.sequencename);
END LOOP;
END;
$$;