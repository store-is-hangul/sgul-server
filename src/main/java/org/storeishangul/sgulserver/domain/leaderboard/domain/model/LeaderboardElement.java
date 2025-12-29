package org.storeishangul.sgulserver.domain.leaderboard.domain.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.Getter;

@Getter
public class LeaderboardElement implements Comparable<LeaderboardElement> {

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

        int scoreCompare = o.getScore() - this.score;

        if (scoreCompare != 0) {
            return scoreCompare;
        }

        // 점수가 같으면 ID로 비교하여 중복 제거 방지
        return this.id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return "LeaderboardElement{" +
            "id='" + id + '\'' +
            ", userName='" + userName + '\'' +
            ", score=" + score +
            '}';
    }
}
