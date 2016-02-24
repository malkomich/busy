package busy.notifications;

import java.io.Serializable;

import org.joda.time.DateTime;

public class Notification implements Serializable {

	private static final long serialVersionUID = 7315210683741955456L;

	private int id;
	private int userId;
	private String type;
	private String message;
	private boolean read;
	private DateTime createDate;
	
	public Notification() {
		
	}
	
	public Notification(int userId, String type, String message) {
		
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getUserId() {
		return userId;
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
	
	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
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

}
