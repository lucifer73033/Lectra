package Lectra.Configurations;

import Lectra.UserAuth.AuthUtilities.CustomAuthTokenConvertor;
import Lectra.UserAuth.AuthUtilities.CustomBasicAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Value("${URI}")
    private String jwksURI;
    @Autowired
    private final CustomAuthTokenConvertor customAuthTokenConvertor;
    @Autowired
    private final CustomBasicAuthProvider customBasicAuthProvider;

    public SecurityConfig(CustomAuthTokenConvertor customAuthTokenConvertor, CustomBasicAuthProvider customBasicAuthProvider) {
        this.customAuthTokenConvertor = customAuthTokenConvertor;
        this.customBasicAuthProvider = customBasicAuthProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(c->c
                        .requestMatchers("/","/index.html","/app.js","/main.css").permitAll()
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(customBasicAuthProvider)
                .oauth2ResourceServer(oauth->oauth.jwt(j->j.jwkSetUri(jwksURI).jwtAuthenticationConverter(customAuthTokenConvertor)))
                .csrf(c->c.disable())
                .build();
    }
}
