package ua.kata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
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
  public Mono<BowlingGameId> createNewGame() {
    return Mono.just(service.createGame().getGameId());
  }

  @PostMapping("/roll/{gameId}/{pin}")
  public Mono<Void> roll(@PathVariable int gameId, @PathVariable int pin) {
    service.findGameById(BowlingGameId.from(gameId)).roll(pin);
    return Mono.empty();
  }

  @GetMapping("/game-result/{gameId}")
  public Mono<GameResult> getGameResult(@PathVariable int gameId) {
    return Mono.just(service.findGameById(BowlingGameId.from(gameId)).score());
  }
}
