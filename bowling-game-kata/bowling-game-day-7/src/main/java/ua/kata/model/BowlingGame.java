package ua.kata.model;

import java.util.Objects;
import java.util.stream.IntStream;

public class BowlingGame {
  private static final int FRAMES_NUMBER = 10;

  private final BowlingGameId gameId;
  private final int[] rolls = new int[21];
  private int current = 0;

  private BowlingGame(BowlingGameId gameId) {
    this.gameId = gameId;
  }

  public static BowlingGame newGameWith(BowlingGameId gameId) {
    return new BowlingGame(gameId);
  }

  public void roll(int pin) {
    rolls[current++] = pin;
  }

  public GameResult score() {
    return GameResult.withScore(computeScore());
  }

  private int computeScore() {
    return IntStream.iterate(0, this::nextFrameIndex)
        .limit(FRAMES_NUMBER)
        .map(this::framePoints)
        .sum();
  }

  private int framePoints(int frameIndex) {
    if (isStrike(frameIndex)) {
      return 10 + strikeBonus(frameIndex);
    } else if (isSpare(frameIndex)) {
      return 10 + spareBonus(frameIndex);
    } else {
      return rolls[frameIndex] + rolls[frameIndex + 1];
    }
  }

  private int nextFrameIndex(int frameIndex) {
    return isStrike(frameIndex) ? frameIndex + 1 : frameIndex + 2;
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

  public BowlingGameId getGameId() {
    return gameId;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object != null && getClass().equals(object.getClass())) {
      BowlingGame that = (BowlingGame) object;
      return Objects.equals(gameId, that.gameId);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(gameId);
  }

  @Override
  public String toString() {
    return "BowlingGame{" +
        "gameId=" + gameId +
        '}';
  }
}
