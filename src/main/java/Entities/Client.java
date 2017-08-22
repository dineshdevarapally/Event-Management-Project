package Entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
	@NamedQuery(query = "SELECT c "+" FROM Client c", name = "get clients")
})
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	private String firstname;
	@NotNull
	private String lastname;
	@NotNull
	private String phonenumber;
	@NotNull
	private String address;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE} , mappedBy ="client", orphanRemoval=true)
    private List<Event> events;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Client(){
		super();
	}
	public Client(String firstname, String lastname, String phonenumber, String address) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.phonenumber = phonenumber;
		this.address = address;
	}
}