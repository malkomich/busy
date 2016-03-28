package busy.role;

import java.io.Serializable;

import busy.company.Branch;
import busy.user.User;

/**
 * Role model. It joins a Company with users working on it.
 * 
 * @author malkomich
 *
 */
public class Role implements Serializable {

    private static final long serialVersionUID = 6259212594249963055L;

    private int id;
    private User user;
    private Branch branch;
    private Boolean manager;
    private String activity;

    public Role() {}

    public Role(User user, Branch branch, String activity) {
        this.user = user;
        this.branch = branch;
        this.activity = activity;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Branch getBranch() {
        return branch;
    }

    @SuppressWarnings("unused")
    private void setManager(Boolean manager) {
        this.manager = manager;
    }

    public Boolean isManager() {
        return manager;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getActivity() {
        return activity;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Integer getUserId() {
        return (user != null) ? user.getId() : null;
    }

    public Integer getBranchId() {
        return (branch != null) ? branch.getId() : null;
    }

}
