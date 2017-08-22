package Entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"username"})})
@NamedQueries({
	@NamedQuery(query = "Select e "+" FROM Employee e where e.username = :username and e.password = :password", name = "login query"),
	@NamedQuery(query = "SELECT e "+" FROM Employee e", name = "get employees")
})
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String firstname;
	@NotNull
	private String lastname;
	@NotNull
	private String dob;
	@NotNull
	private String contact;
	@NotNull
	private String gender;
	@NotNull
	private String role;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Employee() {
		
	}
	public Employee(String username, String password, String firstname, String lastname, String dob,
			String contact, String gender, String role) {
		super();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dob = dob;
		this.contact = contact;
		this.gender = gender;
		this.role = role;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", username=" + username + ", password=" + password + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", dob=" + dob + ", contact=" + contact + ", gender=" + gender + "]";
	}
}