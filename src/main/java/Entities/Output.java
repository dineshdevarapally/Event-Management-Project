package Entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
	@NamedQuery(query = "SELECT o" + " FROM Output o WHERE o.event.eventId=:eventId", name = "get output of event"),
})
public class Output {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int outputId;
	@ManyToOne(cascade=CascadeType.REMOVE)
	private Event event;
	@NotNull
	private int tableNumber;
	@ManyToOne(cascade=CascadeType.REMOVE)
	private Guest guest;
	public int getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	public Guest getGuest() {
		return guest;
	}
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	public int getOutputId() {
		return outputId;
	}
	public void setOutputId(int outputId) {
		this.outputId = outputId;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
}
