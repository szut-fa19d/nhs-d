package model;

public abstract class DatabaseEntry {
  protected long id;

  /**
   * Constructor for new items that don't exist in the database
   */
  protected DatabaseEntry() {}
  
  /**
   * Constructor for items that exist in the database
   */
  protected DatabaseEntry(long id) {
    this.id = id;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
