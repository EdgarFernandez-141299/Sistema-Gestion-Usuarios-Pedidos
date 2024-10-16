package net.edgar.microservicepedidos.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.edgar.microservicepedidos.exception.MissingTokenException;
import net.edgar.microservicepedidos.helper.JWTHelper;
import net.edgar.microservicepedidos.helper.SecurityHelper;
import net.edgar.microservicepedidos.utility.TraceabilityUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

import static net.edgar.microserviceusuarios.constant.MicroservicePedidosConstant.SecurityConstant.*;
import static net.edgar.microserviceusuarios.constant.MicroservicePedidosConstant.TraceabilityConstant.*;


@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    @Value("${spring.application.name}")
    public String applicationName;

    private final JWTHelper jwtHelper;

    private final SecurityHelper securityHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String traceIdFromHeader = request.getHeader(TRACE_ID_HEADER);
        final String traceId = Objects.nonNull(traceIdFromHeader) ? traceIdFromHeader : TraceabilityUtils.generateTraceId();

        final String spanId = TraceabilityUtils.generateSpanId(applicationName);

        this.loadTraceIdSpanIdFilter(traceId, spanId);

        final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        log.info("inicia proceso de autorización...");

        try {
            if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith(BEARER_PREFIX)) {
                String jwt = authorizationHeader.substring(7);
                request.setAttribute(FORMATTED_TOKEN, jwt); // -> Setea el JWT mediante un atributo del 'HttpServletRequest' para que pueda ser recuperado en un controller con el mismo objeto 'HttpServletRequest., en este caso se uso en el recurso de /refresh-token
                this.processAuthorization(request, jwt);
            } else {
                throw new MissingTokenException(NO_AUTHENTICATION_TOKEN_PROVIDED_MESSAGE);
            }
        } catch (MissingTokenException ex) {
            request.setAttribute(MISSING_TOKEN, ex.getMessage());
        } catch (ExpiredJwtException ex) {
            request.setAttribute(JWT_EXPIRADO, ex.getMessage());
        } catch (SignatureException ex) {
            request.setAttribute(INVALID_SIGNATURE_JWT, ex.getMessage());
        } catch (MalformedJwtException ex) {
            request.setAttribute(MALFORMED_JWT, ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            request.setAttribute(NO_SUCH_ALGORITHM, ex.getMessage());
        } catch (InvalidKeySpecException ex) {
            request.setAttribute(INVALID_KEY_SPEC, ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            request.setAttribute(UNSUPPORTED_JWT, ex.getMessage());
        }

        filterChain.doFilter(request, response); // Continua con la cadena de filtros posteriores a este.
    }

    private void processAuthorization(HttpServletRequest request, String jwt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.jwtHelper.validateToken(jwt);

        UserDetails userDetails = this.securityHelper.loadUserAuthorizationDetails(this.jwtHelper.extrtactSubject(jwt),  this.jwtHelper.extractUsername(jwt));

        this.authorizeUser(request, userDetails);
    }

    private void authorizeUser(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private void loadTraceIdSpanIdFilter(String traceId, String spanId) {
        MDC.put(TRACE_ID_KEY, traceId);
        MDC.put(SPAN_ID_KEY, spanId);
    }


}
