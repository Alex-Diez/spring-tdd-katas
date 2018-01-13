package ua.kata.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ua.kata.model.BowlingGame;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingGameServiceTest {

  private BowlingGameService service;

  @Before
  public void setUp() throws Exception {
    service = new BowlingGameService();
  }

  @Test
  public void start_aNewGame() throws Exception {
    assertThat(service.startNewGame()).isNotNull();
  }

  @Test
  public void findGameById() throws Exception {
    BowlingGame game = service.startNewGame();

    assertThat(service.findGameById(game.getId())).isEqualTo(game);
  }
}
