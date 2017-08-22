package Entities;

import java.io.Serializable;

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
	@NamedQuery(query = "SELECT r "+" FROM Rule r WHERE r.event.eventId=:eventId", name = "get rules with event id")
})
public class Rule implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ruleId;
	@ManyToOne
	private Guest mainGuest;
	@ManyToOne
	private Guest subGuest;
	@ManyToOne
	private Event event;
	@NotNull
	private Integer score;
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public Guest getMainGuest() {
		return mainGuest;
	}
	public void setMainGuest(Guest mainGuest) {
		this.mainGuest = mainGuest;
	}
	public Guest getSubGuest() {
		return subGuest;
	}
	public void setSubGuest(Guest subGuest) {
		this.subGuest = subGuest;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
}