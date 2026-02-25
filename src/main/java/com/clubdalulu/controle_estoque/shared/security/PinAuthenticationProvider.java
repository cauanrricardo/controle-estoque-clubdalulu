package com.clubdalulu.controle_estoque.shared.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PinAuthenticationProvider implements AuthenticationProvider {

    @Value("${app.security.pin}")
    private String pinCorreto;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String pinDigitado = authentication.getCredentials().toString();

        if (!pinCorreto.equals(pinDigitado)) {
            throw new BadCredentialsException("PIN incorreto");
        }

        return new UsernamePasswordAuthenticationToken(
                "admin",
                pinDigitado,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
