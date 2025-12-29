package org.storeishangul.sgulserver.domain.leaderboard.infra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElement;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElementWithRank;

@Slf4j
@Repository
public class LeaderboardInMemRepository implements LeaderboardRepository {

    private PriorityQueue<LeaderboardElement> leaderboard;

    public LeaderboardInMemRepository() {

        this.leaderboard = new PriorityQueue<>(LeaderboardElement::compareTo);
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

        Optional<LeaderboardElement> targetOptional = leaderboard.stream()
            .filter(leaderboardElement -> leaderboardElement.getId().equals(id)).findFirst();

        return targetOptional.orElse(null);
    }

    @Override
    public LeaderboardElementWithRank findByIdWithRank(String id) {

        Iterator<LeaderboardElement> iterator = leaderboard.iterator();
        int rank = 1;
        while (iterator.hasNext()) {
            LeaderboardElement leaderboardElement = iterator.next();
            if (leaderboardElement.getId().equals(id)) {
                return LeaderboardElementWithRank.of(leaderboardElement, rank);
            }
            rank++;
        }
        return null;
    }

    @Override
    public List<LeaderboardElementWithRank> findTopN(int topN) {

        AtomicInteger rank = new AtomicInteger(1);
        ArrayList<LeaderboardElementWithRank> result = new ArrayList<>();

        while (topN > 0 && !leaderboard.isEmpty()) {
            LeaderboardElementWithRank leaderboardElementWithRank = LeaderboardElementWithRank.of(leaderboard.poll(), rank.getAndIncrement());
            result.add(leaderboardElementWithRank);
            topN--;
        }

        return result;
    }
}
