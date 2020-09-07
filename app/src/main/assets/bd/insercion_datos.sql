/* INSERCIONES DEPARTAMENTO */
INSERT INTO depart VALUES(0, 'INFORMATICA');
INSERT INTO depart VALUES(1, 'ELECTRONICA');
INSERT INTO depart VALUES(2, 'PSICOLOGIA');

/* INSERCIONES EMPLEADOS */
INSERT INTO emple VALUES(0, 'Carlos', 1);
INSERT INTO emple VALUES(1, 'Rafa', 0);
INSERT INTO emple VALUES(2, 'Sergio', 0);

/* PRUEBAS */
SELECT * FROM emple, depart WHERE depart.id_depart = emple.depart_id;