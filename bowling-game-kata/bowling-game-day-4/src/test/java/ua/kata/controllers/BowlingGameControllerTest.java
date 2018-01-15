package ua.kata.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ua.kata.model.BowlingGame;
import ua.kata.model.BowlingGameId;
import ua.kata.services.BowlingGameService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BowlingGameController.class)
public class BowlingGameControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private BowlingGameService service;
  private BowlingGameId gameId;

  @Before
  public void setUp() throws Exception {
    gameId = BowlingGameId.generate();
    BowlingGame game = BowlingGame.newGameWith(gameId);
    given(service.startNewGame()).willReturn(game);
    given(service.findGameById(gameId)).willReturn(game);
  }

  private ResultActions requestNewGameCreation() throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders.post("/create-game"));
  }

  @Test
  public void create_aGame() throws Exception {
    requestNewGameCreation()
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").isNotEmpty());
  }

  @Test
  public void roll_aBall() throws Exception {
    requestNewGameCreation();
    int pin = 0;
    mockMvc.perform(MockMvcRequestBuilders.post("/roll/" + gameId + "/" + pin))
        .andExpect(status().isOk());
  }

  @Test
  public void retrieveGameResult() throws Exception {
    requestNewGameCreation();

    int pin = 5;
    mockMvc.perform(MockMvcRequestBuilders.post("/roll/" + gameId + "/" + pin));

    mockMvc.perform(MockMvcRequestBuilders.get("/game-result/" + gameId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("score").value(pin));
  }
}
