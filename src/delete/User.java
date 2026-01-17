package delete;

public class User {
    protected String userID;
    protected String name;
    protected String role;

    public User(String userID, String name, String role) {
        this.userID = userID;
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }
}

