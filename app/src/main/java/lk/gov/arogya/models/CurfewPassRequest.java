package lk.gov.arogya.models;

import lk.gov.arogya.models.Constants.Approval;

public class CurfewPassRequest {

    private int requestID;
    private String requestedFor;
    private String requestedBy;
    private String whereTo;
    private String reasonOfRequest;
    private String validDateFrom;
    private String validDateTo;
    private String inspectedBy;
    private String inspectedOn;
    private Approval status;

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(final int requestID) {
        this.requestID = requestID;
    }

    public String getRequestedFor() {
        return requestedFor;
    }

    public void setRequestedFor(final String requestedFor) {
        this.requestedFor = requestedFor;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(final String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getWhereTo() {
        return whereTo;
    }

    public void setWhereTo(final String whereTo) {
        this.whereTo = whereTo;
    }

    public String getReasonOfRequest() {
        return reasonOfRequest;
    }

    public void setReasonOfRequest(final String reasonOfRequest) {
        this.reasonOfRequest = reasonOfRequest;
    }

    public String getValidDateFrom() {
        return validDateFrom;
    }

    public void setValidDateFrom(final String validDateFrom) {
        this.validDateFrom = validDateFrom;
    }

    public String getValidDateTo() {
        return validDateTo;
    }

    public void setValidDateTo(final String validDateTo) {
        this.validDateTo = validDateTo;
    }

    public String getInspectedBy() {
        return inspectedBy;
    }

    public void setInspectedBy(final String inspectedBy) {
        this.inspectedBy = inspectedBy;
    }

    public String getInspectedOn() {
        return inspectedOn;
    }

    public void setInspectedOn(final String inspectedOn) {
        this.inspectedOn = inspectedOn;
    }

    public Approval getStatus() {
        return status;
    }

    public void setStatus(final Approval status) {
        this.status = status;
    }
}
