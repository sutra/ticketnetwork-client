package org.oxerr.ticketnetwork.client.oauth2;

import org.oxerr.ticketnetwork.client.model.Token;

public interface TokenService {

	/**
	 * Obtains an access token.
	 *
	 * @param grantType the grant type.
	 * @param scope the list of scopes separated by space, plus (optionally) a
	 * device scope.
	 * @return the generated token.
	 */
	Token generateToken(String grantType, String scope);

}
