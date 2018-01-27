package ua.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.kata.model.BowlingGameId;
import ua.kata.model.GameResult;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BowlingGameIntegrationTest {
  @Autowired
  private TestRestTemplate restTemplate;

  private BowlingGameId gameId;

  @BeforeEach
  void setUp() throws Exception {
    ResponseEntity<BowlingGameId> game = restTemplate.postForEntity(
      "/create-game",
      null,
      BowlingGameId.class
    );

    gameId = game.getBody();
  }

  private void rollMany(int times, int pin) {
    for (int i = 0; i < times; i++) {
      roll(pin);
    }
  }

  private void rollSpare() {
    roll(4);
    roll(6);
  }

  private void roll(int pin) {
    restTemplate.postForEntity("/roll/" + gameId + "/" + pin, null, null);
  }

  private ResponseEntity<GameResult> requestGameResult() {
    return restTemplate.getForEntity("/game-score/" + gameId, GameResult.class);
  }

  @Test
  void gutterGame() throws Exception {
    rollMany(20, 0);

    assertThat(requestGameResult().getBody()).isEqualTo(GameResult.withScore(0));
  }

  @Test
  void allOnes() throws Exception {
    rollMany(20, 1);

    assertThat(requestGameResult().getBody()).isEqualTo(GameResult.withScore(20));
  }

  @Test
  void oneSpare() throws Exception {
    rollSpare();
    roll(3);
    rollMany(17, 0);

    assertThat(requestGameResult().getBody()).isEqualTo(GameResult.withScore(16));
  }

  @Test
  void oneStrike() throws Exception {
    roll(10);
    roll(4);
    roll(3);
    rollMany(16, 0);

    assertThat(requestGameResult().getBody()).isEqualTo(GameResult.withScore(24));
  }

  @Test
  void perfectGame() throws Exception {
    rollMany(12, 10);

    assertThat(requestGameResult().getBody()).isEqualTo(GameResult.withScore(300));
  }
}
