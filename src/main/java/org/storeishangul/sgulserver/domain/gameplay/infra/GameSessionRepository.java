package org.storeishangul.sgulserver.domain.gameplay.infra;

import jakarta.annotation.Nullable;
import java.time.LocalDateTime;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;

public interface GameSessionRepository {

    @Nullable
    GameSession findActivatingByUserId(String userId);

    GameSession save(GameSession gameSession);

    GameSession update(GameSession gameSession);

    void deleteByUserId(String userId);

    void deleteActivatingAfter(LocalDateTime from);
}
