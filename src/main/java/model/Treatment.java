package model;

import utils.DateConverter;
import java.time.LocalDate;
import java.time.LocalTime;

public class Treatment {
    private long id;
    private long patientId;
    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;
    private String description;
    private String remarks;

    public Treatment(long patientId, LocalDate date, LocalTime begin, LocalTime end, String description, String remarks) {
        this.init(patientId, date, begin, end, description, remarks);
    }

    public Treatment(long id, long patientId, LocalDate date, LocalTime begin, LocalTime end, String description, String remarks) {
        this.id = id;
        this.init(patientId, date, begin, end, description, remarks);
    }

    private void init(long patientId, LocalDate date, LocalTime begin, LocalTime end, String description, String remarks) {
        this.patientId = patientId;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
    }

    public long getId() {
        return id;
    }

    public long getPatientId() {
        return patientId;
    }

    public String getDate() {
        return date.toString();
    }

    public String getBegin() {
        return begin.toString();
    }

    public String getEnd() {
        return end.toString();
    }

    public void setDate(String s_date) {
        this.date = DateConverter.convertStringToLocalDate(s_date);
    }

    public void setBegin(String begin) {
        this.begin = DateConverter.convertStringToLocalTime(begin);
    }

    public void setEnd(String end) {
        this.end = DateConverter.convertStringToLocalTime(end);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @deprecated Habe noch keine Stelle gefunden, wo diese Klasse stringified wird
     */
    public String toString() {
        return "\nBehandlung" + "\nTID: " + id +
                "\nPID: " + patientId +
                "\nDate: " + date +
                "\nBegin: " + begin +
                "\nEnd: " + end +
                "\nDescription: " + description +
                "\nRemarks: " + remarks + "\n";
    }
}