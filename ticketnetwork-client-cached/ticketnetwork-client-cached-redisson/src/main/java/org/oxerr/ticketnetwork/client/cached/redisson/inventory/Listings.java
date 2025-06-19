package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.oxerr.ticketnetwork.client.model.MoneyAmountModel;
import org.oxerr.ticketnetwork.client.model.NearTerm;
import org.oxerr.ticketnetwork.client.model.NearTermDeliveryMethod;
import org.oxerr.ticketnetwork.client.model.Notes;
import org.oxerr.ticketnetwork.client.model.NotesPostModel;
import org.oxerr.ticketnetwork.client.model.Quantity;
import org.oxerr.ticketnetwork.client.model.SeatingType;
import org.oxerr.ticketnetwork.client.model.Seats;
import org.oxerr.ticketnetwork.client.model.StockType;
import org.oxerr.ticketnetwork.client.model.TicketGroup;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.UnitPriceGetModel;
import org.oxerr.ticketnetwork.client.model.UnitPricePostModel;

final class Listings {

	private Listings() {
		throw new AssertionError("No " + this.getClass() + " instances for you!");
	}

	public static boolean isSame(TicketGroup a, TicketGroupV4PostModel b) {
		return Stream.of(
			Objects.equals(a.getSeats().getSection(), b.getSection()),
			Objects.equals(a.getSeats().getRow(), b.getRow()),
			Objects.equals(a.getSeats().getLowSeat(), b.getLowSeat()),
			Objects.equals(a.getQuantity().getAvailable(), b.getQuantity()),
			isSameUnitPrice(a, b),
			Objects.equals(
				Optional.ofNullable(a.getSeats()).map(Seats::getSeatingType).map(SeatingType::getId).orElse(null),
				b.getSeatingTypeId()
			),
			Objects.equals(
				Optional.ofNullable(a.getStockType()).map(StockType::getId).orElse(null),
				b.getStockTypeId()
			),
			Objects.equals(
				Optional.ofNullable(a.getNearTerm()).map(NearTerm::getNearTermDeliveryMethod).map(NearTermDeliveryMethod::getId).orElse(null),
				b.getNearTermDeliveryMethodId()
			)
		).allMatch(Boolean::booleanValue);
	}

	public static boolean isSameUnitPrice(TicketGroup a, TicketGroupV4PostModel b) {
		return Stream.of(
			// Currency
			Objects.equals(
				Optional.ofNullable(a.getUnitPrice()).map(UnitPriceGetModel::getRetailPrice).map(MoneyAmountModel::getCurrencyCode).orElse(null),
				Optional.ofNullable(b.getUnitPrice()).map(UnitPricePostModel::getFacePrice).map(MoneyAmountModel::getCurrencyCode).orElse(null)
			),
			// Wholesale price
			Objects.compare(
				Optional.ofNullable(a.getUnitPrice()).map(UnitPriceGetModel::getWholesalePrice).map(MoneyAmountModel::getValue).orElse(BigDecimal.ZERO),
				Optional.ofNullable(b.getUnitPrice()).map(UnitPricePostModel::getWholesalePrice).orElse(BigDecimal.ZERO),
				BigDecimal::compareTo
			) == 0,
			// Retail price
			Objects.compare(
				Optional.ofNullable(a.getUnitPrice()).map(UnitPriceGetModel::getRetailPrice).map(MoneyAmountModel::getValue).orElse(BigDecimal.ZERO),
				Optional.ofNullable(b.getUnitPrice()).map(UnitPricePostModel::getRetailPrice).orElse(BigDecimal.ZERO),
				BigDecimal::compareTo
			) == 0,
			// Face price
			Objects.compare(
				Optional.ofNullable(a.getUnitPrice()).map(UnitPriceGetModel::getFacePrice).map(MoneyAmountModel::getValue).orElse(BigDecimal.ZERO),
				Optional.ofNullable(b.getUnitPrice()).map(UnitPricePostModel::getFacePrice).map(MoneyAmountModel::getValue).orElse(BigDecimal.ZERO),
				BigDecimal::compareTo
			) == 0
		).allMatch(Boolean::booleanValue);
	}

	public static void patch(TicketGroupV4PostModel dest, TicketGroupV4PostModel orig) {
		dest.setSection(orig.getSection());
		dest.setRow(orig.getRow());
		dest.setLowSeat(orig.getLowSeat());
		dest.setQuantity(orig.getQuantity());
		dest.getUnitPrice().setWholesalePrice(orig.getUnitPrice().getWholesalePrice());
		dest.getUnitPrice().setRetailPrice(orig.getUnitPrice().getRetailPrice());
		dest.getUnitPrice().setFacePrice(orig.getUnitPrice().getFacePrice());
		dest.setSeatingTypeId(orig.getSeatingTypeId());
		dest.setStockTypeId(orig.getStockTypeId());
		dest.setNearTermDeliveryMethodId(orig.getNearTermDeliveryMethodId());
	}

	public static String toString(TicketGroup g) {
		return new ToStringBuilder(g, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("ticketGroupId", g.getTicketGroupId())
			.append("referenceTicketGroupId", g.getReferenceTicketGroupId())
			.append("section", g.getSeats().getSection())
			.append("row", g.getSeats().getRow())
			.append("lowSeat", g.getSeats().getLowSeat())
			.append("highSeat", g.getSeats().getHighSeat())
			.append("quantity", toString(g.getQuantity()))
			.append("wholesalePrice", g.getUnitPrice().getWholesalePrice().getValue())
			.append("retailPrice", g.getUnitPrice().getRetailPrice().getValue())
			.append("facePrice", g.getUnitPrice().getFacePrice().getValue())
			.append("currency", g.getUnitPrice().getRetailPrice().getCurrencyCode())
			.append("notes", toString(g.getNotes()))
			.toString();
	}

	public static String toString(TicketGroupV4PostModel r) {
		return new ToStringBuilder(r, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("referenceTicketGroupId", r.getReferenceTicketGroupId())
			.append("section", r.getSection())
			.append("row", r.getRow())
			.append("lowSeat", r.getLowSeat())
			.append("quantity", r.getQuantity())
			.append("wholesalePrice", r.getUnitPrice().getWholesalePrice())
			.append("retailPrice", r.getUnitPrice().getRetailPrice())
			.append("facePrice", r.getUnitPrice().getFacePrice())
			.append("currency", r.getUnitPrice().getFacePrice().getCurrencyCode())
			.append("notes", toString(r.getNotes()))
			.toString();
	}

	public static String toString(Quantity quantity) {
		return new ToStringBuilder(quantity, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("total", quantity.getTotal())
			.append("available", quantity.getAvailable())
			.toString();
	}

	public static String toString(Notes notes) {
		return new ToStringBuilder(notes, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("external", notes.getExternal())
			.toString();
	}

	public static String toString(NotesPostModel notes) {
		return new ToStringBuilder(notes, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("external", notes.getExternal())
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
