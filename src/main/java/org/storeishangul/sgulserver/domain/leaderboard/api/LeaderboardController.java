package org.storeishangul.sgulserver.domain.leaderboard.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.storeishangul.sgulserver.common.dto.response.RestResponse;
import org.storeishangul.sgulserver.domain.leaderboard.api.dto.request.LeaderboardRankListRequest;
import org.storeishangul.sgulserver.domain.leaderboard.api.dto.response.LeaderboardRankListResponse;
import org.storeishangul.sgulserver.domain.leaderboard.application.LeaderboardApplicationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardApplicationService leaderboardApplicationService;

    @GetMapping("/ranks")
    public RestResponse<LeaderboardRankListResponse> getRankList(LeaderboardRankListRequest request) {

        return RestResponse.ok(leaderboardApplicationService.getTopN(request.getCount()));
    }
}
