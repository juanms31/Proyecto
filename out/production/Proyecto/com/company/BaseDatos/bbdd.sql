SET NAMES 'utf8';
DROP DATABASE IF EXISTS `ProyectoDAM`;
CREATE DATABASE IF NOT EXISTS `ProyectoDAM` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

USE `ProyectoDAM`;

CREATE TABLE GrupoMaterial(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    siglas_grupo VARCHAR(5) NOT NULL,
    nombre_grupo VARCHAR(50),
    Descripcion VARCHAR(150)
);

CREATE TABLE EspecificacionMaterial(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    siglas_Especificacion VARCHAR(5) NOT NULL,
    nombre_Especificacion VARCHAR(50),
    Descripcion VARCHAR(150)
);

CREATE TABLE UnidadMaterial(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    siglas_unidad VARCHAR(5) NOT NULL,
    nombre_unidad VARCHAR(50),
    Descripcion VARCHAR(150)
);

CREATE TABLE CalidadMaterial(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    siglas_calidad VARCHAR(5) NOT NULL,
    nombre_calidad VARCHAR(50),
    Descripcion VARCHAR(150)
);

CREATE TABLE Material (
	id INTEGER AUTO_INCREMENT PRIMARY key,
    cod VARCHAR(20),
    grupo VARCHAR(10),
    descripcion VARCHAR(200),
    especificacion VARCHAR(200),
    unidad VARCHAR (5),
    espesor DECIMAL(5,2),
    calidad VARCHAR(100),
    proveedor1 VARCHAR(100),
    precio1 DECIMAL (10,2),
    proveedor2 VARCHAR (100),
    precio2 DECIMAL (10,2),
    proveedor3 VARCHAR (100),
    precio3 DECIMAL (10,2),
    id_grupo INTEGER,
    FOREIGN KEY (id_grupo)
    	REFERENCES GrupoMaterial (id),
    id_especifiacion INTEGER,
    FOREIGN KEY (id_especifiacion)
    	REFERENCES EspecificacionMaterial (id),
    id_unidad INTEGER,
    FOREIGN KEY (id_unidad)
    	REFERENCES UnidadMaterial (id),
    id_calidad INTEGER,
    FOREIGN KEY (id_calidad)
        REFERENCES CalidadMaterial (id)
);

CREATE TABLE MOInstalacionMaterial (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    proveedor1 VARCHAR(100),
    precio1 DECIMAL (10,2),
    proveedor2 VARCHAR (100),
    precio2 DECIMAL (10,2),
    id_material INTEGER,
    FOREIGN KEY (id_material)
    	REFERENCES Material (id)
);

CREATE TABLE Proveedor (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    CIF VARCHAR(9) NOT NULL,
    nombre_proveedor VARCHAR(150) NOT NULL,
    direccion VARCHAR(150),
    mail1 VARCHAR(100),
    telefono1 VARCHAR(12),
    mail2 VARCHAR(100),
    telefono2 VARCHAR(20)
);

CREATE TABLE Cliente(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    CIF VARCHAR(9) NOT NULL,
    nombre VARCHAR (30),
    direccion VARCHAR (200),
    mail1 VARCHAR (50),
    telefono1 VARCHAR(12),
    mail2 VARCHAR(100),
    telefono2 VARCHAR(20)
);

CREATE TABLE Actuacion (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(30),
    especificacion VARCHAR(200),
    id_cliente INTEGER,
    estado VARCHAR(100),
    descripcion VARCHAR(300),
    importe DECIMAL(10,2),
    por_certificar INTEGER,
    total_certificaciones DECIMAL(10,2),
    gasto_material DECIMAL(10,2),
    material_ofertado DECIMAL(10,2),
    resultado_balance DECIMAL(10,2),

    hoja_planificacion VARCHAR(200),
    hoja_presupuesto VARCHAR(200),

    horas_ofertadas INTEGER,
    horas_ejecutadas INTEGER,

    fecha_solicitud DATE NOT NULL,
    fecha_envio DATE,
    fecha_comienzo DATE,
    fecha_finalizacion DATE,

    FOREIGN KEY (id_cliente)
    	REFERENCES Cliente (id)
);

CREATE TABLE EspecificacionActuacion (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    siglas_Especificacion VARCHAR(5) NOT NULL,
    nombre_Especificacion VARCHAR(50),
    Descripcion VARCHAR(150)
);

CREATE TABLE EstadoActuaciob(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    siglas_estado VARCHAR(5) NOT NULL,
    nombre_estado VARCHAR(50) NOT NULL,
    detalle_estado VARCHAR(200),
    id_actuacion INTEGER,
    FOREIGN KEY (id_actuacion)
    	REFERENCES Actuacion (id)
);
CREATE TABLE Albaran(
    id VARCHAR(20) PRIMARY KEY,
    id_actuacion INTEGER,
    id_proveedor INTEGER,
    concepto VARCHAR (100),
    unidades VARCHAR (20),
    fecha_entrada_albarán DATE,
    precio_unitario DECIMAL(5,2),
    base_imponible DECIMAL(5,2),
    naturaleza VARCHAR(30),

    FOREIGN KEY (id_actuacion)
        REFERENCES Actuacion (id),
    FOREIGN KEY (id_proveedor)
        REFERENCES Proveedor (id)
);

