package ua.kata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.kata.model.BowlingGameId;
import ua.kata.model.GameResult;
import ua.kata.services.BowlingGameService;

@RestController
public class BowlingGameController {

  private final BowlingGameService service;

  @Autowired
  public BowlingGameController(BowlingGameService service) {
    this.service = service;
  }

  @PostMapping("/create-game")
  public BowlingGameId createGame() {
    return service.startNewGame().getId();
  }

  @PostMapping("/roll/{gameId}/{pin}")
  public void roll(@PathVariable int gameId, @PathVariable int pin) {
    service.findGameById(BowlingGameId.from(gameId)).roll(pin);
  }

  @GetMapping("/game-result/{gameId}")
  public GameResult getGameResult(@PathVariable int gameId) {
    return service.findGameById(BowlingGameId.from(gameId)).score();
  }
}
