package busy.company;

import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * Company model
 * 
 * @author malkomich
 *
 */
public class Company implements Serializable {

    private static final long serialVersionUID = 7609051478409569105L;

    private int id;
    private String tradeName;
    private String businessName;
    private String email;
    private String cif;
    private boolean active;
    private Category category;
    
    private DateTime createDate;

    public Company() {}

    public Company(String tradeName, String businessName, String email, String cif, Category category) {
        this.tradeName = tradeName;
        this.businessName = businessName;
        this.email = email;
        this.cif = cif;
        this.category = category;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getCif() {
        return cif;
    }

    @SuppressWarnings("unused")
    private void setActive(Boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setCreateDate(DateTime createDate) {
        this.createDate = createDate;
    }

    public DateTime getCreateDate() {
        return createDate;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Integer getCategoryId() {
        return (category != null) ? category.getId() : null;
    }

}
