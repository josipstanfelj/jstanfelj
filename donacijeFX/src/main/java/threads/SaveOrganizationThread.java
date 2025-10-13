package threads;

import domain.Organization;

public class SaveOrganizationThread extends DatabaseThread implements Runnable {
    public Organization organization;

    public SaveOrganizationThread(Organization organization){
        this.organization = organization;
    }
    @Override
    public void run() {
        super.saveOrganization(organization);
    }
}
