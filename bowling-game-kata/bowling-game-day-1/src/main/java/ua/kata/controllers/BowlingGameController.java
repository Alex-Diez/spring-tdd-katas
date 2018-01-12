package ua.kata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.kata.model.BowlingGame;
import ua.kata.model.BowlingGameId;
import ua.kata.model.GameResult;
import ua.kata.repositories.GameRepository;

@RestController
public class BowlingGameController {

  private final GameRepository repository;

  @Autowired
  public BowlingGameController(GameRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/create-game")
  public BowlingGameId createGame() {
    return repository.createGame();
  }

  @PostMapping("/roll/{gameId}/{pins}")
  public void roll(@PathVariable int gameId, @PathVariable int pins) {
    repository.retrieveGameById(new BowlingGameId(gameId)).roll(pins);
  }

  @GetMapping("/game-score/{gameId}")
  public GameResult requestGameScore(@PathVariable int gameId) {
    return repository.retrieveGameById(new BowlingGameId(gameId)).score();
  }
}
