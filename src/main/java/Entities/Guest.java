package Entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
	@NamedQuery(query = "SELECT g"+" FROM Guest g WHERE g.event.eventId=:eventId and g.eventGuestNumber=:eventGuestNumber", name = "get guest with order")
})
public class Guest implements Serializable {
	public int getGuestId() {
		return guestId;
	}
	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getEventGuestNumber() {
		return eventGuestNumber;
	}
	public void setEventGuestNumber(Integer eventGuestNumber) {
		this.eventGuestNumber = eventGuestNumber;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public List<Rule> getMainRules() {
		return mainRules;
	}
	public List<Rule> getSubRules() {
		return subRules;
	}
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int guestId;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private Integer eventGuestNumber;
	@ManyToOne(cascade=CascadeType.REMOVE)
	private Event event;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE} , mappedBy ="mainGuest", orphanRemoval=true)
    private List<Rule> mainRules;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE} , mappedBy ="subGuest", orphanRemoval=true)
    private List<Rule> subRules;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE} , mappedBy ="guest", orphanRemoval=true)
    private List<Output> output;
}