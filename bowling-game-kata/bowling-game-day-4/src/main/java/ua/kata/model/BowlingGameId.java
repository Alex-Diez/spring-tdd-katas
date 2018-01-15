package ua.kata.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class BowlingGameId {
  private static final AtomicInteger GENERATOR = new AtomicInteger();
  private final int id;

  private BowlingGameId(int id) {
    this.id = id;
  }

  public static BowlingGameId generate() {
    return new BowlingGameId(GENERATOR.incrementAndGet());
  }

  public static BowlingGameId from(int gameId) {
    return new BowlingGameId(gameId);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object != null && getClass().equals(object.getClass())) {
      BowlingGameId gameId = (BowlingGameId) object;
      return id == gameId.id;
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
