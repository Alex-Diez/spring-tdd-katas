package ua.kata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import ua.kata.model.BowlingGameId;
import ua.kata.model.GameResult;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BowlingGameIntegrationTest {
  @Autowired
  private TestRestTemplate restTemplate;

  private int gameId;

  @Before
  public void setUp() throws Exception {
    ResponseEntity<BowlingGameId> createGameResponse = restTemplate.postForEntity(
      "/create-game",
      null,
      BowlingGameId.class
    );
    gameId = createGameResponse.getBody().getGameId();
  }

  private void submitManySameRolls(int times, int pin) {
    for (int i = 0; i < times; i++) {
      submitRolls(pin);
    }
  }

  private void submitRolls(int... pins) {
    for (int pin : pins) {
      restTemplate.postForEntity("/roll/" + gameId + "/" + pin, null, Object.class);
    }
  }

  private void submitSpare() {
    submitRolls(6);
    submitRolls(4);
  }

  private void submitStrike() {
    submitRolls(10);
  }

  private ResponseEntity<GameResult> requestGameResult() {
    return restTemplate.getForEntity("/game-score/" + gameId, GameResult.class);
  }

  @Test
  public void gutterGame() throws Exception {
    submitManySameRolls(20, 0);

    ResponseEntity<GameResult> gameResult = requestGameResult();
    assertThat(gameResult.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(gameResult.getBody().getScore()).isEqualTo(0);
  }

  @Test
  public void allOnes() throws Exception {
    submitManySameRolls(20, 1);

    ResponseEntity<GameResult> gameResult = requestGameResult();
    assertThat(gameResult.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(gameResult.getBody().getScore()).isEqualTo(20);
  }

  @Test
  public void oneSpare() throws Exception {
    submitSpare();
    submitRolls(3);
    submitManySameRolls(17, 0);

    ResponseEntity<GameResult> gameResult = requestGameResult();
    assertThat(gameResult.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(gameResult.getBody().getScore()).isEqualTo(16);
  }

  @Test
  public void oneStrike() throws Exception {
    submitStrike();
    submitRolls(3, 4);
    submitManySameRolls(16, 0);

    ResponseEntity<GameResult> gameResult = requestGameResult();
    assertThat(gameResult.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(gameResult.getBody().getScore()).isEqualTo(24);
  }

  @Test
  public void perfectGame() throws Exception {
    submitManySameRolls(13, 10);

    ResponseEntity<GameResult> gameResult = requestGameResult();
    assertThat(gameResult.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(gameResult.getBody().getScore()).isEqualTo(300);
  }
}
