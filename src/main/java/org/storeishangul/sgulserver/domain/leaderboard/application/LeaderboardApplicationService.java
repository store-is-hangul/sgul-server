package org.storeishangul.sgulserver.domain.leaderboard.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.storeishangul.sgulserver.domain.gameplay.domain.GameSessionService;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;
import org.storeishangul.sgulserver.domain.leaderboard.api.dto.response.LeaderboardRankListResponse;
import org.storeishangul.sgulserver.domain.leaderboard.api.dto.response.LeaderboardRankResponse;
import org.storeishangul.sgulserver.domain.leaderboard.domain.LeaderboardService;

@Service
@RequiredArgsConstructor
public class LeaderboardApplicationService {

    private final LeaderboardService leaderboardService;
    private final GameSessionService gameSessionService;

    public LeaderboardRankResponse saveLeaderboardAndReturnRank(String userId, String sessionId, String userName) {

        GameSession gameSession = gameSessionService.findSessionAndUpdateByUserIdOrElseThrow(userId, sessionId);

        return LeaderboardRankResponse.from(leaderboardService.saveLeaderboardElement(gameSession, userName));
    }

    public LeaderboardRankListResponse getTopN(int n) {

        return LeaderboardRankListResponse.from(leaderboardService.findTopN(n));
    }
}
