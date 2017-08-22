package Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
	@NamedQuery(query = "SELECT e" + " FROM Event e ", name = "get all events"),
})
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int eventId;
	@NotNull
	private String eventName;
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
	@NotNull
	private String venue;
	@NotNull
	private int nSeats;
	@NotNull
	private int empytSeats;
	@ManyToOne
	private Client client;
	@NotNull
	private String fileLocation;
	@NotNull
	private int nGen;
	@Column(nullable = true)
	private int score;
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getnGen() {
		return nGen;
	}
	public void setnGen(int nGen) {
		this.nGen = nGen;
	}
	public List<Guest> getEventGuests() {
		return eventGuests;
	}
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE} , mappedBy ="event",orphanRemoval=true)
    private List<Guest> eventGuests;
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public int getnSeats() {
		return nSeats;
	}
	public void setnSeats(int nSeats) {
		this.nSeats = nSeats;
	}
	public int getEmpytSeats() {
		return empytSeats;
	}
	public void setEmpytSeats(int empytSeats) {
		this.empytSeats = empytSeats;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public void setEventGuests(List<Guest> eventGuests) {
		this.eventGuests = eventGuests;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
}