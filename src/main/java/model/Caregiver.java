package model;

public class Caregiver extends Person {
  private String phoneNumber;

  public Caregiver(String firstName, String lastName, String phoneNumber) {
    super(firstName, lastName);
    this.phoneNumber = phoneNumber;
  }

  public Caregiver(long id, String firstName, String lastName, String phoneNumber) {
    super(id, firstName, lastName);
    this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return this.getFirstName() + " " + this.getLastName();
  }
}
