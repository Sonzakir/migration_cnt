-- create_table_firma
CREATE TABLE firma (
                       id BIGINT DEFAULT  nextval('firma_seq') PRIMARY KEY NOT NULL ,
                       name VARCHAR(150)
);

-- create_table_firma_adresse
CREATE TABLE firma_adresse (
                               adresse_id BIGINT NOT NULL ,
                               firma_id BIGINT NOT NULL ,
                               CONSTRAINT fk_firma_adresse_adresse FOREIGN KEY (adresse_id) REFERENCES adresse(id) ,
                               CONSTRAINT fk_firma_adresse_firma_id FOREIGN KEY (firma_id) REFERENCES firma(id)
);

-- create table_firma_branchen
CREATE TABLE firma_branchen (
                                firma_id BIGINT NOT NULL ,
                                branchen VARCHAR(255) ,
                                CONSTRAINT fk_firma_branchen_firma_id FOREIGN KEY (firma_id) REFERENCES firma(id)
);

-- create table_firma_kontakt
CREATE TABLE firma_kontakt (
                               id BIGINT PRIMARY KEY NOT NULL
);

-- create table_firma_kontakt_list
CREATE TABLE  firma_kontakt_list (
                                    firma_id BIGINT NOT NULL,
                                    kontakt_list_id BIGINT NOT NULL,
                                    CONSTRAINT fk_firmaKontaktList_firma FOREIGN KEY (firma_id) REFERENCES firma(id),
                                    CONSTRAINT fk_firmaKontaktListID_firmaKontaktID FOREIGN KEY (kontakt_list_id) REFERENCES firma_kontakt(id)
);

-- create firma_kontakt_email
CREATE TABLE firma_kontakt_email (
                                     firma_kontakt_id BIGINT NOT NULL ,
                                     email VARCHAR(255),
                                     CONSTRAINT fk_firmaKontaktID_firma_kontakt_id FOREIGN KEY (firma_kontakt_id) REFERENCES firma_kontakt(id)
);

-- create firma_kontakt_faxnummer
CREATE TABLE firma_kontakt_faxnummer (
                                         firma_kontakt_id BIGINT NOT NULL ,
                                         faxnummer VARCHAR(255),
                                         CONSTRAINT fk_firmaKontaktID_firma_kontakt_id FOREIGN KEY (firma_kontakt_id) REFERENCES firma_kontakt(id)
);

-- create firma_kontakt_festnetznummer
CREATE TABLE firma_kontakt_festnetznummer (
                                              firma_kontakt_id BIGINT NOT NULL ,
                                              festnetznummer VARCHAR(255),
                                              CONSTRAINT fk_firmaKontaktID_firma_kontakt_id FOREIGN KEY (firma_kontakt_id) REFERENCES firma_kontakt(id)
);

-- create firma_kontakt_webseite
CREATE TABLE firma_kontakt_webseite(
                                       firma_kontakt_id BIGINT NOT NULL,
                                       webseite VARCHAR(255),
                                       CONSTRAINT fk_firmaKontaktID_firma_kontakt_id FOREIGN KEY (firma_kontakt_id) REFERENCES firma_kontakt(id)
);