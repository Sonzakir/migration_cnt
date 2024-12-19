-- create_table_adresse
CREATE TABLE adresse (
                         id BIGINT DEFAULT nextval('adresse_seq') PRIMARY KEY NOT NULL,
                         strasse VARCHAR(150),
                         haus_no VARCHAR(10),
                         plz VARCHAR(5),
                         stadt VARCHAR(100),
                         bundesland VARCHAR(100)
);

