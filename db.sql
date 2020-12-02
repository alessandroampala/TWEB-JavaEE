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
    nome varchar(100) NOT NULL,
    cognome varchar(100),
    utenteID varchar(100) NOT NULL,
    lessonDate int(2) NOT NULL CHECK (lessonDate >= 0 and lessonDate < 25),
    status ENUM ('done', 'canceled') NOT NULL,
    PRIMARY KEY (id)
);

INSERT into utente (username, password, admin) values ('Emilio','Bruno',1);
INSERT into utente (username, password, admin) values ('Alessandro','Ampala',1);
INSERT into utente (username, password, admin) values ('Mario','Rossi',0);
INSERT into utente (username, password, admin) values ('Luca','Martella',0);
INSERT into utente (username, password, admin) values ('Martino','Gallo',0);

delimiter |

CREATE TRIGGER archiviaOnUpdate AFTER UPDATE ON prenotazione
FOR EACH ROW
BEGIN
    DECLARE nomeDocente varchar(100);
    DECLARE cognomeDocente varchar(100);
    SELECT docente.nome, docente.cognome FROM docente WHERE id=OLD.docenteId INTO nomeDocente, cognomeDocente;
    IF  (NEW.status <> OLD.status) THEN
        INSERT INTO archivioPrenotazione (corsoId, nome, cognome, utenteId, lessonDate, status)
        VALUES (OLD.corsoId, nomeDocente, cognomeDocente, OLD.utenteID, OLD.lessonDate, NEW.status);
    END IF;
END;
|

delimiter ;

delimiter |

CREATE TRIGGER archiviaOnDelete BEFORE DELETE ON prenotazione
FOR EACH ROW
BEGIN
    DECLARE nomeDocente varchar(100);
    DECLARE cognomeDocente varchar(100);
    SELECT docente.nome, docente.cognome FROM docente WHERE id=OLD.docenteId INTO nomeDocente, cognomeDocente;
    IF  (OLD.status = 'active') THEN
        INSERT INTO archivioPrenotazione (corsoId, nome, cognome, utenteId, lessonDate, status)
        VALUES (OLD.corsoId, nomeDocente, cognomeDocente, OLD.utenteID, OLD.lessonDate, 'canceled');
    END IF;
END;
|

delimiter ;

INSERT into docente (nome, cognome) VALUES ('Rossano', 'Gaeta');
INSERT into docente (nome, cognome) VALUES ('Luca', 'Rossi');
INSERT INTO corso (nome) VALUES ('Architettura');
INSERT INTO corso (nome) VALUES ('Sistemi Operativi');
INSERT INTO corso (nome) VALUES ('Matematica');
INSERT INTO lezione (corsoID, docenteID) VALUES ('Architettura', 1);
INSERT INTO lezione (corsoID, docenteID) VALUES ('Sistemi Operativi', 1);
INSERT INTO lezione (corsoID, docenteID) VALUES ('Matematica', 2);
INSERT INTO prenotazione (corsoID, docenteID, utenteID, lessonDate, status) VALUES ('Architettura', 1, 'Alessandro', 20, 'active');
INSERT INTO prenotazione (corsoID, docenteID, utenteID, lessonDate, status) VALUES ('Architettura', 1, 'Alessandro', 14, 'active');
INSERT INTO prenotazione (corsoID, docenteID, utenteID, lessonDate, status) VALUES ('Architettura', 1, 'Alessandro', 2, 'active');
INSERT INTO prenotazione (corsoID, docenteID, utenteID, lessonDate, status) VALUES ('Architettura', 1, 'Alessandro', 0, 'active');