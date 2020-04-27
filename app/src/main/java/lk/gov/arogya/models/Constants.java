package lk.gov.arogya.models;

public class Constants {
    public enum Gender {
        FEMALE("Female"),
        MALE("Male"),
        NON_BINARY("Non Binary");

        private String gender;

        Gender(String name) {
            this.gender = name;
        }

        public String getGender() {
            return gender;
        }

        public static Enum<Gender> getEnum(String value) {
            if (value.equals(FEMALE.getGender())) {
                return FEMALE;
            } else if (value.equals(MALE.getGender())) {
                return MALE;
            } else {
                return NON_BINARY;
            }
        }
    }

    public enum MaritalStatus {
        NOT_MARRIED("Not Married"),
        MARRIED("Married"),
        WIDOWED("Widowed");

        private String status;

        MaritalStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public static Enum<MaritalStatus> getEnum(String value) {
            if (value.equals(NOT_MARRIED.getStatus())) {
                return NOT_MARRIED;
            } else if (value.equals(MARRIED.getStatus())) {
                return MARRIED;
            } else {
                return WIDOWED;
            }
        }
    }

    public enum Disease {
        NONE("None"),
        COVID19("COVID19"),
        INFLUENZA("Influenza"),
        SARS("SARS"),
        EBOLA("Ebola");

        private String diease;

        Disease(String disease) {
            this.diease = disease;
        }

        public String getDiease() {
            return diease;
        }
    }
}
