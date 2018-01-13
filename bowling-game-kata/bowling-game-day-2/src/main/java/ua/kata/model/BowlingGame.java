package ua.kata.model;

import java.util.Objects;
import java.util.stream.IntStream;

public class BowlingGame {
  private static final int FRAME_NUMBER = 10;

  private final BowlingGameId id;
  private final int rolls[] = new int[21];
  private int current;

  public BowlingGameId getId() {
    return id;
  }

  public static BowlingGame newGameWith(BowlingGameId id) {
    return new BowlingGame(id);
  }

  private BowlingGame(BowlingGameId id) {
    this.id = id;
  }

  public void roll(int pin) {
    rolls[current++] = pin;
  }

  public GameResult score() {
    return GameResult.withScore(computeScore());
  }

  private int computeScore() {
    return IntStream.iterate(0, this::nextFrameIndex).limit(FRAME_NUMBER).map(this::computeFramePoints).sum();
  }

  private int computeFramePoints(int frameIndex) {
    if (isStrike(frameIndex)) {
      return 10 + strikeBonus(frameIndex);
    } else if (isSpare(frameIndex)) {
      return 10 + spareBonus(frameIndex);
    } else {
      return rolls[frameIndex] + rolls[frameIndex + 1];
    }
  }

  private int nextFrameIndex(int frameIndex) {
    if (isStrike(frameIndex)) {
      return frameIndex + 1;
    } else {
      return frameIndex + 2;
    }
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

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object != null && getClass().equals(object.getClass())) {
      BowlingGame game = (BowlingGame) object;
      return id == game.id;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
