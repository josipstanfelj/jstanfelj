package domain;


public class Organization extends User {

    private EmployerIdentificationNumber employerIdentificationNumber;
    private OrganizationInfo organizationInfo;
    private SocialMediaLinks socialMediaLinks;
    private EstablishmentInfo establishmentInfo;

    public Organization(String userName, String passwordHashCode, EmployerIdentificationNumber employerIdentificationNumber, OrganizationInfo organizationInfo, SocialMediaLinks socialMediaLinks, EstablishmentInfo establishmentInfo, int userId) {
        super(userName, passwordHashCode, userId);
        this.employerIdentificationNumber = employerIdentificationNumber;
        this.organizationInfo = organizationInfo;
        this.socialMediaLinks = socialMediaLinks;
        this.establishmentInfo = establishmentInfo;
    }

    public EmployerIdentificationNumber getEmployerIdentificationNumber() {
        return employerIdentificationNumber;
    }

    public void setEmployerIdentificationNumber(EmployerIdentificationNumber employerIdentificationNumber) {
        this.employerIdentificationNumber = employerIdentificationNumber;
    }

    public OrganizationInfo getOrganizationInfo() {
        return organizationInfo;
    }

    public void setOrganizationInfo(OrganizationInfo organizationInfo) {
        this.organizationInfo = organizationInfo;
    }

    public SocialMediaLinks getSocialMediaLinks() {
        return socialMediaLinks;
    }

    public void setSocialMediaLinks(SocialMediaLinks socialMediaLinks) {
        this.socialMediaLinks = socialMediaLinks;
    }

    public EstablishmentInfo getEstablishmentInfo() {
        return establishmentInfo;
    }

    public void setEstablishmentInfo(EstablishmentInfo establishmentInfo) {
        this.establishmentInfo = establishmentInfo;
    }
}
