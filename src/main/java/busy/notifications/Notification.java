package busy.notifications;

import java.io.Serializable;

import org.joda.time.DateTime;

import busy.user.User;

/**
 * Notification model. It provides a feedback to the user for the events in the application.
 * 
 * @author malkomich
 *
 */
public class Notification implements Serializable {

    private static final long serialVersionUID = 7315210683741955456L;

    public enum Type {
        COMPANY("notification.type.company");

        private String msgCode;

        public String getMsgCode() {
            return msgCode;
        }

        private Type(String msgCode) {
            this.msgCode = msgCode;
        }
    }

    private int id;
    private User user;
    private String type;
    private String message;
    private boolean read;
    private DateTime createDate;

    public Notification() {}

    public Notification(User user, String type, String message) {
        this.user = user;
        this.type = type;
        this.message = message;
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

    public Integer getUserId() {
        return (user != null) ? user.getId(): null;
    }

}
