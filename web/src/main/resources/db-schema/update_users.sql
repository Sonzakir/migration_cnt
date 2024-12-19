-- Berechtigt den DB_APP_USER erneut auf alle Tabellen - wichtig, wenn neue Tabellen angelegt wurden
-- Für den DB_ADMIN_USER brauchen wir das nicht, denn der ist ja Owner der Tabellen
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA $SCHEMA TO $DB_APP_USER;
GRANT USAGE ON ALL SEQUENCES IN SCHEMA $SCHEMA TO $DB_APP_USER;