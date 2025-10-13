package domain;


public class OrganizationInfo{
    private String city;
    private String street;
    private int postalCode;
    private String state;
    private String email;

    public OrganizationInfo(String city, String street, int postalCode, String state, String email) {
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.state = state;
        this.email = email;
    }
    private OrganizationInfo(){}

    public static class Builder{
        private String city;
        private String street;
        private int postalCode;
        private String state;
        private String email;
        public Builder(String city){
            this.city = city;
        }
        public Builder withStreet(String street){
            this.street = street;

            return this;
        }
        public Builder withPostalCode(int postalCode){
            this.postalCode = postalCode;

            return this;
        }
        public Builder withState(String state){
            this.state = state;

            return this;
        }
        public Builder withEmail(String email){
            this.email = email;

            return this;
        }

        public OrganizationInfo build(){
            OrganizationInfo organizationInfo = new OrganizationInfo();
            organizationInfo.city = this.city;
            organizationInfo.street = this.street;
            organizationInfo.postalCode = this.postalCode;
            organizationInfo.state = this.state;
            organizationInfo.email = this.email;
            return organizationInfo;
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
