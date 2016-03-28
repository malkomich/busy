package busy.user;

import java.io.Serializable;

/**
 * Verification model. Useful to keep a security level in the sign up process of a new User.
 * 
 * @author malkomich
 *
 */
public class Verification implements Serializable {

    private static final long serialVersionUID = 6827550784446892114L;

    private User user;

    private String token;

    public Verification() {}

    public Verification(String token, User user) {
        super();
        this.token = token;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
