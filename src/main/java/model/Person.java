package model;

public abstract class Person extends DatabaseEntry {
    private String firstName;
    private String surname;

    /** @see DatabaseEntry#DatabaseEntry() */
    protected Person(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }
    
    /** @see DatabaseEntry#DatabaseEntry(long) */
    protected Person(long id, String firstName, String surname) {
        super(id);
        this.firstName = firstName;
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
