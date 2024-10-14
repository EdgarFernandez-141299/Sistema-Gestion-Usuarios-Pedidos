
-- Tabla de usuarios
CREATE TABLE public.usuario (
    id_usuario BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    clave_acceso varchar(400) NOT NULL,
    correo_electronico VARCHAR(150) UNIQUE NOT NULL,
    activo BOOLEAN NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


