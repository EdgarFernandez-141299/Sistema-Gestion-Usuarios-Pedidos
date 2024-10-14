package net.edgar.microserviceusuarios.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityHelper {

    private final UserDetailsService userDetailsService;

    public UserDetails loadUserAuthorizationDetails(String username) {
        log.info("Se consulta en base de datos...");
        return this.userDetailsService.loadUserByUsername(username);
    }

}
