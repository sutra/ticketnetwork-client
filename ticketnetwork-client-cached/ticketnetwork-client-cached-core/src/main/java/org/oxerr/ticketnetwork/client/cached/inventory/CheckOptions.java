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

	CheckOptions create(boolean create);
	CheckOptions update(boolean update);
	CheckOptions delete(boolean delete);

	int pageSize();

	int chunkSize();

	boolean create();
	boolean update();
	boolean delete();

}
