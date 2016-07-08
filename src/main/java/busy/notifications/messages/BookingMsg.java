package busy.notifications.messages;

public enum BookingMsg implements MessageCode {
    BOOKING_SUCCESS("notification.message.booking.complete");

    String code;

    BookingMsg(String code) {
        this.code = code;
    }

    @Override
    public String getMessageCode() {
        return code;
    }

}
