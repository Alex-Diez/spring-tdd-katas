package ua.kata.services;

import org.junit.jupiter.api.Test;

import ua.kata.model.BowlingGame;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingGameServiceTest {
  @Test
  void returnNewGame_whenCreate() throws Exception {
    BowlingGameService service = new BowlingGameService();

    BowlingGame newGame = service.createGame();
    assertThat(newGame).isNotNull();
    assertThat(service.createGame()).isNotEqualTo(newGame);
  }

  @Test
  void returnTheSameGame_whenLooksUpById() throws Exception {
    BowlingGameService service = new BowlingGameService();

    BowlingGame game = service.createGame();

    assertThat(service.findGameById(game.getId())).isEqualTo(game);
  }
}
