package org.storeishangul.sgulserver.domain.leaderboard.api.dto.response;

import java.util.List;
import lombok.Getter;
import org.springframework.util.CollectionUtils;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElementWithRank;

@Getter
public class LeaderboardRankListResponse {

    private List<LeaderboardRankResponse> ranks;

    public LeaderboardRankListResponse(List<LeaderboardRankResponse> ranks) {

        this.ranks = ranks;
    }

    public static LeaderboardRankListResponse from(List<LeaderboardElementWithRank> leaderboardElementWithRanks) {

        if (CollectionUtils.isEmpty(leaderboardElementWithRanks)) {

            return new LeaderboardRankListResponse(List.of());
        }

        return new LeaderboardRankListResponse(leaderboardElementWithRanks.stream().map(LeaderboardRankResponse::from).toList());
    }
}
