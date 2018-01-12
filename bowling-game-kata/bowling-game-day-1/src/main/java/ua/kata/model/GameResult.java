package ua.kata.model;

import java.util.Objects;

public class GameResult {
  private int score;

  public GameResult() {
  }

  public GameResult(int score) {
    setScore(score);
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
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
}
