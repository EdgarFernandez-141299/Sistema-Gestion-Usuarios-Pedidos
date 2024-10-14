package net.edgar.microserviceusuarios.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MicroserviceUsuariosConstant {


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SecurityConstant {

        public static final String JWT_EXPIRADO = "jwtExpirado";

        public static final String MISSING_TOKEN = "missingToken";

        public static final String INVALID_SIGNATURE_JWT = "invalidSignatureJwt";

        public static final String MALFORMED_JWT = "malformedJwt";

        public static final String FORMATTED_TOKEN = "formattedToken";

        public static final String INVALID_TOKEN = "invalidToken";

        public static final String NO_SUCH_ALGORITHM = "noSuchAlgorithm";

        public static final String INVALID_KEY_SPEC = "invalidKeySpec";

        public static final String UNSUPPORTED_JWT = "unsupportedJwt";

        public static final String ACCESS_TOKEN_HEADER = "Access-Token";

        public static final String AUTHORIZATION_HEADER = "Authorization";

        public static final String AUTHENTICATION_TOKEN_EXPIRED_MESSAGE = "The authentication token has expired";

        public static final String INVALID_JWT_SIGNATURE_MESSAGE = "Invalid JWT signature";

        public static final String MALFORMED_JWT_MESSAGE = "Malformed JWT";

        public static final String FORBIDDEN_REASONS_MESSAGE = "Access to details denied";

        public static final String BEARER_PREFIX = "Bearer ";

        public static final String NO_AUTHENTICATION_TOKEN_PROVIDED_MESSAGE = "No authentication token provided";

        public static final String UNAUTHORIZED_CODIGO_BASE = "401.edgar-net-unauthorized.401";

        public static final String UNAUTHORIZED_MENSAJE_BASE = "Ocurrió un error de autenticación";

        public static final String FORBIDDEN_CODIGO_BASE = "403.edgar-net-unauthorized.403";

        public static final String FORBIDDEN_MENSAJE_BASE = "Ocurrió un error de acceso prohibido";

        public static final String BAD_CREDENTIALS_DETALLES_MENSAJE_BASE = "Credenciales erróneas";

        public static final String USER_INVALID_MENSAJE_BASE = "Usuario invalido:";

        public static final String ACCESS_CONTROL_ALLOW_ORIGIN_VALUE = "*";

        public static final String ACCESS_CONTROL_ALLOW_METHODS_VALUE = "POST, PUT, GET, OPTIONS, DELETE";

        public static final String ACCESS_CONTROL_MAX_AGE_VALUE = "3600";

        public static final String ACCESS_CONTROL_ALLOW_HEADERS_VALUE = "Content-Type, Authorization, Content-Length, X-Requested-With";

        public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS_VALUE = "true";

        public static final String ACCESS_CONTROL_EXPOSE_HEADERS_VALUE = "*";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ResponseConstant {

        public static final String EXCEPTION_DETAIL_KEY = "exceptionDetail";

        public static final String OK_MENSAJE_BASE = "Operación exitosa";

        public static final String INTERNAL_SERVER_ERROR_CODIGO_BASE = "500.edgar-net-internal-server-error.500";

        public static final String INTERNAL_SERVER_ERROR_MENSAJE_BASE = "Ocurrió un error interno del servidor";

        public static final String NOT_FOUND_CODIGO_BASE = "404.edgar-net-not-found.404";

        public static final String NOT_FOUND_MENSAJE_BASE = "Recurso no encontrado";

        public static final String BAD_REQUEST_CODIGO_BASE = "400.edgar-net-bad-request.400";

        public static final String BAD_REQUEST_MENSAJE_BASE = "Parametros de entrada incorrectos";

        public static final String EXISTING_USER_CODIGO_BASE = "400.edgar-net-existing-user.400";

        public static final String EXISTING_USER_MENSAJE_BASE = "Usuario existente";

        public static final String EXISTING_USER_MENSAJE = "El usuario '%s' ya existe";

    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TraceabilityConstant {

        public static final String TRACE_ID_HEADER = "Trace-Id";

        public static final String TRACE_ID_KEY = "traceId";

        public static final String SPAN_ID_KEY = "spanId";

        public static final String TRACE_ID_SPAN_ID_NULL_FORMAT = "null-null";

    }
}
