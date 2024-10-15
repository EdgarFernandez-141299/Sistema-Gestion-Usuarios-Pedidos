package net.edgar.microserviceusuarios.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.edgar.microserviceusuarios.utility.ResponseUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static net.edgar.microserviceusuarios.constant.MicroserviceUsuariosConstant.SecurityConstant.*;
import static net.edgar.microserviceusuarios.constant.MicroserviceUsuariosConstant.TraceabilityConstant.*;

@Component
@Slf4j
public class WebSecurityAuthenticationEntryPointComponent implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        log.error("Petición con error de seguridad");

        Map<Boolean, String> mensajesErrorMap = new HashMap<>();

        String traceIdSpanId = String.format(TRACE_ID_SPAN_ID_FORMATO, MDC.get(TRACE_ID_KEY), MDC.get(SPAN_ID_KEY));

        Object jwtExpirado = request.getAttribute(JWT_EXPIRADO);
        Object missingToken = request.getAttribute(MISSING_TOKEN);
        Object invalidSignatureJwt = request.getAttribute(INVALID_SIGNATURE_JWT);
        Object malformedJwt = request.getAttribute(MALFORMED_JWT);
        Object invalidToken = request.getAttribute(INVALID_TOKEN);
        Object noSuchAlgorithm = request.getAttribute(NO_SUCH_ALGORITHM);
        Object invalidKeySpec = request.getAttribute(INVALID_KEY_SPEC);
        Object unsupportedJwt = request.getAttribute(UNSUPPORTED_JWT);

        mensajesErrorMap.put(Objects.nonNull(jwtExpirado), AUTHENTICATION_TOKEN_EXPIRED_MESSAGE);
        mensajesErrorMap.put(Objects.nonNull(missingToken), String.valueOf(missingToken));
        mensajesErrorMap.put(Objects.nonNull(invalidSignatureJwt), INVALID_JWT_SIGNATURE_MESSAGE);
        mensajesErrorMap.put(Objects.nonNull(malformedJwt), MALFORMED_JWT_MESSAGE);
        mensajesErrorMap.put(Objects.nonNull(invalidToken), String.valueOf(invalidToken));
        mensajesErrorMap.put(Objects.nonNull(noSuchAlgorithm), String.valueOf(noSuchAlgorithm));
        mensajesErrorMap.put(Objects.nonNull(invalidKeySpec), String.valueOf(invalidKeySpec));
        mensajesErrorMap.put(Objects.nonNull(unsupportedJwt), String.valueOf(unsupportedJwt));

        String detallesMensaje = mensajesErrorMap.getOrDefault(true, FORBIDDEN_REASONS_MESSAGE);
        String codigoError = mensajesErrorMap.containsKey(true) ? UNAUTHORIZED_CODIGO_BASE : FORBIDDEN_CODIGO_BASE;
        String mensajeError = mensajesErrorMap.containsKey(true) ? UNAUTHORIZED_MENSAJE_BASE : FORBIDDEN_MENSAJE_BASE;
        HttpStatus httpStatus = mensajesErrorMap.containsKey(true) ? HttpStatus.UNAUTHORIZED : HttpStatus.FORBIDDEN;

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
        OutputStream responseStream = response.getOutputStream();
        new ObjectMapper().writeValue(responseStream,
                ResponseUtils.generateErrorResponse(
                        codigoError,
                        !traceIdSpanId.equalsIgnoreCase(TRACE_ID_SPAN_ID_NULL_FORMAT) ? traceIdSpanId : null,
                        mensajeError,
                        Collections.singletonList(detallesMensaje)));
        responseStream.flush();
    }
}