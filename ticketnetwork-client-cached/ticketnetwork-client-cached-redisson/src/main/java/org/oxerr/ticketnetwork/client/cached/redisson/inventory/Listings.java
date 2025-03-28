package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.oxerr.ticketnetwork.client.model.TicketGroup;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

final class Listings {

	private Listings() {
		throw new AssertionError("No " + this.getClass() + " instances for you!");
	}

	public static boolean isSame(TicketGroup a, TicketGroupV4PostModel b) {
		return Stream.of(
			Objects.equals(a.getSeats().getSection(), b.getSection()),
			Objects.equals(a.getSeats().getRow(), b.getRow()),
			Objects.equals(a.getSeats().getLowSeat().toString(), b.getLowSeat()),
			Objects.equals(a.getHideSeats(), b.getHideSeats()),
			Objects.equals(a.getQuantity().getAvailable(), b.getQuantity()),
			Objects.equals(a.getUnitPrice().getRetailPrice().getCurrencyCode(), b.getUnitPrice().getFacePrice().getCurrencyCode()),
			Objects.compare(a.getUnitPrice().getRetailPrice().getValue(), b.getUnitPrice().getRetailPrice(), BigDecimal::compareTo) == 0
		).allMatch(Boolean::booleanValue);
	}

	public static String toString(TicketGroup g) {
		return new ToStringBuilder(g, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("ticketGroupId", g.getTicketGroupId())
			.append("referenceTicketGroupId", g.getReferenceTicketGroupId())
			.append("section", g.getSeats().getSection())
			.append("row", g.getSeats().getRow())
			.append("lowSeat", g.getSeats().getLowSeat())
			.append("highSeat", g.getSeats().getHighSeat())
			.append("quantity", g.getQuantity())
			.append("price", g.getUnitPrice().getRetailPrice().getValue())
			.append("notes", g.getNotes())
			.toString();
	}

	public static String toString(TicketGroupV4PostModel r) {
		return new ToStringBuilder(r, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("referenceTicketGroupId", r.getReferenceTicketGroupId())
			.append("section", r.getSection())
			.append("row", r.getRow())
			.append("lowSeat", r.getLowSeat())
			.append("quantity", r.getQuantity())
			.append("price", r.getUnitPrice().getRetailPrice())
			.append("notes", r.getNotes())
			.toString();
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
		if (!(obj instanceof Listings)) {
			return false;
		}
		Listings rhs = (Listings) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
