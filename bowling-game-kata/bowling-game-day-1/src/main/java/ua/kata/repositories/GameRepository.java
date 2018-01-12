package ua.kata.repositories;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

import ua.kata.model.BowlingGame;
import ua.kata.model.BowlingGameId;

@Repository
public class GameRepository {
  private final Map<BowlingGameId, BowlingGame> games = new HashMap<>();

  public BowlingGame retrieveGameById(BowlingGameId id) {
    return games.get(id);
  }

  public BowlingGameId createGame() {
    BowlingGameId id = new BowlingGameId(1);
    BowlingGame game = new BowlingGame();
    games.put(id, game);
    return id;
  }
}
