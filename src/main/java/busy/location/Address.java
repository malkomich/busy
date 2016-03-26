package busy.location;

import java.io.Serializable;

/**
 * Address model.
 * 
 * @author malkomich
 *
 */
public class Address implements Serializable {

    private static final long serialVersionUID = -843704810974065837L;

    private int id;
    private String address1;
    private String address2;
    private String zipCode;
    private City city;

    public Address(String address1, String address2, String zipCode, City city) {
        this.address1 = address1;
        this.address2 = address2;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Address() {}

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress2() {
        return address2;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
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
        return address1 + " " + address2 + ", " + zipCode + " " + city.toString();
    }

    public Integer getCityId() {
        return (city != null) ? city.getId() : null;
    }

}
