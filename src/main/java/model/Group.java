package model;

public class Group extends DatabaseEntry {
    private String groupName;

    /** @see DatabaseEntry#DatabaseEntry() */
    public Group (String groupName){
        this.groupName = groupName;
    }

    /** @see DatabaseEntry#DatabaseEntry(long) */
    public Group (long id, String groupName){
        super(id);
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return groupName;
    }
}
