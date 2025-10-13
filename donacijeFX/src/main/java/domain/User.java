package domain;

public abstract class User {

    private String userName;
    private String passwordHashCode;
    //private String password;
    public int userId;

    public User(String userName, String passwordHashCode) {
        this.userName = userName;
        this.passwordHashCode = passwordHashCode;
    }

    public User(String userName, String passwordHashCode,int userId) {
        this.userName = userName;
        this.passwordHashCode = passwordHashCode;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPasswordHashCode() {
        return passwordHashCode;
    }

    public void setName(String userName) {
        this.userName = userName;
    }

    public void setPasswordHashCode(String password) {
        this.passwordHashCode = passwordHashCode;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