CREATE TABLE MaterialCompradoProveedores(
    id INTEGER PRIMARY KEY,
    fecha_compra DATE,
    id_material INTEGER,
    FOREIGN KEY (id_material)
        REFERENCES Material (id),
    id_proveedor INTEGER,
    FOREIGN KEY (id_proveedor)
        REFERENCES Proveedor (id),
    id_actuacion INTEGER,
    FOREIGN KEY (id_actuacion)
        REFERENCES Actuacion (id),
    id_albaran VARCHAR(20),
    FOREIGN KEY (id_albaran)
        REFERENCES Albaran (id)
);


CREATE TABLE Certificacion(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    fecha_certificacion DATE NOT NULL,
    valor DECIMAL(10,2),
    observaciones VARCHAR(200),
    id_actuacion INTEGER,
    FOREIGN KEY (id_actuacion)
        REFERENCES Actuacion (id)
);


CREATE TABLE Trabajador(
  id INTEGER  AUTO_INCREMENT PRIMARY KEY,
  DNI VARCHAR (9) NOT NULL,
  nombre VARCHAR(30)  NOT NULL,
  apellidos VARCHAR(50),
  telefono VARCHAR(9),
  fecha_nacimiento DATE,
  nacionalidad VARCHAR (20),
  puesto VARCHAR (100),
  salario DOUBLE
);

CREATE TABLE SeguimientoLaboral
(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    id_trabajador INTEGER,
    tipo VARCHAR(10) NOT NULL,
    id_actuacion INTEGER,
    ano INTEGER NOT NULL,
    dia INTEGER NOT NULL,
    mes INTEGER NOT NULL,
    hora_entrada VARCHAR(10) NOT NULL,
    hora_salida VARCHAR(10),
    horas_totales DOUBLE,
    horas_extra DOUBLE,
    FOREIGN KEY (id_actuacion)
        REFERENCES Actuacion (id),
    FOREIGN KEY (id_trabajador)
        REFERENCES Trabajador (id)

);

CREATE TABLE Vacaciones (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    fecha_solicitada_inicio DATE,
    fecha_solicitada_fin DATE,
    fecha_aprobada_inicio DATE,
    fecha_aprobada_fin DATE,
    observaciones VARCHAR(250),
    id_trabajador INTEGER,
    FOREIGN KEY (id_trabajador)
        REFERENCES Trabajador (id)
);


CREATE TABLE MaterialUtilizadoActuacion (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    id_material INTEGER,
    FOREIGN KEY (id_material)
    	REFERENCES Material (id),
    id_actuacion INTEGER,
    FOREIGN KEY (id_actuacion)
    	REFERENCES Actuacion (id)
);

CREATE TABLE Usuario(
  id INTEGER  AUTO_INCREMENT PRIMARY KEY,
  DNI VARCHAR (9) NOT NULL,
  nombre VARCHAR(30)  NOT NULL,
  apellidos VARCHAR(50),
  telefono VARCHAR(9),
  fecha_nacimiento DATE,
  nacionalidad VARCHAR (20),
  email VARCHAR(40) NOT NULL,
  pass VARCHAR(256) NOT NULL
);




-- Insercciones

INSERT INTO `calidadmaterial`(`id`, `siglas_calidad`, `nombre_calidad`, `Descripcion`)
VALUES ('1','AI304','ACERO INOXIDABLE 304 L',''),
       ('2','AI316','ACERO INOXIDABLE 316L','');

INSERT INTO `grupomaterial`(`id`, `siglas_grupo`, `nombre_grupo`, `Descripcion`)
VALUES ('1','MTAC','Material Acero al Carbono',''),
       ('2','MTIN','Material Inoxidable',''),
       ('3','ALU','Chapa Aluminio',''),
       ('4','INOX','ACERO INOXIDABLE','');

INSERT INTO `unidadmaterial`(`id`, `siglas_unidad`, `nombre_unidad`, `Descripcion`)
VALUES ('1','Kg','Kilogramos',''),
       ('2','gr','Gramos',''),
       ('3','L','Litros',''),
       ('4','ml','Mililitros',''),
       ('5','m2','Metros Cuadrados','');

INSERT INTO `especificacionmaterial`(`id`, `siglas_Especificacion`, `nombre_Especificacion`, `Descripcion`)
VALUES ('1','IN','Industrial',''),
       ('2','PE','Petroleo',''),
       ('3','SA','Sanitario',''),
       ('4','SO','Soportacion','');

INSERT INTO `especificacionactuacion`(`id`, `siglas_Especificacion`, `nombre_Especificacion`, `Descripcion`)
VALUES ('1','REP','Reparacion',''),
       ('2','MAN','Mantenimiento',''),
       ('3','NUEVA','Nueva Obra',''),
       ('4','AIS','Aislacion','');

INSERT INTO `usuario`(`id`, `DNI`, `nombre`, `apellidos`, `telefono`, `fecha_nacimiento`, `nacionalidad`, `email`, `pass`)
VALUES ('1','00000000K','admin','','','','','admin','admin')
