SET NAMES 'utf8';
DROP DATABASE IF EXISTS `ProyectoDAM`;
CREATE DATABASE IF NOT EXISTS `ProyectoDAM` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

USE `ProyectoDAM`;

CREATE TABLE Material (
	id INTEGER PRIMARY key,
    grupo VARCHAR(10),
    cod VARCHAR(20),
    descripcion VARCHAR(200),
    especificacion VARCHAR(200),
    unidad VARCHAR (5),
    espesor VARCHAR(50),
    calidad VARCHAR(100),
    proveedor1 VARCHAR(100),
    precio1 DECIMAL (10,2),
    proveedor2 VARCHAR (100),
    precio2 DECIMAL (10,2),
    proveedor3 VARCHAR (100),
    precio3 DECIMAL (10,2)
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
    nombre_proveedor VARCHAR(150) NOT NULL,
    direccion VARCHAR(150),
    mail1 VARCHAR(100),
    mail2 VARCHAR(100),
    telefono1 VARCHAR(12),
    telefono2 VARCHAR(20)
);

CREATE TABLE Cliente(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR (30),
    direccion VARCHAR (200),
    mail1 VARCHAR (50),
    mail2 VARCHAR (50),
    telefono1 INTEGER (12),
    telefono2 VARCHAR (12)
);

CREATE TABLE Actuacion (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    especificacion VARCHAR(200),
    estado VARCHAR(100),
    fecha_solicitud DATE,
    fecha_envio DATE,
    fecha_comienzo DATE,
    fecha_finalizacion DATE,
    descripcion VARCHAR(300),
    importe DECIMAL(10,2),
    hoja_planificacion VARCHAR(200),
    hoja_presupuesto VARCHAR(200),
    total_certificacion DECIMAL(10,2),
    por_certificar INTEGER,
    horas_ofertadas INTEGER,
    horas_ejecutadas INTEGER,
    material_ofertado INTEGER,
    gasto_material DECIMAL(10,2),
    resultado_balance DECIMAL(10,2),
    id_cliente INTEGER,
    FOREIGN KEY (id_cliente)
    	REFERENCES Cliente (id)
);

CREATE TABLE Albaran(
    id INTEGER PRIMARY KEY,
    concepto VARCHAR (100),
    unidades VARCHAR (20),
    fecha_entrada_albarán DATE,
    precio_unitario double,
    base_imponible double,
    naturaleza VARCHAR(30),

    id_actuacion INTEGER,
    FOREIGN KEY (id_actuacion)
        REFERENCES Actuacion (id),
    id_proveedor INTEGER,
    FOREIGN KEY (id_proveedor)
        REFERENCES Proveedor (id)
);


CREATE TABLE Certificacion(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    observaciones VARCHAR(200),
    id_actuacion INTEGER,
    FOREIGN KEY (id_actuacion)
        REFERENCES Actuacion (id)
);


CREATE TABLE Trabajador(
  id INTEGER  AUTO_INCREMENT PRIMARY KEY,
  fnac DATE,
  nacionalidad VARCHAR (20),
  nombre VARCHAR(30),
  apellidos VARCHAR(50),
  puesto VARCHAR (100),
  salario double
);

CREATE TABLE SeguimientoLaboral
(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    ano INTEGER,
    dia INTEGER,
    mes INTEGER,
    hora_entrada DATE,
    hora_salida DATE,
    horas_totales DATE,
    horas_extra INTEGER,
    id_actuacion INTEGER,
    FOREIGN KEY (id_actuacion)
        REFERENCES Actuacion (id),
    id_trabajador INTEGER,
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
    id_albaran INTEGER,
    FOREIGN KEY (id_albaran)
    	REFERENCES Albaran (id)
);

CREATE TABLE materialUtilizadoActuacion (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    id_material INTEGER,
    FOREIGN KEY (id_material)
    	REFERENCES Material (id),
    id_actuacion INTEGER,
    FOREIGN KEY (id_actuacion)
    	REFERENCES Actuacion (id)
);
