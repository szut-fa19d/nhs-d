package model;

public class LogEntry extends DatabaseEntry {
    private String timestamp;
    private String type;
    private long objId;
    private long userId;
    private String desc;

    public LogEntry(String type, long objId, long userId, String desc) {
        this.type = type;
        this.objId = objId;
        this.userId = userId;
        this.desc = desc;
    }

    public LogEntry(long id, String timestamp, String type, long objId, long userId, String desc) {
        super(id);
        this.timestamp = timestamp;
        this.type = type;
        this.objId = objId;
        this.userId = userId;
        this.desc = desc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getObjId() {
        return objId;
    }

    public void setObjId(long objId) {
        this.objId = objId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
