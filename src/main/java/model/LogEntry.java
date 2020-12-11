package model;

public class LogEntry {
    private long id;
    private long timestamp;
    private String type;
    private long obj_id;
    private long user_id;
    private String desc;

    public LogEntry(String type, long obj_id, int user_id, String desc) {
        this.type = type;
        this.obj_id = obj_id;
        this.user_id = user_id;
        this.desc = desc;
    }

    public LogEntry(long id, long timestamp, String type, long obj_id, int user_id, String desc) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.obj_id = obj_id;
        this.user_id = user_id;
        this.desc = desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getObj_id() {
        return obj_id;
    }

    public void setObj_id(long obj_id) {
        this.obj_id = obj_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
