package ua.kata.model;

import java.util.Objects;

public class GameResult {
  private final int score;

  private GameResult(int score) {
    this.score = score;
  }

  public static GameResult withScore(int score) {
    return new GameResult(score);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o != null && getClass().equals(o.getClass())) {
      GameResult that = (GameResult) o;
      return score == that.score;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(score);
  }

  @Override
  public String toString() {
    return String.valueOf(score);
  }
}
