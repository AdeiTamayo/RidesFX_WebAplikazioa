package eus.ehu.ridesfx.domain;

public class NotLoggedInUser extends User{

    private String userName;
    private String password;


    public NotLoggedInUser(String userName, String password) {
        super(userName, password);
    }

    public NotLoggedInUser() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "NotLoggedInUser{" +
                "userName='" + userName + '\'' +
                '}';
    }

}
