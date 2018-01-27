package ua.kata.services;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ua.kata.model.BowlingGame;
import ua.kata.model.BowlingGameId;

@Service
public class BowlingGameService {
  private final Map<BowlingGameId, BowlingGame> games = new ConcurrentHashMap<>();

  public BowlingGame createGame() {
    return games.computeIfAbsent(BowlingGameId.generate(), BowlingGame::newGameWith);
  }

  public BowlingGame findGameById(BowlingGameId id) {
    return games.get(id);
  }
}
