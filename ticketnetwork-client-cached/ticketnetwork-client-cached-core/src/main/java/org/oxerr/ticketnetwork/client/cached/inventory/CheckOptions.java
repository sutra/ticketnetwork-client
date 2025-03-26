package org.oxerr.ticketnetwork.client.cached.inventory;

/**
 * The options in check listings.
 */
public interface CheckOptions {

	static CheckOptions defaults() {
		return new CheckParams();
	}

	CheckOptions pageSize(int pageSize);

	CheckOptions chunkSize(int chunkSize);

	int pageSize();

	int chunkSize();

}
