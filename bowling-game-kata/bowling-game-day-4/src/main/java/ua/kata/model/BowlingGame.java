package ua.kata.model;

import java.util.stream.IntStream;

public class BowlingGame {
  private static final int FRAME_NUMBER = 10;
  private final BowlingGameId id;
  private int[] rolls = new int[21];
  private int current;

  private BowlingGame(BowlingGameId id) {
    this.id = id;
  }

  public static BowlingGame newGameWith(BowlingGameId id) {
    return new BowlingGame(id);
  }

  public void roll(int pin) {
    rolls[current++] = pin;
  }

  public GameResult score() {
    return GameResult.withScore(computeScore());
  }

  private int computeScore() {
    return IntStream.iterate(0, this::nextFrameIndex).limit(FRAME_NUMBER).map(this::framePoints).sum();
  }

  private int framePoints(int frameIndex) {
    if (isStrike(frameIndex)) return 10 + strikeBonus(frameIndex);
    else if(isSpare(frameIndex)) return 10 + spareBonus(frameIndex);
    else return rolls[frameIndex] + rolls[frameIndex + 1];
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

  public BowlingGameId getId() {
    return id;
  }

  @Override
  public String toString() {
    return "BowlingGame{" +
        "id=" + id +
        '}';
  }
}
