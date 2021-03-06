package busy.company;

import java.io.Serializable;

import busy.location.Address;

/**
 * Branch model. Companies will have at least one(the headquarters).
 * 
 * @author malkomich
 */
public class Branch implements Serializable {

    private static final long serialVersionUID = -4918502964124607207L;

    private int id;
    private Company company;
    private Address address;
    private Boolean headquarters;
    private String phone;

    public Branch() {}

    public Branch(Company company, Address address, String phone) {
        this.company = company;
        this.address = address;
        this.phone = phone;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    @SuppressWarnings("unused")
    private void setHeadquarters(Boolean headquarters) {
        this.headquarters = headquarters;
    }

    public Boolean isHeadquarters() {
        return headquarters;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Integer getCompanyId() {
        return (company != null) ? company.getId() : null;
    }

    public Integer getAddressId() {
        return (address != null) ? address.getId() : null;
    }

}
