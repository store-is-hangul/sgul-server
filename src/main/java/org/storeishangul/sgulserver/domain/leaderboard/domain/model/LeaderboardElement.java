package org.storeishangul.sgulserver.domain.leaderboard.domain.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.Getter;

@Getter
public class LeaderboardElement implements Comparable<LeaderboardElement>{

    private final String id;
    private String userName;
    private int score;

    protected LeaderboardElement(String id, String userName, int score) {
        this.id = id;
        this.userName = userName;
        this.score = score;
    }

    public static LeaderboardElement of(String userName, int score) {
        return new LeaderboardElement(
            userName + LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli(),
            userName,
            score
        );
    }

    @Override
    public int compareTo(LeaderboardElement o) {

        return o.getScore() - this.score;
    }
}
