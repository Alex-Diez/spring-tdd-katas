package ua.kata.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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

  private BowlingGameId id;

  @Before
  public void createGame() throws Exception {
    id = BowlingGameId.next();
    BowlingGame game = BowlingGame.newGameWith(id);
    given(service.createGame()).willReturn(game);
    given(service.findGameById(id)).willReturn(game);

    mockMvc.perform(MockMvcRequestBuilders.post("/create-game"));
  }

  @Test
  public void roll_aBall() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/roll/" + id + "/" + 0))
      .andExpect(status().isOk());
  }

  @Test
  public void requestGameById() throws Exception {
    int roll = 5;

    mockMvc.perform(MockMvcRequestBuilders.post("/roll/" + id + "/" + roll));

    mockMvc.perform(MockMvcRequestBuilders.get("/game-result/" + id))
      .andExpect(status().isOk())
      .andExpect(jsonPath("score").value(roll));
  }
}
