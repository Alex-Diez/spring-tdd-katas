package ua.kata.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingGameTest {

  private BowlingGame game;

  @BeforeEach
  void setUp() throws Exception {
    game = new BowlingGame();
  }

  private void rollMany(int times, int pin) {
    for(int i = 0; i < times; i++) {
      game.roll(pin);
    }
  }

  private void rollSpare() {
    game.roll(4);
    game.roll(6);
  }

  private void rollStrike() {
    game.roll(10);
  }

  @Test
  void gutterGame() throws Exception {
    rollMany(20, 0);

    assertThat(game.score()).isEqualTo(new GameResult(0));
  }

  @Test
  void allOnes() throws Exception {
    rollMany(20, 1);

    assertThat(game.score()).isEqualTo(new GameResult(20));
  }

  @Test
  void oneSpare() throws Exception {
    rollSpare();
    game.roll(3);
    rollMany(17, 0);

    assertThat(game.score()).isEqualTo(new GameResult(16));
  }

  @Test
  void oneStrike() throws Exception {
    rollStrike();
    game.roll(4);
    game.roll(3);
    rollMany(16, 0);

    assertThat(game.score()).isEqualTo(new GameResult(24));
  }
}
