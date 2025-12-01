package org.storeishangul.sgulserver.domain.gameplay.infra;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;

@Repository
public class GameSessionInMemRepository implements GameSessionRepository{

    private final Map<String, GameSession> gameSessions;

    public GameSessionInMemRepository() {

        this.gameSessions = new HashMap<>();
    }

    @Override
    public GameSession findByUserId(String userId) {

        return gameSessions.get(userId);
    }

    @Override
    public GameSession save(GameSession gameSession) {

        gameSessions.put(gameSession.getUserId(), gameSession);

        return gameSession;
    }

    @Override
    public GameSession update(GameSession gameSession) {

        gameSessions.put(gameSession.getUserId(), gameSession);

        return gameSession;
    }

    @Override
    public void deleteByUserId(String userId) {

        gameSessions.remove(userId);
    }
}
