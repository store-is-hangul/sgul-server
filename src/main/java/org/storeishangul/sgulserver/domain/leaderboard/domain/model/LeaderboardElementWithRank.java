package org.storeishangul.sgulserver.domain.leaderboard.domain.model;

import lombok.Getter;

@Getter
public class LeaderboardElementWithRank extends LeaderboardElement{

    private int rank;

    private LeaderboardElementWithRank(String id, String userName, int score, int rank) {
        super(id, userName, score);
        this.rank = rank;
    }

    public static LeaderboardElementWithRank of(LeaderboardElement leaderboardElement, int rank) {
        return new LeaderboardElementWithRank(leaderboardElement.getId(), leaderboardElement.getUserName(), leaderboardElement.getScore(), rank);
    }
}
