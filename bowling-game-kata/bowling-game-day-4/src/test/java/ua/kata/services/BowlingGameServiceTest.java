package ua.kata.services;

import org.junit.Before;
import org.junit.Test;

import ua.kata.model.BowlingGame;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingGameServiceTest {

  private BowlingGameService service;
  private BowlingGame game;

  @Before
  public void setUp() throws Exception {
    service = new BowlingGameService();
    game = service.startNewGame();
  }

  @Test
  public void createNewGame_whenRequestedToCreate_aGame() throws Exception {
    assertThat(service.startNewGame()).isNotEqualTo(game);
  }

  @Test
  public void returnTheSameGame_whenGameRequestedById() throws Exception {
    assertThat(service.findGameById(game.getId())).isEqualTo(game);
  }
}
