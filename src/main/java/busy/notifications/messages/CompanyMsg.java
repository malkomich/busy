package busy.notifications.messages;

public enum CompanyMsg implements MessageCode {

    COMPANY_PENDING("notification.message.company.pending"),
    COMPANY_APPROVED("notification.message.company.approved"),
    COMPANY_REJECTED("notification.message.company.rejected");

    String code;

    CompanyMsg(String code) {
        this.code = code;
    }

    @Override
    public String getMessageCode() {
        return code;
    }
}
