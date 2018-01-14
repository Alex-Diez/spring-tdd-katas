package ua.kata.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import ua.kata.model.BowlingGame;
import ua.kata.model.BowlingGameId;

@Service
public class BowlingGameService {
  private final Map<BowlingGameId, BowlingGame> games = new HashMap<>();

  public BowlingGame createGame() {
    BowlingGameId id = BowlingGameId.next();
    BowlingGame game = BowlingGame.newGameWith(id);
    games.put(id, game);
    return game;
  }

  public BowlingGame findGameById(BowlingGameId id) {
    return games.get(id);
  }
}
