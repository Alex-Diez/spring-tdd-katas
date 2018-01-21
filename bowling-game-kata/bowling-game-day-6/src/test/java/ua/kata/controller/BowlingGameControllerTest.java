package ua.kata.controller;

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
  private static final int ROLLED_PIN = 5;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BowlingGameService service;
  private BowlingGameId id;

  @Before
  public void setUp() throws Exception {
    id = BowlingGameId.generate();
    BowlingGame game = BowlingGame.newGameWith(id);
    given(service.createGame()).willReturn(game);
    given(service.findGameById(id)).willReturn(game);
  }

  private ResultActions requestNewGame() throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders.post("/create-game"));
  }

  private ResultActions roll(int i) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders.post("/roll/" + id + "/" + i));
  }

  @Test
  public void createNewGame() throws Exception {
    requestNewGame()
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").isNotEmpty());
  }

  @Test
  public void roll_aBall() throws Exception {
    requestNewGame();

    roll(5)
        .andExpect(status().isOk());
  }

  @Test
  public void requestGameResult() throws Exception {
    requestNewGame();

    roll(ROLLED_PIN);

    mockMvc.perform(MockMvcRequestBuilders.get("/game-result/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("score").value(ROLLED_PIN));
  }
}
