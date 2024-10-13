
CREATE TABLE public.pedido (
    id BIGSERIAL PRIMARY KEY,
    id_usuario BIGINT,
    fecha_de_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN NOT NULL,
    estado VARCHAR(20) CHECK (estado IN ('PENDIENTE', 'COMPLETADO', 'CANCELADO')) NOT NULL,
    total NUMERIC(10, 2) NOT NULL
);

