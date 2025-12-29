package org.storeishangul.sgulserver.domain.leaderboard.domain;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElement;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElementWithRank;
import org.storeishangul.sgulserver.domain.leaderboard.infra.LeaderboardRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;

    public LeaderboardElementWithRank saveLeaderboardElement(GameSession gameSession, String userName) {

        LeaderboardElement leaderboardElement = LeaderboardElement.of(userName, gameSession.getTotalScore());
        log.info("[Leaderboard] Saving leaderboard element: {}", leaderboardElement);
        leaderboardRepository.save(leaderboardElement);

        LeaderboardElementWithRank saved = leaderboardRepository.findByIdWithRank(leaderboardElement.getId());
        log.info("[Leaderboard] Saved leaderboard element: {}", saved);
        return saved;
    }

    public List<LeaderboardElementWithRank> findTopN(int n) {

        return leaderboardRepository.findTopN(n);
    }
}
