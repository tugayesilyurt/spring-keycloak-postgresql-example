package com.keycloak.example.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keycloak.example.dto.CreateUserRequest;
import com.keycloak.example.dto.UserCredentials;
import com.keycloak.example.dto.UserToken;
import com.keycloak.example.service.KeycloakAdminClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

	private final KeycloakAdminClientService kcAdminClient;
	private final ObjectMapper objectMapper;

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody CreateUserRequest user) {
		kcAdminClient.createKeycloakUser(user);
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);

	}

	@DeleteMapping(value = "/logout/{userId}")
	public ResponseEntity<?> logoutUser(@PathVariable("userId") String userId) {
		kcAdminClient.logoutUser(userId);
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);

	}

	@PostMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getTokenUsingCredentials(@RequestBody UserCredentials userCredentials) throws Exception {

		String responseToken = kcAdminClient.getToken(userCredentials);
		UserToken uToken = objectMapper.readValue(responseToken, UserToken.class);

		return new ResponseEntity<UserToken>(uToken, HttpStatus.OK);

	}

    @RolesAllowed("tugay-role")
    @GetMapping("/test/role")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("it's okey!");
    }

    @RolesAllowed("test-role")
    @GetMapping("/test")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("test-role");
    }

}