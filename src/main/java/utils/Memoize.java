package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Memoize<T> {
  private List<T> storage;
  private Callable<List<T>> loader;

  public Memoize(Callable<List<T>> f) {
    this.loader = f;
    this.storage = new ArrayList<>();
  }

  public List<T> get() throws SQLException {
    if (this.storage.isEmpty()) {
      try {
        this.storage = this.loader.call();
      } catch (Exception exception) { // TODO Vielleicht doch kein Callable benutzen, da es Exception werfen kann
        // TODO Auto-generated catch block
        exception.printStackTrace();
      }
    }

    return this.storage;
  }
}
