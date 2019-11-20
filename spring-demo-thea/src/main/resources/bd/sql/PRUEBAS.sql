
BEGIN TRANSACTION;  
DROP TABLE IF EXISTS rol;
DROP TABLE IF EXISTS funcion;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS persona;
DROP TABLE IF EXISTS foto;
DROP TABLE IF EXISTS usuario_rol;
DROP TABLE IF EXISTS persona_funcion;
DROP TABLE IF EXISTS acceso;
DROP TABLE IF EXISTS registro;
DROP TABLE IF EXISTS rol_Permiso;
DROP TABLE IF EXISTS permiso;
COMMIT;

SELECT * FROM rol;
SELECT * FROM funcion;
SELECT * FROM usuario;
SELECT * FROM persona;
SELECT * FROM foto;
SELECT * FROM usuario_rol;
SELECT * FROM persona_funcion;
SELECT * FROM acceso;
SELECT * FROM registro;
SELECT * FROM rol_Permiso;
SELECT * FROM permiso;
