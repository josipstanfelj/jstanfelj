package domain;

import java.time.LocalDate;

public class EstablishmentInfo {
    private String founderName;
    private LocalDate foundationDate;
    private String industry;

    public EstablishmentInfo(String founderName, LocalDate foundationDate, String industry) {
        this.founderName = founderName;
        this.foundationDate = foundationDate;
        this.industry = industry;
    }

    public String getFounderName() {
        return founderName;
    }

    public void setFounderName(String founderName) {
        this.founderName = founderName;
    }

    public LocalDate getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(LocalDate foundationDate) {
        this.foundationDate = foundationDate;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
