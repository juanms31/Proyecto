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
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    cod VARCHAR(20),
    grupo VARCHAR(10),
    descripcion VARCHAR(200),
    especificacion VARCHAR(200),
    unidad VARCHAR (5),
    espesor DECIMAL(5,2),
    calidad VARCHAR(100),
    proveedor_1 VARCHAR(100),
    precio_1 DECIMAL (10,2),
    proveedor_2 VARCHAR (100),
    precio_2 DECIMAL (10,2),
    proveedor_3 VARCHAR (100),
    precio_3 DECIMAL (10,2),
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
    proveedor_1 VARCHAR(100),
    precio_1 DECIMAL (10,2),
    proveedor_2 VARCHAR (100),
    precio_2 DECIMAL (10,2),
    id_material INTEGER,
    FOREIGN KEY (id_material)
    	REFERENCES Material (id)
);

CREATE TABLE Proveedor (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    CIF VARCHAR(9) NOT NULL,
    nombre_proveedor VARCHAR(150) NOT NULL,
    direccion VARCHAR(150),
    mail_1 VARCHAR(100),
    telefono_1 VARCHAR(12),
    mail_2 VARCHAR(100),
    telefono_2 VARCHAR(20)
);

CREATE TABLE Cliente(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    CIF VARCHAR(9) NOT NULL,
    nombre VARCHAR (30),
    direccion VARCHAR (200),
    mail_1 VARCHAR (50),
    telefono_1 VARCHAR(12),
    mail_2 VARCHAR(100),
    telefono_2 VARCHAR(20)
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
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    cod varchar(20) NOT NULL,
    id_actuacion INTEGER,
    id_proveedor INTEGER,
    concepto VARCHAR (100),
    fecha_entrada_albarán DATE,

    FOREIGN KEY (id_actuacion)
        REFERENCES Actuacion (id),
    FOREIGN KEY (id_proveedor)
        REFERENCES Proveedor (id)
);

CREATE TABLE MaterialCompradoProveedores(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    id_material INTEGER,
    id_proveedor INTEGER,
    id_actuacion INTEGER,
    id_albaran INTEGER,
    unidades DOUBLE,
    precio_Unidad DOUBLE,
    base_Imponible DOUBLE,
    FOREIGN KEY (id_actuacion)
        REFERENCES Actuacion (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_proveedor)
        REFERENCES Proveedor (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_material)
        REFERENCES Material (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_albaran)
        REFERENCES Albaran (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
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
VALUES ('1','00000000K','admin','admin','697441578','1999-05-31','','admin','admin');

INSERT INTO `proveedor`(`id`, `CIF`, `nombre_proveedor`, `direccion`, `mail_1`, `telefono_1`, `mail_2`, `telefono_2`)
VALUES ('1','36598655P','Soleos SLU','Avd Madrid','soleosslu@gmail.com','698332657','',''),
    ('2','36598655L','Latigos SLU','Avd Malaga','latigosslu@gmail.com','654223698','',''),
    ('3','36598655U','Bombonas SLU','Avd Almeria','bombonasslu@gmail.com','625456896','','');

INSERT INTO `cliente`(`id`, `CIF`, `nombre`, `direccion`, `mail_1`, `telefono_1`, `mail_2`, `telefono_2`)
VALUES ('1','21035545K','PITA','Avd PITA','parquetecnologico@gmail.com','632554789','',''),
       ('2','36598874P','PTS','Avd PTS','parquetecnologico@gmail.com','65988547','','');

INSERT INTO `actuacion` (`id`, `nombre`, `especificacion`, `id_cliente`, `estado`, `descripcion`, `importe`, `por_certificar`, `total_certificaciones`, `gasto_material`, `material_ofertado`, `resultado_balance`, `hoja_planificacion`, `hoja_presupuesto`, `horas_ofertadas`, `horas_ejecutadas`, `fecha_solicitud`, `fecha_envio`, `fecha_comienzo`, `fecha_finalizacion`)
VALUES
    (1, 'MANTENIMIENTO PITA', 'MAN', 1, 'Pendiente', '', '0.00', 0, '0.00', '0.00', '0.00', '0.00', 'algo.pdf', 'algo2.pdf', 3600, 1200, '2022-06-03', NULL, NULL, NULL);

INSERT INTO `albaran` (`id`, `cod`, `id_actuacion`, `id_proveedor`, `concepto`, `fecha_entrada_albarán`) VALUES
    (13, '123/C', 1, 1, 'Concepto Albaran 2', '2022-06-06');

INSERT INTO `material` (`id`, `cod`, `grupo`, `descripcion`, `especificacion`, `unidad`, `espesor`, `calidad`, `proveedor_1`, `precio_1`, `proveedor_2`, `precio_2`, `proveedor_3`, `precio_3`, `id_grupo`, `id_especifiacion`, `id_unidad`, `id_calidad`)
VALUES
    (1, 'MTAC1', 'MTAC', 'Acero al carbono 1', 'Petroleo', 'gr', '63.00', 'AI316', 'Latigos SLU', '32100.00', '', '0.00', '', '0.00', NULL, NULL, NULL, NULL),
    (2, 'ALU2', 'ALU', 'Aluminio Sanitario 1', 'Sanitario', 'Kg', '3.20', 'AI304', 'Soleos SLU', '360.00', 'Latigos SLU', '0.00', '', '0.00', NULL, NULL, NULL, NULL),
    (3, 'INOX3', 'INOX', 'Inoxidable Soportación', 'Soportacion', 'Kg', '23.00', 'AI304', 'Soleos SLU', '2310.00', 'Latigos SLU', '1230.00', 'Bombonas SLU', '1230.00', NULL, NULL, NULL, NULL),
    (4, 'INOX4', 'INOX', 'Inoxidable Sanitario', 'Sanitario', 'gr', '23.00', 'AI316', 'Latigos SLU', '360.00', 'Soleos SLU', '210.00', '', '0.00', NULL, NULL, NULL, NULL);

INSERT INTO `materialcompradoproveedores` (`id`, `id_material`, `id_proveedor`, `id_actuacion`, `id_albaran`, `unidades`, `precio_Unidad`, `base_Imponible`) VALUES
    (35, 1, 1, 1, 13, 5, 32100, 160500),
    (36, 4, 1, 1, 13, 9, 360, 3240);

INSERT INTO `trabajador`(`id`, `DNI`, `nombre`, `apellidos`, `telefono`, `fecha_nacimiento`, `nacionalidad`, `puesto`, `salario`)
VALUES
    ('1','21035545K','Juan','Martinez Sanchez','697441578','1999-05-31','Español','Tecnico','18000');

INSERT INTO `seguimientolaboral` (`id`, `id_trabajador`, `tipo`, `id_actuacion`, `ano`, `dia`, `mes`, `hora_entrada`, `hora_salida`, `horas_totales`, `horas_extra`)
VALUES
    (1, 1, 'Entrada', 1, 2022, 6, 6, '11:15', '', 0, 0);
