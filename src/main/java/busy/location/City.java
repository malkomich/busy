package busy.location;

import java.io.Serializable;

/**
 * City model.
 * 
 * @author malkomich
 *
 */
public class City implements Serializable {

    private static final long serialVersionUID = -8284061335821188200L;

    private int id;
    private String name;
    private Country country;

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public City() {}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + " (" + country.getName() + ")";
    }

    public Integer getCountryId() {
        return (country != null) ? country.getId() : null;
    }

}
