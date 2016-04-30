package busy.notifications;

import java.io.Serializable;

import org.joda.time.DateTime;

import busy.notifications.messages.MessageCode;
import busy.user.User;

/**
 * Notification model. It provides a feedback to the user for the events in the application.
 * 
 * @author malkomich
 * @param <E>
 *
 */
public class Notification implements Serializable {

    private static final long serialVersionUID = 7315210683741955456L;

    public Notification(Type type, MessageCode messageCode, User user, DateTime createDate, Boolean read) {
        this.type = type;
        this.messageCode = messageCode.getMessageCode();
        this.user = user;
        this.createDate = createDate;
        this.read = read;
    }

    public Notification(Type type, MessageCode messageCode, User user, DateTime createDate) {
        this(type, messageCode, user, createDate, null);
    }

    public Notification(Type type, MessageCode messageCode, User user, Boolean read) {
        this(type, messageCode, user, null, read);
    }

    public Notification(Type type, MessageCode messageCode, User user) {
        this(type, messageCode, user, null, null);
    }

    public Notification() {

    }

    public enum Type {
        COMPANY("notification.type.company");

        private final String code;

        Type(String code) {
            this.code = code;
        }

        String getCode() {
            return code;
        }

        static Type fromCode(String code) {
            switch (code) {
                case "notification.type.company":
                    return Type.COMPANY;

                default:
                    return null;
            }

        }

    }

    private Type type;
    private String messageCode;
    private int id;
    private User user;
    private Boolean read;
    private DateTime createDate;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTypeCode() {
        return (type != null) ? type.getCode() : null;
    }

    public void setMessage(MessageCode messageCode) {
        this.messageCode = messageCode.getMessageCode();
    }

    @SuppressWarnings("unused")
    private void setMessageUnsecured(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean isRead() {
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
        return (user != null) ? user.getId() : null;
    }

}
