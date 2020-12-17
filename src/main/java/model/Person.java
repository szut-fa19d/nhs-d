package model;

public abstract class Person extends DatabaseEntry {
    private String firstName;
    private String lastName;

    /** @see DatabaseEntry#DatabaseEntry() */
    protected Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /** @see DatabaseEntry#DatabaseEntry(long) */
    protected Person(long id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
