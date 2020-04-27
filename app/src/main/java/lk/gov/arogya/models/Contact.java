package lk.gov.arogya.models;

import java.util.Date;
import lk.gov.arogya.models.Constants.Disease;

public class Contact {

    private String contactedUIDHash;
    private Date foundTimestamp;
    private Date lostTimestamp;
    private Disease disease;

    public Contact(String contactedUIDHash) {
        this.contactedUIDHash = contactedUIDHash;
        this.disease = Disease.NONE;
    }

    public String getContactedUIDHash() {
        return contactedUIDHash;
    }

    public void setContactedUIDHash(final String contactedUIDHash) {
        this.contactedUIDHash = contactedUIDHash;
    }

    public Date getFoundTimestamp() {
        return foundTimestamp;
    }

    public void setFoundTimestamp(final Date foundTimestamp) {
        this.foundTimestamp = foundTimestamp;
    }

    public Date getLostTimestamp() {
        return lostTimestamp;
    }

    public void setLostTimestamp(final Date lostTimestamp) {
        this.lostTimestamp = lostTimestamp;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(final Disease disease) {
        this.disease = disease;
    }
}
