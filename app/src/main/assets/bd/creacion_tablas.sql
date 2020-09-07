DROP TABLE emple;

CREATE TABLE IF NOT EXISTS 'Emple' (
id_emple INTEGER PRIMARY KEY NOT NULL,
nombre_emple TEXT NOT NULL,
depart_id INTEGER NOT NULL,
CONSTRAINT fk_emple_depart FOREIGN KEY(depart_id) REFERENCES Depart(id_depart)
);

DROP TABLE Depart;

CREATE TABLE IF NOT EXISTS 'Depart' (
id_depart INTEGER PRIMARY KEY NOT NULL,
nombre_depart TEXT NOT NULL
);