package com.keycloak.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@Getter
@Setter
@ConfigurationProperties("keycloak")
@AllArgsConstructor
@NoArgsConstructor
public class JwtDecoderConfig {

	public String jwkSetUri;

	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).jwsAlgorithm(SignatureAlgorithm.RS512).build();
	}

}