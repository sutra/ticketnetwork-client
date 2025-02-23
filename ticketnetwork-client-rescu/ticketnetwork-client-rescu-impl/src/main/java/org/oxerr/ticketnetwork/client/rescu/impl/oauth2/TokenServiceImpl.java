package org.oxerr.ticketnetwork.client.rescu.impl.oauth2;

import org.oxerr.ticketnetwork.client.model.Token;
import org.oxerr.ticketnetwork.client.oauth2.TokenService;
import org.oxerr.ticketnetwork.client.rescu.resource.oauth2.TokenResource;

public class TokenServiceImpl implements TokenService {

	private final TokenResource tokenResource;

	public TokenServiceImpl(TokenResource tokenResource) {
		this.tokenResource = tokenResource;
	}

	@Override
	public Token generateToken(String grantType, String scope) {
		return this.tokenResource.generateToken(grantType, scope);
	}

}
