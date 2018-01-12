package ua.kata.model;

public class BowlingGame {
  private int[] rolls = new int[21];
  private int current;

  public void roll(int pin) {
    rolls[current++] = pin;
  }

  public GameResult score() {
    int score = 0;
    int frameIndex = 0;

    for (int i = 0; i < 10; i++) {
      if (isStrike(frameIndex)) {
        score += 10 + strikeBonus(frameIndex);
        frameIndex += 1;
      } else if (isSpare(frameIndex)) {
        score += 10 + spareBonus(frameIndex);
        frameIndex += 2;
      } else {
        score += rolls[frameIndex] + rolls[frameIndex + 1];
        frameIndex += 2;
      }
    }

    return new GameResult(score);
  }

  private boolean isStrike(int frameIndex) {
    return rolls[frameIndex] == 10;
  }

  private int strikeBonus(int frameIndex) {
    return rolls[frameIndex + 1] + rolls[frameIndex + 2];
  }

  private boolean isSpare(int frameIndex) {
    return rolls[frameIndex] + rolls[frameIndex + 1] == 10;
  }

  private int spareBonus(int frameIndex) {
    return rolls[frameIndex + 2];
  }
}
