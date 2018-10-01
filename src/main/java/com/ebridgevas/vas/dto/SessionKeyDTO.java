package com.ebridgevas.vas.dto;

/**
 * @author david@tekeshe.com
 */
public class SessionKeyDTO {
    protected final String callId; // = agiRequest.getParameter("call_id");
    protected final String calledId; // = "789"; //agiRequest.getParameter("called_id");
    protected final String callerId;//  = agiRequest.getParameter("caller_id");

    public SessionKeyDTO(
            String callerId,
            String calledId,
            String callId) {
        this.callerId = callerId;
        this.calledId = calledId;
        this.callId = callId;
    }

    public String getCallId() {
        return callId;
    }

    public String getCalledId() {
        return calledId;
    }

    public String getCallerId() {
        return callerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionKeyDTO that = (SessionKeyDTO) o;

        if (callId != null ? !callId.equals(that.callId) : that.callId != null) return false;
        if (calledId != null ? !calledId.equals(that.calledId) : that.calledId != null) return false;
        return !(callerId != null ? !callerId.equals(that.callerId) : that.callerId != null);

    }

    @Override
    public int hashCode() {
        int result = callId != null ? callId.hashCode() : 0;
        result = 31 * result + (calledId != null ? calledId.hashCode() : 0);
        result = 31 * result + (callerId != null ? callerId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SessionKeyDTO{" +
                "callId='" + callId + '\'' +
                ", calledId='" + calledId + '\'' +
                ", callerId='" + callerId + '\'' +
                '}';
    }
}
