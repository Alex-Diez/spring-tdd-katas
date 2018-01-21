package ua.kata.services;

import org.junit.Test;

import ua.kata.model.BowlingGame;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingGameServiceTest {

  @Test
  public void createNewGame_eachTimeWhenRequested() throws Exception {
    BowlingGameService service = new BowlingGameService();

    BowlingGame game = service.createGame();

    assertThat(service.createGame()).isNotEqualTo(game);
  }

  @Test
  public void returnTheSameGame_whenRequestedFindById() throws Exception {
    BowlingGameService service = new BowlingGameService();

    BowlingGame game = service.createGame();

    assertThat(service.findGameById(game.getId())).isEqualTo(game);
  }
}