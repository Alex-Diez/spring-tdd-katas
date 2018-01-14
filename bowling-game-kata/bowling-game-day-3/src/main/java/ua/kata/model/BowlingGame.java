package ua.kata.model;

public class BowlingGame {
  private final BowlingGameId id;
  private final int[] rolls = new int[21];
  private int current;

  private BowlingGame(BowlingGameId id) {
    this.id = id;
  }

  public static BowlingGame newGameWith(BowlingGameId id) {
    return new BowlingGame(id);
  }

  public BowlingGameId getId() {
    return id;
  }

  public void roll(int pin) {
    rolls[current++] = pin;
  }

  public GameResult score() {
    int score = 0;
    int frameIndex = 0;

    for (int i = 0; i < 10; i++) {
      if (rolls[frameIndex] == 10) {
        score += 10 + rolls[frameIndex + 1] + rolls[frameIndex + 2];
        frameIndex += 1;
      } else if (rolls[frameIndex] + rolls[frameIndex + 1] == 10) {
        score += 10 + rolls[frameIndex + 2];
        frameIndex += 2;
      } else {
        score += rolls[frameIndex] + rolls[frameIndex + 1];
        frameIndex += 2;
      }
    }

    return GameResult.withScore(score);
  }
}
