package org.oxerr.ticketnetwork.client.rescu.resource.oauth2;

import org.oxerr.ticketnetwork.client.model.Token;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * The OAuth Token API provides a way to generate a new access token.
 * See <a href="https://tn-release.s3.amazonaws.com/tn-apis/API+Authorization.pdf">API AUTHORIZATION</a>.
 */
@Path("/oauth2")
public interface TokenResource {

	/**
	 * Obtaining an access token.
	 *
	 * Note: Generating a new access token with the same scopes as an existing
	 * token will invalidate the previous token. One JWT access token can be
	 * used multiple times within its lifetime, and it is best practice to
	 * re-use the same token for as long as possible after generating it.
	 *
	 * To generate an access token, integrate with the OAuth Token API. Use the
	 * /oauth2/token resource to obtain a new token.
	 *
	 * Combine your application's consumer key and secret in the format
	 * {@code consumer-key:consumer-secret}, then base64 encode the resulting
	 * string. This will be used as a Basic auth token in the Authorization
	 * header of the HTTP request, Use the Sandbox client credentials when
	 * generating a Sandbox application token, and the Production credentials
	 * when generating a Production application token.
	 *
	 * Submit a form {@code POST} request to the /oauth2/token resource to
	 * generate a new token and invalidate the old token. Include any scopes
	 * needed by that token in the request.
	 *
	 * @param grantType {@code client_credentials}
	 * @param scope the list of scopes separated by a space, plus (optionally)
	 * a device scope.
	 * @return the new access token as well as the number of seconds remaining
	 * before the token expires.
	 */
	@POST
	@Path("/token")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	Token generateToken(
		@FormParam("grant_type") String grantType,
		@FormParam("scope") String scope
	);

}
