package busy.user;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import busy.location.Address;

/**
 * User model. It holds the data of the people using the application.
 * 
 * @author malkomich
 *
 */
public class User implements Serializable {

    private static final long serialVersionUID = 4782710436663959256L;

    private int id;
    
    @NotEmpty(message = "{firstname.required}")
    @Length(max = 35, message = "{firstname.maxlength}")
    private String firstName;
    
    @NotEmpty(message = "{lastname.required}")
    @Length(max = 35, message = "{lastname.maxlength}")
    private String lastName;
    
    @Email(message = "{email.wrong_format}")
    @NotEmpty(message = "{email.required}")
    @Length(max = 50, message = "{email.maxlength}")
    private String email;
    private String pasword;
    private String nif;
    private Address address;
    
    @Length(min = 8, max = 12, message = "{phone.length}")
    @Pattern(regexp = "[0-9]*", message = "{phone.wrong_format}")
    private String phone;
    private Boolean active;
    private Boolean admin;

    public User(String firstName, String lastName, String email, String password, String nif, String phone,
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

    public User() {}

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
        
        if (phone != "")
            this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setActive(Boolean active) {
        this.active = (active != null) ? active : true;
    }

    public Boolean isActive() {
        return (active != null) ? active : false;
    }

    @SuppressWarnings("unused")
    private void setAdmin(Boolean admin) {
        this.admin = (admin != null) ? admin : false;
    }

    public Boolean isAdmin() {
        return (admin != null) ? admin : false;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public Integer getAddressId() {
        return (address != null) ? address.getId() : null;
    }

    private long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof User && ((User) object).getSerialVersionUID() == serialVersionUID
            && ((User) object).getId() == id) {

            return true;
        }

        return false;

    }

}
