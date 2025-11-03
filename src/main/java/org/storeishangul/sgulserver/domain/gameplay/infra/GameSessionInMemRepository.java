package org.storeishangul.sgulserver.domain.gameplay.infra;

import org.springframework.stereotype.Repository;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;

@Repository
public class GameSessionInMemRepository implements GameSessionRepository{

    @Override
    public GameSession findByUserId(String userId) {
        //TODO

        return null;
    }

    @Override
    public void save(GameSession gameSession) {
        //TODO
    }

    @Override
    public void update(GameSession gameSession) {
        //TODO
    }

    @Override
    public void deleteByUserId(String userId) {
        //TODO
    }
}
