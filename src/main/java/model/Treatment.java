package model;

import utils.DateConverter;
import java.time.LocalDate;
import java.time.LocalTime;

public class Treatment {
    private long id;
    private Patient patient;
    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;
    private String description;
    private String remarks;
    private Boolean locked;

    public Treatment(Patient patient, LocalDate date, LocalTime begin, LocalTime end, String description, String remarks,Boolean locked) {
        this.init(patient, date, begin, end, description, remarks, locked);
    }

    public Treatment(long id, Patient patient, LocalDate date, LocalTime begin, LocalTime end, String description, String remarks,Boolean locked) {
        this.id = id;
        this.init(patient, date, begin, end, description, remarks, locked);
    }

    private void init(Patient patient, LocalDate date, LocalTime begin, LocalTime end, String description, String remarks,Boolean locked) {
        this.patient = patient;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
        this.locked = locked;
    }

    public long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
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

    public void setLocked(Boolean locked) { this.locked = locked; }

    public Boolean getLocked() { return locked; }

    /**
     * @deprecated Habe noch keine Stelle gefunden, wo diese Klasse stringified wird
     */
    public String toString() {
        return "\nBehandlung" + "\nTID: " + id +
                "\nPID: " + patient.getId() +
                "\nDate: " + date +
                "\nBegin: " + begin +
                "\nEnd: " + end +
                "\nDescription: " + description +
                "\nRemarks: " + remarks +
                "\nLocked: " + locked +
                "\n";
    }
}