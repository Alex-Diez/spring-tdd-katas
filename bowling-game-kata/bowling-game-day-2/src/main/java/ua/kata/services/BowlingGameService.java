package ua.kata.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import ua.kata.model.BowlingGame;
import ua.kata.model.BowlingGameId;

@Service
public class BowlingGameService {
  private final Map<BowlingGameId, BowlingGame> games = new HashMap<>();

  public BowlingGame findGameById(BowlingGameId gameId) {
    return games.get(gameId);
  }

  public BowlingGame startNewGame() {
    BowlingGame game = BowlingGame.newGameWith(BowlingGameId.next());
    games.put(game.getId(), game);
    return game;
  }
}
