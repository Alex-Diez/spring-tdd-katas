package ua.kata.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.kata.model.BowlingGame;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingGameServiceTest {

  private BowlingGameService service;

  @BeforeEach
  void setUp() {
    service = new BowlingGameService();
  }

  @Test
  void returnsNewGame_afterRequestedToCreateNewGame() throws Exception {
    BowlingGame game = service.createGame();
    assertThat(service.createGame()).isNotEqualTo(game);
  }

  @Test
  void returnsSameGame_whenRequestedToFindGameById() throws Exception {
    BowlingGame game = service.createGame();
    assertThat(service.findGameById(game.getGameId())).isEqualTo(game);
  }
}