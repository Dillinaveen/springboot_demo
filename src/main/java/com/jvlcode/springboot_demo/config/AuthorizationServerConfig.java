
package com.jvlcode.springboot_demo.config;

/*
 * JWT generation is handled by JwtTokenService.
 * JWT validation is configured in JwtConfig and SecurityConfig.
 *
 * A separate OAuth2 Authorization Server is not required
 * for this application's current JWT-based authentication.
 */
public final class AuthorizationServerConfig {

    private AuthorizationServerConfig() {
    }
}
