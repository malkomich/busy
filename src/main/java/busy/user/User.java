package busy.user;

import java.io.Serializable;

import busy.location.Address;

public class User implements Serializable {

	private static final long serialVersionUID = 4782710436663959256L;

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String pasword;
	private String nif;
	private Address address;
	private String phone;
	private Boolean active;
	private Boolean admin;

	public User(String firstName, String lastName, String email,
			String password, String nif, String phone,
			Boolean active, Boolean admin, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.pasword = password;
		this.nif = nif;
		this.phone = phone;
		this.active = (active != null) ? active : true;
		this.admin = (admin != null) ? admin : false;
		this.address = address;
	}

	public User() {

	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPassword(String password) {
		this.pasword = password;
	}

	public String getPassword() {
		return pasword;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNif() {
		return nif;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setActive(Boolean active) {
		this.active = (active != null) ? active : true;
	}

	public Boolean isActive() {
		return active;
	}

	@SuppressWarnings("unused")
	private void setAdmin(Boolean admin) {
		this.admin = (admin != null) ? admin : false;
	}

	public Boolean isAdmin() {
		return admin;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}

	@SuppressWarnings("unused")
	private void setId(Integer id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName + " (" + email + ")";
	}

	public Integer getAddressId() {
		return (address != null) ? address.getId() : null;
	}

}
