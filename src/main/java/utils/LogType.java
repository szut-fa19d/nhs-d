package utils;

public enum LogType {
    PATIENT("Patient"),
    TREATMENT("Treatment");

    private String type;

    LogType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
