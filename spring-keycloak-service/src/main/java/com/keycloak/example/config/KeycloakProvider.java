package com.keycloak.example.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
public class KeycloakProvider {

	public String url;

	public String realm;

	public String username;

	public String password;

	private static Keycloak keycloak = null;

	public Keycloak getInstance() {
		if (keycloak == null) {

			return KeycloakBuilder.builder().serverUrl(url).realm("master").clientId("admin-cli").username(username)
					.password(password).build();
		}
		return keycloak;
	}

	public UsersResource getKeycloakUserResource() {

		Keycloak kc = KeycloakBuilder.builder().serverUrl(url).realm("master").username(username).password(password)
				.clientId("tugay-client").resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
				.build();

		RealmResource realmResource = kc.realm(realm);
		UsersResource userRessource = realmResource.users();

		return userRessource;
	}

	public RealmResource getRealmResource() {

		Keycloak kc = KeycloakBuilder.builder().serverUrl(url).realm("master").username(username).password(password)
				.clientId("tugay-client").resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
				.build();

		RealmResource realmResource = kc.realm(realm);

		return realmResource;

	}

}