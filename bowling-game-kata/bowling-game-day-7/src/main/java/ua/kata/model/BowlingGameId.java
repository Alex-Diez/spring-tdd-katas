package ua.kata.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class BowlingGameId {
  private static final AtomicInteger GENERATOR = new AtomicInteger();

  private int id;

  public BowlingGameId(int id) {
    this.id = id;
  }

  public BowlingGameId() {
  }

  public static BowlingGameId generate() {
    return new BowlingGameId(GENERATOR.incrementAndGet());
  }

  public static BowlingGameId from(int gameId) {
    return new BowlingGameId(gameId);
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object != null && getClass().equals(object.getClass())) {
      BowlingGameId that = (BowlingGameId) object;
      return id == that.id;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return String.valueOf(id);
  }
}
