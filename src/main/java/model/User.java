package model;

public class User {

    private long id;
    private Group group;
    private String username;
    private String password;

    public User() {};

    public User(String username, String password, Group group){
        this.username = username ;
        this.group = group;
        this.password = password;
    }

    public User(long id, String username, String password, Group group){
        this.username = username ;
        this.password = password;
        this.id = id;
        this.group = group;
    }

    public long getGroup(){
        return this.group.getId();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}


