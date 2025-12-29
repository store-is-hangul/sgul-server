package org.storeishangul.sgulserver.domain.leaderboard.api.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LeaderboardRankListRequest {

    private Integer count;

    public Integer getCount() {

        return count == null ? 10 : count;
    }
}
