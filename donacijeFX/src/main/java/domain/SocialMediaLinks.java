package domain;

public class SocialMediaLinks {
    private String facebook;
    private String twitter;
    private String instagram;

    public SocialMediaLinks(String facebook, String twitter, String instagram) {
        this.facebook = facebook;
        this.twitter = twitter;
        this.instagram = instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
}
