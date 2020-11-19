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
    PRIMARY KEY (id),
    FOREIGN KEY (corsoID) REFERENCES corso(nome) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (docenteID) REFERENCES docente(id) ON DELETE CASCADE ON UPDATE CASCADE,
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
    PRIMARY KEY (id),
    UNIQUE(docenteID, lessonDate),
    UNIQUE(utenteID, lessonDate)
);

INSERT into utente (username, password, admin) values ('Emilio','Bruno',1);
INSERT into utente (username, password, admin) values ('Alessandro','Ampala',1);
INSERT into utente (username, password, admin) values ('Mario','Rossi',0);
INSERT into utente (username, password, admin) values ('Luca','Martella',0);
INSERT into utente (username, password, admin) values ('Martino','Gallo',0);