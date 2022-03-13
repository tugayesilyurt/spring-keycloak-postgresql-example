package com.keycloak.example.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import com.keycloak.example.config.KeycloakProvider;
import com.keycloak.example.dto.CreateUserRequest;
import com.keycloak.example.dto.UserCredentials;

@Service
public class KeycloakAdminClientService {

	private final KeycloakProvider kcProvider;

	public KeycloakAdminClientService(KeycloakProvider keycloakProvider) {
		this.kcProvider = keycloakProvider;
	}

	public Response createKeycloakUser(CreateUserRequest user) {
		UsersResource usersResource = kcProvider.getInstance().realm(kcProvider.realm).users();
		CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

		UserRepresentation kcUser = new UserRepresentation();
		kcUser.setUsername(user.getUsername());
		kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
		kcUser.setFirstName(user.getFirstname());
		kcUser.setLastName(user.getLastname());
		kcUser.setEmail(user.getEmail());
		kcUser.setEnabled(true);
		kcUser.setEmailVerified(false);
		Response response = usersResource.create(kcUser);

		return response;

	}

	private static CredentialRepresentation createPasswordCredentials(String password) {
		CredentialRepresentation passwordCredentials = new CredentialRepresentation();
		passwordCredentials.setTemporary(false);
		passwordCredentials.setType(CredentialRepresentation.PASSWORD);
		passwordCredentials.setValue(password);
		return passwordCredentials;
	}

	private String sendPost(List<NameValuePair> urlParameters) throws Exception {

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(kcProvider.url + "/realms/" + kcProvider.realm + "/protocol/openid-connect/token");

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();

	}

	public String getToken(UserCredentials userCredentials) throws Exception {

		String responseToken = null;

		String username = userCredentials.getUsername();

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		urlParameters.add(new BasicNameValuePair("client_id", "admin-cli"));
		urlParameters.add(new BasicNameValuePair("username", username));
		urlParameters.add(new BasicNameValuePair("password", userCredentials.getPassword()));

		responseToken = sendPost(urlParameters);

		return responseToken;

	}

	public void logoutUser(String userId) {

		UsersResource userRessource = kcProvider.getKeycloakUserResource();

		userRessource.get(userId).logout();

	}

}