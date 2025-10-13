package domain;


public class DonorInfo {
    private String city;
    private String street;
    private int postalCode;
    private int houseNumber;
    private String email;
    private int phoneNumber;

    public DonorInfo(){}
    public DonorInfo(String city, String street, int postalCode, int houseNumber, String email, int phoneNumber) {
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.houseNumber = houseNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static class Builder {
        private String city;
        private String street;
        private int postalCode;
        private int houseNumber;
        private String email;
        private int phoneNumber;

        public Builder(String city) {
            this.city = city;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder postalCode(int postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder houseNumber(int houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phoneNumber(int phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public DonorInfo build() {
            DonorInfo donorInfo = new DonorInfo();
            donorInfo.city = this.city;
            donorInfo.street = this.street;
            donorInfo.postalCode = this.postalCode;
            donorInfo.houseNumber = this.houseNumber;
            donorInfo.email = this.email;
            donorInfo.phoneNumber = this.phoneNumber;
            return donorInfo;
        }
    }

}
