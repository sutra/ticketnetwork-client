package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TicketGroupV4PostModel implements Serializable {

	private static final long serialVersionUID = 2025022301L;

	/**
	 * ID of the event for this listing.
	 */
	private Integer eventId;

	/**
	 * The number of tickets in the ticket group.
	 */
	private Integer quantity;

	/**
	 * The ID of the seating type.
	 */
	private Integer seatingTypeId;

	/**
	 * The broker-entered section in which the ticket group is located.
	 * This should match the section which is printed on the ticket
	 * (e.g. "2ND MEZZ 2").
	 * Max length is 100 characters.
	 */
	private String section;

	/**
	 * A user friendly representation of the section in which the ticket group
	 * is located (e.g. "Second Mezzanine 2"). Max length is 100 characters.
	 */
	private String standardSection;

	/**
	 * An internal representation of the section in which the ticket group is
	 * located which is used to locate the ticket group uniquely on the venue
	 * map (e.g. "2nd$Mezz$2"). Max length is 500 characters.
	 */
	private String canonicalSection;

	/**
	 * The row in which the ticket group is located. Max length is 30 characters.
	 */
	private String row;

	/**
	 * The lowest seat in the ticket group. Required if seatingType is not
	 * “General Admission”. For General Admission tickets, the value is ignored.
	 */
	private Integer lowSeat;

	/**
	 * UnitPrice object.
	 */
	private UnitPricePostModel unitPrice;

	/**
	 * Indicates whether these tickets are currently on-hand.
	 */
	private Boolean isOnHand;

	/**
	 * Gets or sets the on hand days before event.
	 */
	private Integer onHandDaysBeforeEvent;

	/**
	 * Indicates the tickets will be on-hand and ready to ship no later than
	 * this date. Only the date component will be used; midnight will be used
	 * as the time.
	 */
	private LocalDate onHandDate;

	/**
	 * The stock type for the ticket group. If omitted, defaults to stock type
	 * ID 1, which is “Standard”.
	 */
	private Integer stockTypeId;

	/**
	 * The ticket group type for the ticket group. If omitted, defaults to
	 * ticket group type ID 1, which is “Event Ticket”.
	 */
	private Integer ticketGroupTypeId;

	/**
	 * A comma-separated list of broadcast channel IDs that indicate which
	 * marketplaces these tickets are broadcasted to.
	 */
	private String broadcastChannelIds;

	/**
	 * Notes object.
	 */
	private Notes notes;

	/**
	 * The near term delivery method or set of near term delivery methods that
	 * should be available to customers. If omitted, defaults to the value set
	 * by the account setting for the ticket group stock type.
	 */
	private Integer nearTermDeliveryMethodId;

	/**
	 * Indicates when the near term shipping method(s) should become available
	 * to customers. If omitted, defaults to the value set by the account
	 * setting for the ticket group stock type.
	 */
	private Integer nearTermDisplayId;

	/**
	 * Sets the split rule for the ticket group. If omitted, defaults to the
	 * value set by the account setting for the ticket group stock type.
	 */
	private Integer splitRuleId;

	/**
	 * Indicates whether the seat numbers for the tickets are hidden.
	 */
	private Boolean hideSeats;

	/**
	 * Indicates whether the ticket group is in a pending state.
	 */
	private Boolean pending;

	/**
	 * The reference (external) ticket group ID.
	 *
	 * Note: This field does not have to be unique.
	 */
	private Integer referenceTicketGroupId;

	/**
	 * Flag indicating whether this is a short ticket group.
	 */
	private Boolean isShort;

	/**
	 * The array of tags to be associated with the ticket group.
	 */
	private List<Tag> tags;

	/**
	 * The autopricer settings for this ticket group.
	 */
	private Autopricer autopricer;

	public TicketGroupV4PostModel() {
	}

	public TicketGroupV4PostModel(TicketGroup ticketGroup) {
		this.eventId = ticketGroup.getEvent().getId();
		this.quantity = ticketGroup.getQuantity().getAvailable();
		this.seatingTypeId = ticketGroup.getSeats().getSeatingType().getId();
		this.section = ticketGroup.getSeats().getSection();
		this.standardSection = ticketGroup.getSeats().getStandardSection();
		this.canonicalSection = ticketGroup.getSeats().getCanonicalSection();
		this.row = ticketGroup.getSeats().getRow();
		this.lowSeat = ticketGroup.getSeats().getLowSeat();
		this.unitPrice = new UnitPricePostModel(ticketGroup.getUnitPrice());
		this.isOnHand = ticketGroup.getIsOnHand();
		this.onHandDate = ticketGroup.getOnHandDate();
		this.stockTypeId = ticketGroup.getStockType().getId();
		this.ticketGroupTypeId = ticketGroup.getTicketGroupType().getId();
		this.broadcastChannelIds = ticketGroup.getBroadcastChannelIds();
		this.notes = ticketGroup.getNotes();
		this.nearTermDeliveryMethodId = ticketGroup.getNearTerm().getNearTermDeliveryMethod().getId();
		this.nearTermDisplayId = ticketGroup.getNearTerm().getNearTermDisplay().getId();
		this.splitRuleId = ticketGroup.getSplitRule().getId();
		this.hideSeats = ticketGroup.getHideSeats();
		this.pending = ticketGroup.getPending();
		this.referenceTicketGroupId = ticketGroup.getReferenceTicketGroupId();
		this.isShort = ticketGroup.getIsShort();
		this.tags = ticketGroup.getTags();
		this.autopricer = ticketGroup.getAutopricer();
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getSeatingTypeId() {
		return seatingTypeId;
	}

	public void setSeatingTypeId(Integer seatingTypeId) {
		this.seatingTypeId = seatingTypeId;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getStandardSection() {
		return standardSection;
	}

	public void setStandardSection(String standardSection) {
		this.standardSection = standardSection;
	}

	public String getCanonicalSection() {
		return canonicalSection;
	}

	public void setCanonicalSection(String canonicalSection) {
		this.canonicalSection = canonicalSection;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public Integer getLowSeat() {
		return lowSeat;
	}

	public void setLowSeat(Integer lowSeat) {
		this.lowSeat = lowSeat;
	}

	public UnitPricePostModel getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(UnitPricePostModel unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Boolean getIsOnHand() {
		return isOnHand;
	}

	public void setIsOnHand(Boolean isOnHand) {
		this.isOnHand = isOnHand;
	}

	public Integer getOnHandDaysBeforeEvent() {
		return onHandDaysBeforeEvent;
	}

	public void setOnHandDaysBeforeEvent(Integer onHandDaysBeforeEvent) {
		this.onHandDaysBeforeEvent = onHandDaysBeforeEvent;
	}

	public LocalDate getOnHandDate() {
		return onHandDate;
	}

	public void setOnHandDate(LocalDate onHandDate) {
		this.onHandDate = onHandDate;
	}

	public Integer getStockTypeId() {
		return stockTypeId;
	}

	public void setStockTypeId(Integer stockTypeId) {
		this.stockTypeId = stockTypeId;
	}

	public Integer getTicketGroupTypeId() {
		return ticketGroupTypeId;
	}

	public void setTicketGroupTypeId(Integer ticketGroupTypeId) {
		this.ticketGroupTypeId = ticketGroupTypeId;
	}

	public String getBroadcastChannelIds() {
		return broadcastChannelIds;
	}

	public void setBroadcastChannelIds(String broadcastChannelIds) {
		this.broadcastChannelIds = broadcastChannelIds;
	}

	public Notes getNotes() {
		return notes;
	}

	public void setNotes(Notes notes) {
		this.notes = notes;
	}

	public Integer getNearTermDeliveryMethodId() {
		return nearTermDeliveryMethodId;
	}

	public void setNearTermDeliveryMethodId(Integer nearTermDeliveryMethodId) {
		this.nearTermDeliveryMethodId = nearTermDeliveryMethodId;
	}

	public Integer getNearTermDisplayId() {
		return nearTermDisplayId;
	}

	public void setNearTermDisplayId(Integer nearTermDisplayId) {
		this.nearTermDisplayId = nearTermDisplayId;
	}

	public Integer getSplitRuleId() {
		return splitRuleId;
	}

	public void setSplitRuleId(Integer splitRuleId) {
		this.splitRuleId = splitRuleId;
	}

	public Boolean getHideSeats() {
		return hideSeats;
	}

	public void setHideSeats(Boolean hideSeats) {
		this.hideSeats = hideSeats;
	}

	public Boolean getPending() {
		return pending;
	}

	public void setPending(Boolean pending) {
		this.pending = pending;
	}

	public Integer getReferenceTicketGroupId() {
		return referenceTicketGroupId;
	}

	public void setReferenceTicketGroupId(Integer referenceTicketGroupId) {
		this.referenceTicketGroupId = referenceTicketGroupId;
	}

	public Boolean getIsShort() {
		return isShort;
	}

	public void setIsShort(Boolean isShort) {
		this.isShort = isShort;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Autopricer getAutopricer() {
		return autopricer;
	}

	public void setAutopricer(Autopricer autopricer) {
		this.autopricer = autopricer;
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
		if (!(obj instanceof TicketGroupV4PostModel)) {
			return false;
		}
		TicketGroupV4PostModel rhs = (TicketGroupV4PostModel) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
