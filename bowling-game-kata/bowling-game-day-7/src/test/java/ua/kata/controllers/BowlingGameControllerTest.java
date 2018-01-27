package ua.kata.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import ua.kata.model.BowlingGame;
import ua.kata.model.BowlingGameId;
import ua.kata.services.BowlingGameService;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(BowlingGameController.class)
class BowlingGameControllerTest {
  @Autowired
  private WebTestClient client;

  @MockBean
  private BowlingGameService service;
  private BowlingGameId id;

  @BeforeEach
  void setUp() throws Exception {
    id = BowlingGameId.generate();
    BowlingGame game = BowlingGame.newGameWith(id);

    given(service.createGame()).willReturn(game);
    given(service.findGameById(id)).willReturn(game);
  }

  private WebTestClient.ResponseSpec makeRequestToCreateGame() {
    return client.post().uri("/create-game")
        .exchange();
  }

  @Test
  void createNewGame() throws Exception {
    makeRequestToCreateGame()
        .expectStatus().isOk()
        .expectBody().jsonPath("id").isNotEmpty();
  }

  @Test
  void roll_aBall() throws Exception {
    makeRequestToCreateGame();

    client.post().uri("/roll/" + id + "/" + 5)
        .exchange()
        .expectStatus().isOk()
        .expectBody().isEmpty();
  }

  @Test
  void requestGameCurrentScore() throws Exception {
    makeRequestToCreateGame();

    client.post().uri("/roll/" + id + "/" + 5)
        .exchange();

    client.get().uri("/game-result/" + id)
        .exchange()
        .expectStatus().isOk()
        .expectBody().jsonPath("score").isEqualTo(5);
  }
}
