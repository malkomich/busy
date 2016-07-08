package busy.location;

import java.io.Serializable;

/**
 * Country model.
 * 
 * @author malkomich
 *
 */
public class Country implements Serializable {

    private static final long serialVersionUID = -1791719052398890417L;

    private int id;
    private String name;
    private String code;

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Country() {}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + " [" + code + "]";
    }

}
