package model;

public class Group {
    private long id;
    private String groupName;

    public Group (String groupName){
        this.groupName = groupName;
    }

    public Group (long id, String groupName){
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
