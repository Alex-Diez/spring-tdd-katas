package ua.kata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import ua.kata.model.BowlingGameId;
import ua.kata.model.GameResult;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BowlingGameApplicationTest {
  @Autowired
  private TestRestTemplate restTemplate;

  private BowlingGameId sendRequestToCreateNewGame() {
    return restTemplate.postForObject("/create-game", null, BowlingGameId.class);
  }

  private GameResult requestGameResultFor(BowlingGameId firstGame) throws URISyntaxException {
    return restTemplate.getForObject(new URI("/game-result/" + firstGame), GameResult.class);
  }

  private void roll(BowlingGameId gameId, int pin) {
    restTemplate.postForLocation("/roll/" + gameId + "/" + pin, null);
  }

  @Test
  public void startNewGame() throws Exception {
    BowlingGameId gameId = sendRequestToCreateNewGame();

    assertThat(gameId).isNotNull();
  }

  @Test
  public void playerCanFinishTheGame() throws Exception {
    BowlingGameId gameId = sendRequestToCreateNewGame();

    for (int i = 0; i < 20; i++) {
      roll(gameId, i % 5);
    }

    assertThat(requestGameResultFor(gameId)).isEqualTo(GameResult.withScore(40));
  }

  @Test
  public void twoGames_canBePlayedAtTheSameTime() throws Exception {
    BowlingGameId firstGame = sendRequestToCreateNewGame();
    BowlingGameId secondGame = sendRequestToCreateNewGame();

    for (int i = 0; i < 20; i++) {
      roll(firstGame, i % 5);
      roll(secondGame, i % 6);
    }

    assertThat(requestGameResultFor(firstGame)).isEqualTo(GameResult.withScore(40));
    assertThat(requestGameResultFor(secondGame)).isEqualTo(GameResult.withScore(46));
  }
}
