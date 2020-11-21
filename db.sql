DROP TABLE IF EXISTS prenotazione;
DROP TABLE IF EXISTS archivioPrenotazione;
DROP TABLE IF EXISTS lezione;
DROP TABLE IF EXISTS corso;
DROP TABLE IF EXISTS docente;
DROP TABLE IF EXISTS utente;

CREATE TABLE utente (
    username varchar(100) NOT NULL,
    password varchar(255) NOT NULL,
    admin BOOLEAN NOT NULL DEFAULT 0,
    PRIMARY KEY (username)
);

CREATE TABLE docente (
    id int(11) NOT NULL AUTO_INCREMENT,
    nome varchar(100) NOT NULL,
    cognome varchar(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE corso (
    nome varchar(100) NOT NULL,
    PRIMARY KEY (nome)
);

CREATE TABLE lezione (
    corsoID varchar(100) NOT NULL,
    docenteID int(11) NOT NULL,
    PRIMARY KEY (corsoID, docenteID),
    FOREIGN KEY (corsoID) REFERENCES corso(nome) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (docenteID) REFERENCES docente(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE prenotazione (
    id int(11) NOT NULL AUTO_INCREMENT,
    corsoID varchar(100) NOT NULL,
    docenteID int(11) NOT NULL,
    utenteID varchar(100) NOT NULL,
    lessonDate int(2) NOT NULL CHECK (lessonDate >= 0 and lessonDate < 25),
    status ENUM ('active', 'done', 'canceled') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (corsoID, docenteID) REFERENCES lezione(corsoID, docenteID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (utenteID) REFERENCES utente(username) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE(docenteID, lessonDate),
    UNIQUE(utenteID, lessonDate)
);

CREATE TABLE archivioPrenotazione (
    id int(11) NOT NULL AUTO_INCREMENT,
    corsoID varchar(100) NOT NULL,
    docenteID int(11) NOT NULL,
    utenteID varchar(100) NOT NULL,
    lessonDate int(2) NOT NULL CHECK (lessonDate >= 0 and lessonDate < 25),
    status ENUM ('done', 'canceled') NOT NULL,
    PRIMARY KEY (id),
    UNIQUE(docenteID, lessonDate),
    UNIQUE(utenteID, lessonDate)
);

INSERT into utente (username, password, admin) values ('Emilio','Bruno',1);
INSERT into utente (username, password, admin) values ('Alessandro','Ampala',1);
INSERT into utente (username, password, admin) values ('Mario','Rossi',0);
INSERT into utente (username, password, admin) values ('Luca','Martella',0);
INSERT into utente (username, password, admin) values ('Martino','Gallo',0);

delimiter |

CREATE TRIGGER archivia AFTER UPDATE ON prenotazione
FOR EACH ROW
BEGIN
    IF  (NEW.status <> OLD.status) THEN

        INSERT INTO archivioPrenotazione (corsoId, docenteId, utenteId, lessonDate, status)
        VALUES (OLD.corsoId, OLD.docenteId, OLD.utenteID, OLD.lessonDate, NEW.status);


    END IF;
END;
|

delimiter ;

INSERT into docente (nome, cognome) VALUES ('Rossano', 'Gaeta');
INSERT INTO corso (nome) VALUES ('Architettura');
INSERT INTO lezione (corsoID, docenteID) VALUES ('Architettura', 1);
INSERT INTO prenotazione (corsoID, docenteID, utenteID, lessonDate, status) VALUES ('Architettura', 1, 'Alessandro', 20, 'active');


-- Transaction to call from code
START TRANSACTION;

UPDATE prenotazione
SET status = 'done'
WHERE id = 1;

DELETE FROM prenotazione where id = 1;

COMMIT;