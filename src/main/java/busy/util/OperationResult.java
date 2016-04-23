package busy.util;

import java.io.Serializable;

public class OperationResult implements Serializable {

    private static final long serialVersionUID = -8305353140690381781L;

    private boolean success;
    private ResultCode code;
    private String errorMsg;

    public enum ResultCode {
        OK,
        SERVICE_TYPE_WITH_BOOKINGS("service-type.error.bookings"),
        NOT_EXISTS("service-type.error.not-exists");
        
        private String msgCode;
        
        private ResultCode() {
            this.msgCode = null;
        }
        
        private ResultCode(String msgCode) {
            this.msgCode = msgCode;
        }
        
        public String getMsgCode() {
            return msgCode;
        }
    }

    public OperationResult(ResultCode code) {
        this.code = code;
        success = (code == ResultCode.OK);
    }

    public boolean isSuccess() {
        return success;
    }

    public ResultCode getCode() {
        return code;
    }
    
    public String getErrorMsg() {
        return errorMsg;
    }
    
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
