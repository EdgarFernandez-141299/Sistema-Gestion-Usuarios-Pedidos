-- Crear la base de datos
CREATE DATABASE pedidos_db;

-- Conectar a la base de datos
\c pedidos_db;

-- Tabla de pedidos
CREATE TABLE public.pedido (
    id_pedido BIGSERIAL PRIMARY KEY,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) CHECK (estado IN ('PENDIENTE', 'COMPLETADO', 'CANCELADO', 'ELIMINADO')) NOT NULL,
    total NUMERIC(10, 2) NOT NULL,
    id_usuario BIGINT NOT NULL
);