package model;

import utils.DateConverter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patients live in a NURSING home and are treated by nurses.
 */
public class Patient extends Person {
    private LocalDate dateOfBirth;
    private String careLevel;
    private String roomnumber;
    private String assets;
    private Boolean locked;
    private List<Treatment> allTreatments = new ArrayList<>();

    /** @see Person#Person(String, String) */
    public Patient(
        String firstName,
        String surname,
        LocalDate dateOfBirth,
        String careLevel,
        String roomnumber,
        String assets,
        Boolean locked
    ) {
        super(firstName, surname);
        this.init(dateOfBirth, careLevel, roomnumber, assets, locked);
    }

    /** @see Person#Person(long, String, String) */
    public Patient( // NOSONAR
        long id,
        String firstName,
        String surname,
        LocalDate dateOfBirth,
        String careLevel,
        String roomnumber,
        String assets,
        Boolean locked
    ) {
        super(id, firstName, surname);
        this.init(dateOfBirth, careLevel, roomnumber, assets, locked);
    }

    /**
     * This method holds the assignments that both constructors have in common
     */
    private void init(LocalDate dateOfBirth, String careLevel, String roomnumber, String assets, boolean locked) {
        this.dateOfBirth = dateOfBirth;
        this.careLevel = careLevel;
        this.roomnumber = roomnumber;
        this.assets = assets;
        this.locked = locked;
    }

    /**
     * @see LocalDate#toString()
     */
    public String getDateOfBirth() {
        return dateOfBirth.toString();
    }

    /**
     * convert given param to a localDate and store as new <code>birthOfDate</code>
     * @param dateOfBirth as string in the following format: YYYY-MM-DD
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = DateConverter.convertStringToLocalDate(dateOfBirth);
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

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean getLocked() {
        return locked;
    }
    
    /**
     * adds a treatment to the treatment-list, if it does not already contain it.
     * @return true if the treatment was not already part of the list. otherwise false
     */
    public boolean add(Treatment treatment) {
        if (this.allTreatments.contains(treatment)) {
            return false;
        }

        this.allTreatments.add(treatment);
        return true;
    }

    public String toString() {
        return "Patient" + "\nMNID: " + this.id +
                "\nFirstname: " + this.getFirstName() +
                "\nLastname: " + this.getLastName() +
                "\nBirthday: " + this.dateOfBirth +
                "\nCarelevel: " + this.careLevel +
                "\nRoomnumber: " + this.roomnumber +
                "\nAssets: " + this.assets +
                "\nLocked: " + this.locked +
                "\n";
    }
}