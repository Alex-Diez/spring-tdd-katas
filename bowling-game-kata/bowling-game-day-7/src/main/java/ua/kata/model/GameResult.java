package ua.kata.model;

import java.util.Objects;

public class GameResult {
  private int score;

  public GameResult(int score) {
    this.score = score;
  }

  public GameResult() {
  }

  public static GameResult withScore(int score) {
    return new GameResult(score);
  }

  public int getScore() {
    return score;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object != null && getClass().equals(object.getClass())) {
      GameResult that = (GameResult) object;
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
