package busy.notifications;

import java.io.Serializable;

import org.joda.time.DateTime;

import busy.user.User;

public class Notification implements Serializable {

	private static final long serialVersionUID = 7315210683741955456L;

	private int id;
	private User user;
	private String type;
	private String message;
	private boolean read;
	private DateTime createDate;
	
	public Notification() {
		
	}
	
	public Notification(User user, String type, String message) {
		
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setRead(boolean read) {
		this.read = read;
	}
	
	public boolean isRead() {
		return read;
	}
	
	public DateTime getCreateDate() {
		return createDate;
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
	
}
