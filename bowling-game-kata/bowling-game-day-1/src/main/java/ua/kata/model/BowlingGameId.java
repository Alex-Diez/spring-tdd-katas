package ua.kata.model;

import java.util.Objects;

public class BowlingGameId {
  private int gameId;

  public BowlingGameId() {
  }

  public BowlingGameId(int gameId) {
    setGameId(gameId);
  }

  public void setGameId(int gameId) {
    this.gameId = gameId;
  }

  public int getGameId() {
    return gameId;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object != null && getClass().equals(object.getClass())) {
      BowlingGameId gameId1 = (BowlingGameId) object;
      return gameId == gameId1.gameId;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(gameId);
  }
}
