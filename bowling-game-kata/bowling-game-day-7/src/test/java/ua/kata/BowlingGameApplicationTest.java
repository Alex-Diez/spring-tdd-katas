package ua.kata;

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

  private BowlingGameId makeRequestToCreateGame() {
    return restTemplate.postForObject("/create-game", null, BowlingGameId.class);
  }

  private void submitRoll(BowlingGameId gameId, int pin) {
    restTemplate.postForLocation("/roll/" + gameId + "/" + pin, null);
  }

  private GameResult requestGameResult(BowlingGameId gameId) {
    return restTemplate.getForObject("/game-result/" + gameId, GameResult.class);
  }

  @Test
  void requestToStartNewGame() throws Exception {
    assertThat(makeRequestToCreateGame()).isNotNull();
  }

  @Test
  void gameCanBeFinished() throws Exception {
    BowlingGameId gameId = makeRequestToCreateGame();

    for (int i = 0; i < 20; i++) {
      int pin = i % 5;
      submitRoll(gameId, pin);
    }

    assertThat(requestGameResult(gameId)).isEqualTo(GameResult.withScore(40));
  }

  @Test
  void twoGamesCanBePlayedInParallel() throws Exception {
    BowlingGameId gameId = makeRequestToCreateGame();
    BowlingGameId theOtherGameId = makeRequestToCreateGame();

    for (int i = 0; i < 20; i++) {
      submitRoll(gameId, i % 5);
      submitRoll(theOtherGameId, i % 6);
    }

    assertThat(requestGameResult(gameId)).isEqualTo(GameResult.withScore(40));
    assertThat(requestGameResult(theOtherGameId)).isEqualTo(GameResult.withScore(46));
  }
}
