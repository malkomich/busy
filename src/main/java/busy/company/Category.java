package busy.company;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * Category model. It keeps the companies classified.
 * 
 * @author malkomich
 *
 */
public class Category implements Serializable {

    private static final long serialVersionUID = -2844610433599629096L;

    @Expose
    private int id;
    @Expose
    private String name;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
