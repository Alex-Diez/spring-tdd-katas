package ua.kata.repositories;

import org.junit.jupiter.api.Test;

import ua.kata.model.BowlingGame;
import ua.kata.model.BowlingGameId;

import static org.assertj.core.api.Assertions.assertThat;

class GameRepositoryTest {
  @Test
  void createGame() throws Exception {
    GameRepository repository = new GameRepository();
    BowlingGameId gameId = repository.createGame();

    assertThat(gameId.getGameId()).isEqualTo(1);
  }

  @Test
  void retrieveCreatedGameById() throws Exception {
    GameRepository repository = new GameRepository();
    BowlingGameId gameId = repository.createGame();

    BowlingGame game = repository.retrieveGameById(gameId);

    assertThat(game).isNotNull();
  }
}
