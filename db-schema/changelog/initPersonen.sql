
-- create table person
CREATE TABLE person (
                        id BIGINT DEFAULT nextval('person_seq') PRIMARY KEY NOT NULL ,
                        vorname VARCHAR(255),
                        nachname VARCHAR(255)
);

-- create table person_adresse
CREATE TABLE person_adresse(
                               adresse_id BIGINT NOT NULL,
                               person_id BIGINT NOT NULL,
                               CONSTRAINT fk_person_adresse_zu_adresse FOREIGN KEY (adresse_id) REFERENCES adresse(id),
                               CONSTRAINT fk_person_adresse_zu_person FOREIGN KEY (person_id) REFERENCES person(id)
);


-- create table person_kontakt
CREATE TABLE person_kontakt (
                                id BIGINT PRIMARY KEY NOT NULL
);

-- create table person_kontakt_email
CREATE TABLE person_kontakt_email(
                                     person_kontakt_id BIGINT NOT NULL,
                                     email VARCHAR(255),
                                     CONSTRAINT fk_personKontaktEmail_zu_person_kontakt FOREIGN KEY (person_kontakt_id) REFERENCES person_kontakt(id)
);

-- create table person_kontakt_festnetznummer
CREATE TABLE person_kontakt_festnetznummer (
                                               person_kontakt_id BIGINT NOT NULL,
                                               festnetznummer VARCHAR(255),
                                               CONSTRAINT fk_personKontaktFestnetznummer_zu_person_kontakt FOREIGN KEY (person_kontakt_id) REFERENCES person_kontakt(id)
);

-- create table_person_kontakt_mobil_nummern
CREATE TABLE person_kontakt_mobil_nummern (
                                              person_kontakt_id BIGINT NOT NULL,
                                              mobil_nummern VARCHAR(255),
                                              CONSTRAINT fk_personKontaktMobilNummer_zu_person_kontakt FOREIGN KEY (person_kontakt_id) REFERENCES person_kontakt(id)
);

-- create table_person_kontakt_webseite
CREATE TABLE person_kontakt_webseite (
                                         person_kontakt_id BIGINT NOT NULL,
                                         webseite VARCHAR(255),
                                         CONSTRAINT fk_personKontaktWebseite_zu_person_kontakt FOREIGN KEY (person_kontakt_id) REFERENCES person_kontakt(id)
);

-- create table_person_kontakt_list
CREATE TABLE person_kontakt_list (
                                     person_id BIGINT NOT NULL,
                                     kontakt_list_id BIGINT NOT NULL,
                                     CONSTRAINT fk_personKontaktList_zu_person FOREIGN KEY (person_id) REFERENCES person(id),
                                     CONSTRAINT fk_personKontaktList_zu_person_kontakt FOREIGN KEY (kontakt_list_id) REFERENCES person_kontakt(id)
);
