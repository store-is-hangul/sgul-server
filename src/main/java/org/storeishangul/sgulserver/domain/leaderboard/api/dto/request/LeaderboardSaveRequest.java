package org.storeishangul.sgulserver.domain.leaderboard.api.dto.request;

import lombok.Getter;

@Getter
public class LeaderboardSaveRequest {

    private String userName;
    private int score;
}
