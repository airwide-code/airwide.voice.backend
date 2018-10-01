package com.ebridgevas.vas.dto;

/**
 *
 */
public class IVRResponse {

    private int terminateSession;

    public IVRResponse() {
        this.terminateSession = 1;
    }

    public int getTerminateSession() {
        return terminateSession;
    }

    public void setTerminateSession(int terminateSession) {
        this.terminateSession = terminateSession;
    }
}
