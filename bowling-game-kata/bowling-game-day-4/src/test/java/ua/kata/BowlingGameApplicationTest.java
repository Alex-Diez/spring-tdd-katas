package ua.kata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import ua.kata.model.BowlingGameId;
import ua.kata.model.GameResult;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BowlingGameApplicationTest {
  @Autowired
  private TestRestTemplate restTemplate;
  private BowlingGameId gameId;

  @Before
  public void setUp() throws Exception {
    gameId = createNewGame();
  }

  private BowlingGameId createNewGame() {
    return restTemplate.postForObject("/create-game", null, BowlingGameId.class);
  }

  private GameResult requestGameResult(BowlingGameId gameId) {
    return restTemplate.getForObject("/game-result/" + gameId, GameResult.class);
  }

  private void roll(BowlingGameId theOtherGameId, int pin) {
    restTemplate.postForLocation("/roll/" + theOtherGameId + "/" + pin, null);
  }

  @Test
  public void startNewGame() throws Exception {
    assertThat(gameId).isNotNull();
  }

  @Test
  public void gameCanBeFinished() throws Exception {
    for (int i = 0; i < 20; i++) {
      roll(gameId, i % 5);
    }

    GameResult gameResult = requestGameResult(gameId);

    assertThat(gameResult).isEqualTo(GameResult.withScore(40));
  }

  @Test
  public void twoGamesCanBePlayed_atTheSameTime() throws Exception {
    BowlingGameId theOtherGameId = createNewGame();

    for (int i = 0; i < 20; i++) {
      roll(gameId, i % 5);
      roll(theOtherGameId, i % 6);
    }

    assertThat(requestGameResult(gameId)).isEqualTo(GameResult.withScore(40));
    assertThat(requestGameResult(theOtherGameId)).isEqualTo(GameResult.withScore(46));
  }
}
