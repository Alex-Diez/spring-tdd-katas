package ua.kata.model;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingGameTest {

  private BowlingGame game;

  @Before
  public void setUp() throws Exception {
    game = BowlingGame.newGameWith(BowlingGameId.next());
  }

  private void rollMany(int times, int pin) {
    for (int i = 0; i < times; i++) {
      game.roll(pin);
    }
  }

  private void rollSpare() {
    game.roll(4);
    game.roll(6);
  }

  @Test
  public void gutterGame() throws Exception {
    rollMany(20, 0);

    assertThat(game.score()).isEqualTo(GameResult.withScore(0));
  }

  @Test
  public void allOnes() throws Exception {
    rollMany(20, 1);

    assertThat(game.score()).isEqualTo(GameResult.withScore(20));
  }

  @Test
  public void oneSpare() throws Exception {
    rollSpare();
    game.roll(3);
    rollMany(17, 0);

    assertThat(game.score()).isEqualTo(GameResult.withScore(16));
  }

  @Test
  public void oneStrike() throws Exception {
    game.roll(10);
    game.roll(4);
    game.roll(3);
    rollMany(16, 0);

    assertThat(game.score()).isEqualTo(GameResult.withScore(24));
  }

  @Test
  public void perfectGame() throws Exception {
    rollMany(12, 10);

    assertThat(game.score()).isEqualTo(GameResult.withScore(300));
  }
}
