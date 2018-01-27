package ua.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.kata.model.BowlingGameId;
import ua.kata.model.GameResult;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BowlingGameApplicationTest {

  @Autowired
  private TestRestTemplate restTemplate;
  private BowlingGameId gameId;

  @BeforeEach
  void setUp() throws Exception {
    gameId = requestNewGameCreation();
  }

  private BowlingGameId requestNewGameCreation() {
    return restTemplate.postForObject("/create-game", null, BowlingGameId.class);
  }

  private void rollBall(BowlingGameId gameId, int pin) {
    restTemplate.postForLocation("/roll/" + gameId + "/" + pin, null);
  }

  private GameResult requestGameResult(BowlingGameId theOtherGameId) {
    return restTemplate.getForObject("/game-result/" + theOtherGameId, GameResult.class);
  }

  @Test
  void createNewGame() throws Exception {
    assertThat(gameId).isNotNull();
  }

  @Test
  void gameCanBeFinished() throws Exception {
    for (int i = 0; i < 20; i++) {
      rollBall(gameId, i % 5);
    }

    assertThat(requestGameResult(gameId)).isEqualTo(GameResult.withScore(40));
  }

  @Test
  void twoGameCanBePlayed_inParallel() throws Exception {
    BowlingGameId theOtherGameId = requestNewGameCreation();

    for (int i = 0; i < 20; i++) {
      rollBall(gameId, i % 5);
      rollBall(theOtherGameId, i % 6);
    }

    assertThat(requestGameResult(gameId)).isEqualTo(GameResult.withScore(40));
    assertThat(requestGameResult(theOtherGameId)).isEqualTo(GameResult.withScore(46));
  }
}
