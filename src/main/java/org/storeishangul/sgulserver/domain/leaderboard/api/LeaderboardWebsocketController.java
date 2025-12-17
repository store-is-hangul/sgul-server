package org.storeishangul.sgulserver.domain.leaderboard.api;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.storeishangul.sgulserver.domain.leaderboard.api.dto.request.LeaderboardSaveRequest;
import org.storeishangul.sgulserver.domain.leaderboard.application.LeaderboardApplicationService;

@Controller
@RequiredArgsConstructor
public class LeaderboardWebsocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final LeaderboardApplicationService leaderboardApplicationService;

    @MessageMapping("/leaderboard/save")
    public void saveLeaderboard(Principal principal, LeaderboardSaveRequest request) {

        String userId = principal.getName();

        messagingTemplate.convertAndSendToUser(
            userId,
            "/queue/game",
            leaderboardApplicationService.saveLeaderboardAndReturnRank(
                userId,
                request.getUserName(),
                request.getScore()
            )
        );
    }
}
