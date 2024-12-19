#!/bin/bash

# Wenn bei der lokalen Entwicklung der DB-Server zeitgleich mit dem Init-Container
# gestartet wird, brauchen wir etwas Wartezeit
if [ "$DEV_STAGE" = "TRUE" ]; then
  sleep 10
fi


export SCHEMA="telefonbuch";

###
# Hier kommt der einzige Part, wo der globale Superuser DB_ADMIN_USER verwendet wird - er legt das Schema und die
# Nutzer in diesem Schema an (nicht aber die Tabellen im Schema).
###

# 1.1 Schema anlegen - hier wird der DB_ADMIN_USER (Super-Admin mit Rechten für die gesamte Datenbank benötigt)
echo "$DB_ADMIN_PASSWORD" | psql -d "postgresql://$DB_SERVER_NAME:5432/postgres" -U "$DB_ADMIN_USER" -W \
  -c "CREATE SCHEMA IF NOT EXISTS $SCHEMA;"

## 1.2 Zwei User anlegen: - DB_APP_ADMIN_USER mit DDL-Rechten für das Schema (für Liquibase)
##                       - DB_APP_USER mit Lese-Schreibrechten auf Tabellen, aber keine DDL-Rechte
envsubst < /init_users.sql > /liquibase/init_users_subst.sql
echo "$DB_ADMIN_PASSWORD" | psql -d "postgresql://$DB_SERVER_NAME:5432/postgres" -U "$DB_ADMIN_USER" -W \
  -f /liquibase/init_users_subst.sql

###
# Ab hier wird nur noch mit dem DB_APP_ADMIN_USER gearbeitet, der Rechte innerhalb dieses Schemas hat, aber keine
# globalen Rechte mehr auf die gesamte Datenbank - es kann also nichts in anderen Services kaputt gemacht werden.
###

# Mit Liquibase Schemamigrationen, bzw. die initiale Anlage der Tabellen durchführen
/liquibase/liquibase \
  --url=jdbc:postgresql://"$DB_SERVER_NAME":5432/postgres?currentSchema=$SCHEMA \
  --defaultSchemaName=$SCHEMA \
  --changeLogFile=changelog/changelog.xml \
  --username="$DB_APP_ADMIN_USER" \
  --password="$DB_APP_ADMIN_PASS" \
  update

LIQUIBASE_EXIT_CODE=$?

if [ $LIQUIBASE_EXIT_CODE -eq 0 ]
then
  # Falls durch den DB_APP_ADMIN_USER neue Tabellen angelegt wurden, muss der DB_APP_USER für diese berechtigt werden
  envsubst < /update_users.sql > /liquibase/update_users_subst.sql
  echo "$DB_APP_ADMIN_PASS" | psql -d "postgresql://$DB_SERVER_NAME:5432/postgres" -U "$DB_APP_ADMIN_USER" -W \
    -f /liquibase/update_users_subst.sql
#  echo "ADMIN PASS = $DB_APP_ADMIN_PASS ADMIN USERNAME = $DB_APP_ADMIN_USER"
else
  exit 1
fi
