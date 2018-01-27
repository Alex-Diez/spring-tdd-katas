package ua.kata.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.kata.model.BowlingGame;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingGameServiceTest {

  private BowlingGameService service;
  private BowlingGame game;

  @BeforeEach
  void setUp() throws Exception {
    service = new BowlingGameService();
    game = service.startNewGame();
  }

  @Test
  void createNewGame_whenRequestedToCreate_aGame() throws Exception {
    assertThat(service.startNewGame()).isNotEqualTo(game);
  }

  @Test
  void returnTheSameGame_whenGameRequestedById() throws Exception {
    assertThat(service.findGameById(game.getId())).isEqualTo(game);
  }
}
