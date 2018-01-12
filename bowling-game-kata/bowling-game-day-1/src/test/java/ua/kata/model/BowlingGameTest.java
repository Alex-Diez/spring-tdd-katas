package ua.kata.model;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingGameTest {

  private BowlingGame game;

  @Before
  public void setUp() throws Exception {
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
  public void gutterGame() throws Exception {
    rollMany(20, 0);

    assertThat(game.score()).isEqualTo(new GameResult(0));
  }

  @Test
  public void allOnes() throws Exception {
    rollMany(20, 1);

    assertThat(game.score()).isEqualTo(new GameResult(20));
  }

  @Test
  public void oneSpare() throws Exception {
    rollSpare();
    game.roll(3);
    rollMany(17, 0);

    assertThat(game.score()).isEqualTo(new GameResult(16));
  }

  @Test
  public void oneStrike() throws Exception {
    rollStrike();
    game.roll(4);
    game.roll(3);
    rollMany(16, 0);

    assertThat(game.score()).isEqualTo(new GameResult(24));
  }
}
