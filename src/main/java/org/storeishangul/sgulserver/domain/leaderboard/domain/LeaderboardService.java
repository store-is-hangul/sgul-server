package org.storeishangul.sgulserver.domain.leaderboard.domain;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElement;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElementWithRank;
import org.storeishangul.sgulserver.domain.leaderboard.infra.LeaderboardRepository;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;

    public LeaderboardElementWithRank saveLeaderboardElement(String userName, int score) {

        LeaderboardElement leaderboardElement = LeaderboardElement.of(userName, score);
        leaderboardRepository.save(leaderboardElement);

        return leaderboardRepository.findByIdWithRank(leaderboardElement.getId());
    }

    public List<LeaderboardElementWithRank> findTopN(int n) {

        return leaderboardRepository.findTopN(n);
    }
}
