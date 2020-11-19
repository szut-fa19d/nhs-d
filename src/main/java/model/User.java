package model;

public class User {
    private Group group;
    private String username;
    private String password;

    public User(String username, String password, Group group){
        this.username = username ;
        this.group = group;
        this.password = password;
    }

    public Group getGroup(){
        return this.group;
    }

    public void setGroup (Group group){
        this.group = group;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


