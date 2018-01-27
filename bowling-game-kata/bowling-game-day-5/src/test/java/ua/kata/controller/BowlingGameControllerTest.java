package ua.kata.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ua.kata.model.BowlingGame;
import ua.kata.model.BowlingGameId;
import ua.kata.services.BowlingGameService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BowlingGameController.class)
class BowlingGameControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BowlingGameService service;
  private BowlingGameId gameId;

  @BeforeEach
  void setUp() throws Exception {
    gameId = BowlingGameId.generate();
    BowlingGame game = BowlingGame.newGameWith(gameId);
    given(service.createGame()).willReturn(game);
    given(service.findGameById(gameId)).willReturn(game);
  }

  private ResultActions requestNewGame() throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders.post("/create-game"));
  }

  @Test
  void createNewGame() throws Exception {
    requestNewGame()
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(gameId.toString()));
  }

  @Test
  void roll_aBall() throws Exception {
    requestNewGame();

    mockMvc.perform(MockMvcRequestBuilders.post("/roll/" + gameId + "/" + 5))
        .andExpect(status().isOk());
  }

  @Test
  void requestGameResult() throws Exception {
    requestNewGame();

    mockMvc.perform(MockMvcRequestBuilders.post("/roll/" + gameId + "/" + 5));

    mockMvc.perform(MockMvcRequestBuilders.get("/game-result/" + gameId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("score").value(5));
  }
}
