package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketGroup implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The broker ID for the ticket owner.
	 */
	private Integer brokerId;

	/**
	 * The local ID of the ticket group.
	 */
	private Integer ticketGroupId;

	/**
	 * The ID of the ticket group on the ticketNetwork exchange.
	 */
	private Integer exchangeTicketGroupId;

	/**
	 * The date and time when the ticket group was created.
	 */
	private OffsetDateTime created;

	/**
	 * The date and time when the ticket group was last updated.
	 */
	private OffsetDateTime updated;

	/**
	 * Event object.
	 */
	private Event event;

	/**
	 * The name of the zone. Only applicable to zone ticket groups.
	 */
	private String zone;

	/**
	 * A comma-separated list of additional notes in the note class
	 * "Viewing Obstructions" for this ticket group.
	 */
	private String view;

	/**
	 * Seats object.
	 */
	private Seats seats;

	/**
	 * UnitPrice object.
	 */
	private UnitPriceGetModel unitPrice;

	/**
	 * Indicates whether these tickets are currently on-hand.
	 * PATCH operations: add, replace, test.
	 */
	private Boolean isOnHand;

	/**
	 * Indicates the tickets will be on-hand and ready to ship no later than
	 * this date. The value is a date with no time component. PATCH operations:
	 * add, replace, test.
	 */
	private LocalDate onHandDate;

	/**
	 * Indicates whether the tickets are available for instant download.
	 */
	private Boolean isInstant;

	/**
	 * The reference (external) ticket group ID. PATCH operations: add, remove,
	 * replace, test.
	 */
	private Integer referenceTicketGroupId;

	/**
	 * StockType object.
	 */
	private StockType stockType;

	/**
	 * TicketGroupType object.
	 */
	private TicketGroupType ticketGroupType;

	/**
	 * SplitRule object.
	 */
	private SplitRule splitRule;

	/**
	 * Quantity object.
	 */
	private Quantity quantity;

	/**
	 * Collection of ticket objects.
	 */
	private List<Ticket> tickets;

	/**
	 * A comma-separated list of broadcast channel IDs that indicate which
	 * marketplaces these tickets are broadcasted to. PATCH operations: add,
	 * remove, replace, test.
	 */
	private String broadcastChannelIds;

	/**
	 * Notes object.
	 */
	private Notes notes;

	/**
	 * NearTerm object.
	 */
	private NearTerm nearTerm;

	/**
	 * Collection or Order objects.
	 */
	private List<Order> orders;

	/**
	 * Gets or sets the last user who updated the ticket group.
	 */
	private User lastUpdatedBy;

	/**
	 * Indicates whether the seat numbers for the tickets are hidden. PATCH operations: replace.
	 */
	private Boolean hideSeats;

	/**
	 * Indicates whether the ticket group is in a pending state. PATCH operations: add, replace, test.
	 */
	private Boolean pending;

	/**
	 * Indicates whether this ticket group is available on Mercury.
	 */
	private Boolean isMercury;

	/**
	 * Indicates whether this ticket group is available on TN Prime.
	 */
	private Boolean isTNPrime;

	/**
	 * Indicates whether this ticket group is short. PATCH operations: replace.
	 */
	private Boolean isShort;

	/**
	 * Indicates whether this ticket group has barcodes for all tickets.
	 */
	private Boolean hasBarcodes;

	/**
	 * Indicates whether this ticket group has an associated purchase order.
	 */
	private Boolean hasPurchaseOrder;

	/**
	 * The purchase order ID(s) for the purchase order(s) associated with this ticket group.
	 */
	private List<Integer> purchaseOrderIds;

	/**
	 * The array of tags associated with the ticket group. PATCH operations:
	 * add, remove, replace, test.
	 */
	private List<Tag> tags;

	/**
	 * Indicates whether the ticket group has QR screenshots available.
	 */
	private Boolean hasQrScreenshots;

	/**
	 * The autopricer settings for this ticket group.
	 */
	private Autopricer autopricer;

	/**
	 * The third party listings associated with this ticket group.
	 */
	private List<ThirdPartyExchangeListing> thirdPartyExchangeListings;

	/**
	 * Indicates whether the owner of this ticket group is a member of the
	 * National Association of Ticket Brokers.
	 */
	private Boolean isNatbBroker;

	/**
	 * The HTTP links for the resource; links are used to transition through
	 * application states by the client and identify related resources available
	 * for interaction.
	 */
	@JsonProperty("_links")
	private List<Link> links;

	public TicketGroup() {
	}

	public TicketGroup(TicketGroupV4PostModel ticketGroupV4PostModel) {
		this.event = new Event(ticketGroupV4PostModel.getEventId());

		this.seats = new Seats();
		this.seats.setSection(ticketGroupV4PostModel.getSection());
		this.seats.setRow(ticketGroupV4PostModel.getRow());
		this.seats.setLowSeat(ticketGroupV4PostModel.getLowSeat());
		this.seats.setStandardSection(ticketGroupV4PostModel.getStandardSection());
		this.seats.setCanonicalSection(ticketGroupV4PostModel.getCanonicalSection());
		this.seats.setSeatingType(new SeatingType(ticketGroupV4PostModel.getSeatingTypeId()));

		this.unitPrice = new UnitPriceGetModel(ticketGroupV4PostModel.getUnitPrice());
		this.isOnHand = ticketGroupV4PostModel.getIsOnHand();
		this.onHandDate = ticketGroupV4PostModel.getOnHandDate();
		this.referenceTicketGroupId = ticketGroupV4PostModel.getReferenceTicketGroupId();
		this.stockType = new StockType(ticketGroupV4PostModel.getStockTypeId());
		this.ticketGroupType = new TicketGroupType(ticketGroupV4PostModel.getTicketGroupTypeId());
		this.splitRule = new SplitRule(ticketGroupV4PostModel.getSplitRuleId());

		Quantity quantity = new Quantity();
		quantity.setTotal(ticketGroupV4PostModel.getQuantity());
		this.quantity = quantity;

		this.broadcastChannelIds = ticketGroupV4PostModel.getBroadcastChannelIds();
		this.notes = ticketGroupV4PostModel.getNotes();

		NearTerm nearTerm = new NearTerm();
		nearTerm.setNearTermDeliveryMethod(new NearTermDeliveryMethod(ticketGroupV4PostModel.getNearTermDeliveryMethodId()));
		nearTerm.setNearTermDisplay(new NearTermDisplay(ticketGroupV4PostModel.getNearTermDisplayId()));

		this.nearTerm = nearTerm;
		this.hideSeats = ticketGroupV4PostModel.getHideSeats();
		this.pending = ticketGroupV4PostModel.getPending();
		this.isShort = ticketGroupV4PostModel.getIsShort();
		this.tags = ticketGroupV4PostModel.getTags();
		this.autopricer = ticketGroupV4PostModel.getAutopricer();
	}

	public Integer getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(Integer brokerId) {
		this.brokerId = brokerId;
	}

	public Integer getTicketGroupId() {
		return ticketGroupId;
	}

	public void setTicketGroupId(Integer ticketGroupId) {
		this.ticketGroupId = ticketGroupId;
	}

	public Integer getExchangeTicketGroupId() {
		return exchangeTicketGroupId;
	}

	public void setExchangeTicketGroupId(Integer exchangeTicketGroupId) {
		this.exchangeTicketGroupId = exchangeTicketGroupId;
	}

	public OffsetDateTime getCreated() {
		return created;
	}

	public void setCreated(OffsetDateTime created) {
		this.created = created;
	}

	public OffsetDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(OffsetDateTime updated) {
		this.updated = updated;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public Seats getSeats() {
		return seats;
	}

	public void setSeats(Seats seats) {
		this.seats = seats;
	}

	public UnitPriceGetModel getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(UnitPriceGetModel unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Boolean getIsOnHand() {
		return isOnHand;
	}

	public void setIsOnHand(Boolean isOnHand) {
		this.isOnHand = isOnHand;
	}

	public LocalDate getOnHandDate() {
		return onHandDate;
	}

	public void setOnHandDate(LocalDate onHandDate) {
		this.onHandDate = onHandDate;
	}

	public Boolean getIsInstant() {
		return isInstant;
	}

	public void setIsInstant(Boolean isInstant) {
		this.isInstant = isInstant;
	}

	public Integer getReferenceTicketGroupId() {
		return referenceTicketGroupId;
	}

	public void setReferenceTicketGroupId(Integer referenceTicketGroupId) {
		this.referenceTicketGroupId = referenceTicketGroupId;
	}

	public StockType getStockType() {
		return stockType;
	}

	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	public TicketGroupType getTicketGroupType() {
		return ticketGroupType;
	}

	public void setTicketGroupType(TicketGroupType ticketGroupType) {
		this.ticketGroupType = ticketGroupType;
	}

	public SplitRule getSplitRule() {
		return splitRule;
	}

	public void setSplitRule(SplitRule splitRule) {
		this.splitRule = splitRule;
	}

	public Quantity getQuantity() {
		return quantity;
	}

	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
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

	public NearTerm getNearTerm() {
		return nearTerm;
	}

	public void setNearTerm(NearTerm nearTerm) {
		this.nearTerm = nearTerm;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public User getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(User lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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

	public Boolean getIsMercury() {
		return isMercury;
	}

	public void setIsMercury(Boolean isMercury) {
		this.isMercury = isMercury;
	}

	public Boolean getIsTNPrime() {
		return isTNPrime;
	}

	public void setIsTNPrime(Boolean isTNPrime) {
		this.isTNPrime = isTNPrime;
	}

	public Boolean getIsShort() {
		return isShort;
	}

	public void setIsShort(Boolean isShort) {
		this.isShort = isShort;
	}

	public Boolean getHasBarcodes() {
		return hasBarcodes;
	}

	public void setHasBarcodes(Boolean hasBarcodes) {
		this.hasBarcodes = hasBarcodes;
	}

	public Boolean getHasPurchaseOrder() {
		return hasPurchaseOrder;
	}

	public void setHasPurchaseOrder(Boolean hasPurchaseOrder) {
		this.hasPurchaseOrder = hasPurchaseOrder;
	}

	public List<Integer> getPurchaseOrderIds() {
		return purchaseOrderIds;
	}

	public void setPurchaseOrderIds(List<Integer> purchaseOrderIds) {
		this.purchaseOrderIds = purchaseOrderIds;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Boolean getHasQrScreenshots() {
		return hasQrScreenshots;
	}

	public void setHasQrScreenshots(Boolean hasQrScreenshots) {
		this.hasQrScreenshots = hasQrScreenshots;
	}

	public Autopricer getAutopricer() {
		return autopricer;
	}

	public void setAutopricer(Autopricer autopricer) {
		this.autopricer = autopricer;
	}

	public List<ThirdPartyExchangeListing> getThirdPartyExchangeListings() {
		return thirdPartyExchangeListings;
	}

	public void setThirdPartyExchangeListings(List<ThirdPartyExchangeListing> thirdPartyExchangeListings) {
		this.thirdPartyExchangeListings = thirdPartyExchangeListings;
	}

	public Boolean getIsNatbBroker() {
		return isNatbBroker;
	}

	public void setIsNatbBroker(Boolean isNatbBroker) {
		this.isNatbBroker = isNatbBroker;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
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
		if (!(obj instanceof TicketGroup)) {
			return false;
		}
		TicketGroup rhs = (TicketGroup) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
