package lk.gov.arogya.models;

public class EpidemicAlert {

    private String UIDHash;
    private String epidemic;
    private String latitude;
    private String longitude;
    private String contactDate;

    public String getUIDHash() {
        return UIDHash;
    }

    public void setUIDHash(final String UIDHash) {
        this.UIDHash = UIDHash;
    }

    public String getEpidemic() {
        return epidemic;
    }

    public void setEpidemic(final String epidemic) {
        this.epidemic = epidemic;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }

    public String getContactDate() {
        return contactDate;
    }

    public void setContactDate(final String contactDate) {
        this.contactDate = contactDate;
    }
}
