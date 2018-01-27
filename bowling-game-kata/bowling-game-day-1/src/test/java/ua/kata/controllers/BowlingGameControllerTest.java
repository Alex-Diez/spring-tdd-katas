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
import ua.kata.repositories.GameRepository;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BowlingGameController.class)
class BowlingGameControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private GameRepository gameRepository;

  @BeforeEach
  void setUp() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post("/create-game"));

    given(gameRepository.retrieveGameById(any())).willReturn(new BowlingGame());
  }

  @Test
  void rollABall() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post("/roll/1/0"))
      .andExpect(status().isOk());
  }

  @Test
  void requestAGameScore() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/game-score/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("score").value("0"));
  }
}
