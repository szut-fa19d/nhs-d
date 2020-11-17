package model;

public class Caregiver extends Person{
  private long id;
  private String phoneNumber;

  public Caregiver(String firstName, String lastName, String phoneNumber) {
    super(firstName, lastName);
    this.phoneNumber = phoneNumber;
  }

  public Caregiver(long id, String firstName, String lastName, String phoneNumber) {
    super(firstName, lastName);
    this.id = id;
    this.phoneNumber = phoneNumber;
  }

  public long getId() {
    return id; // TODO haben bisher alle Klassen, die von Person extenden, auch. Wie können wir hier DRY befriedigen?
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
