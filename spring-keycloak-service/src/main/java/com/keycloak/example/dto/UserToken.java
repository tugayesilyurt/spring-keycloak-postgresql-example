package com.keycloak.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserToken {
	private String access_token;
	private String expires_in;
	private String refresh_expires_in;
	private String refresh_token;
	private String token_type;
	private String session_state;
	private String scope;
}
