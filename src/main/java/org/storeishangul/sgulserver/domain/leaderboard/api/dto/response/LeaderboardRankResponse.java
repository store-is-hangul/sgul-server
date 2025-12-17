package org.storeishangul.sgulserver.domain.leaderboard.api.dto.response;

import lombok.Getter;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElementWithRank;

@Getter
public class LeaderboardRankResponse {

    private String userName;
    private int score;
    private int rank;

    private LeaderboardRankResponse(String userName, int score, int rank) {
        this.userName = userName;
        this.score = score;
        this.rank = rank;
    }

    public static LeaderboardRankResponse from(LeaderboardElementWithRank leaderboardElementWithRank) {

        if (leaderboardElementWithRank == null) {

            return null;
        }

        return new LeaderboardRankResponse(leaderboardElementWithRank.getUserName(), leaderboardElementWithRank.getScore(), leaderboardElementWithRank.getRank());
    }
}
