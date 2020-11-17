package model;

public class User {
    private Group group;
    private String username;

    public User(String username, Group group){
        this.username = username ;
        this.group = group;
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


