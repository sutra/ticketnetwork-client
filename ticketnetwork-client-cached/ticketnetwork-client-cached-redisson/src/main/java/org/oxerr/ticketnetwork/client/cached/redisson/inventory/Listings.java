package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.oxerr.ticketnetwork.client.model.AdditionalNotePostModel;
import org.oxerr.ticketnetwork.client.model.AdditionalNoteVariablePostModel;
import org.oxerr.ticketnetwork.client.model.MoneyAmountModel;
import org.oxerr.ticketnetwork.client.model.NearTerm;
import org.oxerr.ticketnetwork.client.model.NearTermDeliveryMethod;
import org.oxerr.ticketnetwork.client.model.Note;
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
import org.oxerr.ticketnetwork.client.model.Variable;

final class Listings {

	private Listings() {
		throw new AssertionError("No " + this.getClass() + " instances for you!");
	}

	public static boolean isSame(TicketGroup a, TicketGroupV4PostModel b) {
		return Stream.of(
			// section
			Objects.equals(a.getSeats().getSection(), b.getSection()),
			// row
			Objects.equals(a.getSeats().getRow(), b.getRow()),
			// low seat
			Objects.equals(a.getSeats().getLowSeat(), b.getLowSeat()),
			// quantity
			Objects.equals(a.getQuantity().getAvailable(), b.getQuantity()),
			// unit price
			isSameUnitPrice(Optional.ofNullable(a.getUnitPrice()), Optional.ofNullable(b.getUnitPrice())),
			// seating type
			Objects.equals(
				Optional.ofNullable(a.getSeats()).map(Seats::getSeatingType).map(SeatingType::getId).orElse(null),
				b.getSeatingTypeId()
			),
			// stock type
			Objects.equals(
				Optional.ofNullable(a.getStockType()).map(StockType::getId).orElse(null),
				b.getStockTypeId()
			),
			// near term delivery method
			Objects.equals(
				Optional.ofNullable(a.getNearTerm()).map(NearTerm::getNearTermDeliveryMethod).map(NearTermDeliveryMethod::getId).orElse(null),
				b.getNearTermDeliveryMethodId()
			),
			// broadcast channel IDs
			Objects.equals(a.getBroadcastChannelIds(), b.getBroadcastChannelIds()),
			// is on hand
			Objects.equals(a.getIsOnHand(), b.getIsOnHand()),
			// on hand date
			Objects.equals(a.getOnHandDate(), b.getOnHandDate()),
			// notes
			isSameNotes(Optional.ofNullable(a.getNotes()), Optional.ofNullable(b.getNotes()))
		).allMatch(Boolean::booleanValue);
	}

	public static boolean isSameUnitPrice(Optional<UnitPriceGetModel> a, Optional<UnitPricePostModel> b) {
		return Stream.of(
			// Currency
			Objects.equals(
				a.map(UnitPriceGetModel::getRetailPrice).map(MoneyAmountModel::getCurrencyCode).orElse(null),
				b.map(UnitPricePostModel::getFacePrice).map(MoneyAmountModel::getCurrencyCode).orElse(null)
			),
			// Wholesale price
			Objects.compare(
				a.map(UnitPriceGetModel::getWholesalePrice).map(MoneyAmountModel::getValue).orElse(BigDecimal.ZERO),
				b.map(UnitPricePostModel::getWholesalePrice).orElse(BigDecimal.ZERO),
				BigDecimal::compareTo
			) == 0,
			// Retail price
			Objects.compare(
				a.map(UnitPriceGetModel::getRetailPrice).map(MoneyAmountModel::getValue).orElse(BigDecimal.ZERO),
				b.map(UnitPricePostModel::getRetailPrice).orElse(BigDecimal.ZERO),
				BigDecimal::compareTo
			) == 0,
			// Face price
			Objects.compare(
				a.map(UnitPriceGetModel::getFacePrice).map(MoneyAmountModel::getValue).orElse(BigDecimal.ZERO),
				b.map(UnitPricePostModel::getFacePrice).map(MoneyAmountModel::getValue).orElse(BigDecimal.ZERO),
				BigDecimal::compareTo
			) == 0
		).allMatch(Boolean::booleanValue);
	}

	public static boolean isSameNotes(Optional<Notes> a, Optional<NotesPostModel> b) {
		return Stream.of(
			Objects.equals(
				a.map(Notes::getExternal).orElse(null),
				b.map(NotesPostModel::getExternal).orElse(null)
			),
			Objects.equals(
				a.map(Notes::getInternal).orElse(null),
				b.map(NotesPostModel::getInternal).orElse(null)
			),
			isSameAdditionalNotes(
				a.map(Notes::getAdditional).orElseGet(Collections::emptyList),
				b.map(NotesPostModel::getAdditional).orElseGet(Collections::emptyList)
			)
		).allMatch(Boolean::booleanValue);
	}

	public static boolean isSameAdditionalNotes(List<Note> note, List<AdditionalNotePostModel> additionalNotes) {
		if (note.size() != additionalNotes.size()) {
			return false;
		}
		return note.stream()
			.allMatch(n -> additionalNotes.stream().anyMatch(an -> isSameAdditionalNote(n, an)));
	}

	public static boolean isSameAdditionalNote(Note note, AdditionalNotePostModel additionalNote) {
		return Stream.of(
			Objects.equals(note.getId(), additionalNote.getNoteId()),
			isSameVariables(
				Optional.ofNullable(note.getVariables()).orElseGet(Collections::emptyList),
				Optional.ofNullable(additionalNote.getVariables()).orElseGet(Collections::emptyList)
			)
		).allMatch(Boolean::booleanValue);
	}

	public static boolean isSameVariables(List<Variable> variables, List<AdditionalNoteVariablePostModel> additionalNoteVariables) {
		if (variables.size() != additionalNoteVariables.size()) {
			return false;
		}
		return variables.stream()
			.allMatch(v -> additionalNoteVariables.stream().anyMatch(m -> isSameVariable(v, m)));
	}

	public static boolean isSameVariable(Variable variable, AdditionalNoteVariablePostModel m) {
		return Stream.of(
			Objects.equals(variable.getVariable(), m.getVariable()),
			Objects.equals(variable.getValue(), m.getValue())
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
		dest.setNotes(orig.getNotes());
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
