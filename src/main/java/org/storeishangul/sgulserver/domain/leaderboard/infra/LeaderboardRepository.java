package org.storeishangul.sgulserver.domain.leaderboard.infra;

import jakarta.annotation.Nullable;
import java.util.List;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElement;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElementWithRank;

public interface LeaderboardRepository {

    void save(LeaderboardElement leaderboardElement);

    void deleteById(String id);

    @Nullable
    LeaderboardElement findById(String id);

    LeaderboardElementWithRank findByIdWithRank(String id);

    List<LeaderboardElementWithRank> findTopN(int topN);
}
