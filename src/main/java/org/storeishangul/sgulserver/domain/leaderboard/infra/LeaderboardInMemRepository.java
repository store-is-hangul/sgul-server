package org.storeishangul.sgulserver.domain.leaderboard.infra;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElement;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElementWithRank;

@Slf4j
@Repository
public class LeaderboardInMemRepository implements LeaderboardRepository {

    private final ConcurrentSkipListSet<LeaderboardElement> leaderboard;

    public LeaderboardInMemRepository() {

        this.leaderboard = new ConcurrentSkipListSet<>(LeaderboardElement::compareTo);
    }

    @Override
    public void save(LeaderboardElement leaderboardElement) {

        log.info("[Leaderboard] Saving leaderboard element: {}", leaderboardElement);
        leaderboard.add(leaderboardElement);
    }

    @Override
    public void deleteById(String id) {
        leaderboard.removeIf(leaderboardElement -> leaderboardElement.getId().equals(id));
    }

    @Override
    public LeaderboardElement findById(String id) {

        return leaderboard.stream()
            .filter(e -> e.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public LeaderboardElementWithRank findByIdWithRank(String id) {

        int rank = 1;

        for (LeaderboardElement element : leaderboard) {
            if (element.getId().equals(id)) {
                return LeaderboardElementWithRank.of(element, rank);
            }
            rank++;
        }

        return null;
    }

    @Override
    public List<LeaderboardElementWithRank> findTopN(int topN) {

        AtomicInteger rank = new AtomicInteger(1);

        return leaderboard.stream()
            .limit(topN)
            .map(e -> LeaderboardElementWithRank.of(e, rank.getAndIncrement()))
            .toList();
    }
}
