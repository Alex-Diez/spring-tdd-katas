package ua.kata.services;

import org.junit.jupiter.api.Test;

import ua.kata.model.BowlingGame;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingGameServiceTest {
  @Test
  void createNewGame_whenRequested() throws Exception {
    BowlingGameService service = new BowlingGameService();

    BowlingGame game = service.createGame();

    assertThat(game).isNotNull();
    assertThat(service.createGame()).isNotEqualTo(game);
  }
  
  @Test
  void findTheSameGame_bySameId() throws Exception {
    BowlingGameService service = new BowlingGameService();

    BowlingGame game = service.createGame();

    assertThat(service.findGameById(game.getId())).isEqualTo(game);
  }
}
