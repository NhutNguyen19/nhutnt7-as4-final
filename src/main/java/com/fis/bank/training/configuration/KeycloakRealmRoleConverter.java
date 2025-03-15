package com.fis.bank.training.configuration;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // Trích xuất các quyền từ Keycloak
        Collection<GrantedAuthority> authorities = defaultGrantedAuthoritiesConverter.convert(jwt);

        Collection<GrantedAuthority> keycloakAuthorities = jwt.getClaimAsStringList("realm_access").stream()
                .map(role -> "ROLE_" + role) // Ánh xạ các quyền từ Keycloak thành quyền của Spring Security
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Kết hợp các quyền từ JWT và Keycloak
        authorities.addAll(keycloakAuthorities);
        return authorities;
    }
}
