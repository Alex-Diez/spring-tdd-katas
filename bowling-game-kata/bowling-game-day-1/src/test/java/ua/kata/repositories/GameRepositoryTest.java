package ua.kata.repositories;

import org.junit.Test;

import ua.kata.model.BowlingGame;
import ua.kata.model.BowlingGameId;

import static org.assertj.core.api.Assertions.assertThat;

public class GameRepositoryTest {
  @Test
  public void createGame() throws Exception {
    GameRepository repository = new GameRepository();
    BowlingGameId gameId = repository.createGame();

    assertThat(gameId.getGameId()).isEqualTo(1);
  }

  @Test
  public void retrieveCreatedGameById() throws Exception {
    GameRepository repository = new GameRepository();
    BowlingGameId gameId = repository.createGame();

    BowlingGame game = repository.retrieveGameById(gameId);

    assertThat(game).isNotNull();
  }
}
