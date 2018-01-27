package ua.kata.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.kata.model.BowlingGame;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingGameServiceTest {

  private BowlingGameService service;

  @BeforeEach
  void setUp() throws Exception {
    service = new BowlingGameService();
  }

  @Test
  void start_aNewGame() throws Exception {
    assertThat(service.startNewGame()).isNotNull();
  }

  @Test
  void findGameById() throws Exception {
    BowlingGame game = service.startNewGame();

    assertThat(service.findGameById(game.getId())).isEqualTo(game);
  }
}
