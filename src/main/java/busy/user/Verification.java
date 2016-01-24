package busy.user;

public class Verification {
	
    private User user;
    
    private String token;
     
    public Verification() {
        super();
    }
    
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
