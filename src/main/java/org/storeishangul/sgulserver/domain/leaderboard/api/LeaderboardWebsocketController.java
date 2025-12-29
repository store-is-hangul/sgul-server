package org.storeishangul.sgulserver.domain.leaderboard.api;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.storeishangul.sgulserver.domain.leaderboard.api.dto.request.LeaderboardSaveRequest;
import org.storeishangul.sgulserver.domain.leaderboard.api.dto.response.LeaderboardRankResponse;
import org.storeishangul.sgulserver.domain.leaderboard.application.LeaderboardApplicationService;

@Controller
@RequiredArgsConstructor
public class LeaderboardWebsocketController {

    private final LeaderboardApplicationService leaderboardApplicationService;

    @MessageMapping("/leaderboard/save")
    @SendToUser("/queue/leaderboard/save")
    public LeaderboardRankResponse saveLeaderboard(Principal principal, SimpMessageHeaderAccessor accessor, LeaderboardSaveRequest request) {

        String userId = principal.getName();

        return leaderboardApplicationService.saveLeaderboardAndReturnRank(
            userId,
            accessor.getSessionId(),
            request.getUserName()
        );
    }
}
