package ua.kata.model;

import java.util.Objects;
import java.util.stream.IntStream;

public class BowlingGame {
  private static final int FRAMES_NUMBER = 10;
  private final BowlingGameId gameId;
  private final int[] rolls = new int[21];
  private int current;

  private BowlingGame(BowlingGameId gameId) {
    this.gameId = gameId;
  }

  public static BowlingGame newGameWith(BowlingGameId gameId) {
    return new BowlingGame(gameId);
  }

  public BowlingGameId getId() {
    return gameId;
  }

  public void roll(int pin) {
    rolls[current++] = pin;
  }

  public GameResult score() {
    return GameResult.withScore(computeGameScore());
  }

  private int computeGameScore() {
    return IntStream.iterate(0, this::nextFrameIndex).limit(FRAMES_NUMBER).map(this::framePoints).sum();
  }

  private int framePoints(int frameIndex) {
    if (isStrike(frameIndex)) return 10 + strikeBonus(frameIndex);
    else if (isSpare(frameIndex)) return 10 + spareBonus(frameIndex);
    else return rolls[frameIndex] + rolls[frameIndex + 1];
  }

  private int nextFrameIndex(int frameIndex) {
    if (isStrike(frameIndex)) return frameIndex + 1;
    else return frameIndex + 2;
  }

  private int spareBonus(int frameIndex) {
    return rolls[frameIndex + 2];
  }

  private boolean isSpare(int frameIndex) {
    return rolls[frameIndex] + rolls[frameIndex + 1] == 10;
  }

  private int strikeBonus(int frameIndex) {
    return rolls[frameIndex + 1] + rolls[frameIndex + 2];
  }

  private boolean isStrike(int frameIndex) {
    return rolls[frameIndex] == 10;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object != null && getClass().equals(object.getClass())) {
      BowlingGame game = (BowlingGame) object;
      return Objects.equals(gameId, game.gameId);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(gameId);
  }
}
