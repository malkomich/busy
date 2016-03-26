package busy.user.web;

/**
 * Form model to handle the POST request for login with an existing user account.
 * 
 * @author malkomich
 *
 */
public class LoginForm {

    // Fields
    
    private String email;

    private String pasword;

    // Getters and Setters

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

}
