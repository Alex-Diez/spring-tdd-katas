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

import ua.kata.controllers.BowlingGameController;
import ua.kata.model.BowlingGame;
import ua.kata.model.BowlingGameId;
import ua.kata.repositories.GameRepository;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BowlingGameController.class)
public class BowlingGameControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private GameRepository gameRepository;

  @Before
  public void setUp() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post("/create-game"));

    given(gameRepository.retrieveGameById(any())).willReturn(new BowlingGame());
  }

  @Test
  public void rollABall() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post("/roll/1/0"))
      .andExpect(status().isOk());
  }

  @Test
  public void requestAGameScore() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/game-score/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("score").value("0"));
  }
}
