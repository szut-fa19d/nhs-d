package model;

import utils.DateConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patients live in a NURSING home and are treated by nurses.
 */
public class Patient extends Person {
    private long id;
    private LocalDate dateOfBirth;
    private String careLevel;
    private String roomnumber;
    private String assets;
    private List<Treatment> allTreatments = new ArrayList<>();

    public Patient(String firstName, String surname, LocalDate dateOfBirth, String careLevel, String roomnumber, String assets) {
        super(firstName, surname);
        this.init(dateOfBirth, careLevel, roomnumber, assets);
    }

    public Patient(long id, String firstName, String surname, LocalDate dateOfBirth, String careLevel, String roomnumber, String assets) {
        super(firstName, surname);
        this.id = id;
        this.init(dateOfBirth, careLevel, roomnumber, assets);
    }

    private void init(LocalDate dateOfBirth, String careLevel, String roomnumber, String assets) {
        this.dateOfBirth = dateOfBirth;
        this.careLevel = careLevel;
        this.roomnumber = roomnumber;
        this.assets = assets;
    }

    public long getId() {
        return id;
    }

    public String getDateOfBirth() {
        return dateOfBirth.toString();
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * convert given param to a localDate and store as new <code>birthOfDate</code>
     * @param dateOfBirth as string in the following format: YYYY-MM-DD
     */
    public void setDateOfBirth(String dateOfBirth) {
        LocalDate birthday = DateConverter.convertStringToLocalDate(dateOfBirth);
        this.dateOfBirth = birthday;
    }

    public String getCareLevel() {
        return careLevel;
    }

    public void setCareLevel(String newCareLevel) {
        this.careLevel = newCareLevel;
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber;
    }

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    /**
     * adds a treatment to the treatment-list, if it does not already contain it.
     * @return true if the treatment was not already part of the list. otherwise false
     */
    public boolean add(Treatment m) {
        if (!this.allTreatments.contains(m)) {
            this.allTreatments.add(m);
            return true;
        }

        return false;
    }

    public String toString() {
        return "Patient" + "\nMNID: " + this.id +
                "\nFirstname: " + this.getFirstName() +
                "\nSurname: " + this.getSurname() +
                "\nBirthday: " + this.dateOfBirth +
                "\nCarelevel: " + this.careLevel +
                "\nRoomnumber: " + this.roomnumber +
                "\nAssets: " + this.assets +
                "\n";
    }
}