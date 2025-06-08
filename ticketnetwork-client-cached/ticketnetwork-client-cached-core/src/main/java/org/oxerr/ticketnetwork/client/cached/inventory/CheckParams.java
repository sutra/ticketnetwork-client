package org.oxerr.ticketnetwork.client.cached.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The check params implements the {@link CheckOptions}.
 */
public class CheckParams implements CheckOptions {

	/**
	 * The page size when do check.
	 */
	private int pageSize = 500;

	/**
	 * The chunk size to load keys from cache.
	 */
	private int chunkSize = 0;

	private boolean create = true;
	private boolean update = true;
	private boolean delete = true;

	@Override
	public CheckOptions pageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	@Override
	public CheckOptions chunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
		return this;
	}

	@Override
	public CheckOptions create(boolean create) {
		this.create = create;
		return this;
	}

	@Override
	public CheckOptions update(boolean update) {
		this.update = update;
		return this;
	}

	@Override
	public CheckOptions delete(boolean delete) {
		this.delete = delete;
		return this;
	}

	public int pageSize() {
		return pageSize;
	}

	public int chunkSize() {
		return chunkSize;
	}

	@Override
	public boolean create() {
		return create;
	}

	@Override
	public boolean update() {
		return update;
	}

	@Override
	public boolean delete() {
		return delete;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CheckParams)) {
			return false;
		}
		CheckParams rhs = (CheckParams) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
