package model;

import utils.DateConverter;
import java.time.LocalDate;
import java.time.LocalTime;

public class Treatment extends DatabaseEntry {
    private Patient patient;
    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;
    private String description;
    private String remarks;

    /** @see DatabaseEntry#DatabaseEntry() */
    public Treatment(Patient patient, LocalDate date, LocalTime begin, LocalTime end, String description, String remarks) {
        this.init(patient, date, begin, end, description, remarks);
    }
    
    /** @see DatabaseEntry#DatabaseEntry(long) */
    public Treatment(long id, Patient patient, LocalDate date, LocalTime begin, LocalTime end, String description, String remarks) {
        super(id);
        this.init(patient, date, begin, end, description, remarks);
    }

    /**
     * Assignements that the constructors have in common
     */
    private void init(Patient patient, LocalDate date, LocalTime begin, LocalTime end, String description, String remarks) {
        this.patient = patient;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
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

    public void setDate(String newDate) {
        this.date = DateConverter.convertStringToLocalDate(newDate);
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
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @deprecated Habe noch keine Stelle gefunden, wo diese Klasse stringified wird
     */
    @Deprecated(forRemoval=true)
    public String toString() {
        return "\nBehandlung" + "\nTID: " + id +
                "\nPID: " + patient.getId() +
                "\nDate: " + date +
                "\nBegin: " + begin +
                "\nEnd: " + end +
                "\nDescription: " + description +
                "\nRemarks: " + remarks + "\n";
    }
}