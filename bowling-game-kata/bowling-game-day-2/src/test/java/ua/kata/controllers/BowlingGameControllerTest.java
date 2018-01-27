package ua.kata.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
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
  private static final BowlingGameId GAME_ID = BowlingGameId.from(1);
  private static final int DEFAULT_PIN = 0;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BowlingGameService service;

  @BeforeEach
  void setUp() throws Exception {
    BowlingGame game = BowlingGame.newGameWith(GAME_ID);
    given(service.startNewGame()).willReturn(game);
    given(service.findGameById(GAME_ID)).willReturn(game);
    mockMvc.perform(MockMvcRequestBuilders.post("/create-game"));
  }

  @Test
  void roll_aBall() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/roll/" + GAME_ID + "/" + DEFAULT_PIN))
      .andExpect(status().isOk());
  }

  @Test
  void requestGameResult() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/game-score/" + GAME_ID))
      .andExpect(status().isOk())
      .andExpect(jsonPath("score").value("0"));
  }
}
