package org.storeishangul.sgulserver.domain.leaderboard.infra;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Repository;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElement;
import org.storeishangul.sgulserver.domain.leaderboard.domain.model.LeaderboardElementWithRank;

@Repository
public class LeaderboardInMemRepository implements LeaderboardRepository {

    private PriorityQueue<LeaderboardElement> leaderboard;

    public LeaderboardInMemRepository() {

        this.leaderboard = new PriorityQueue<>();
    }

    @Override
    public void save(LeaderboardElement leaderboardElement) {

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

        return leaderboard.stream()
            .limit(topN)
            .map(e -> LeaderboardElementWithRank.of(e, rank.getAndIncrement()))
            .toList();
    }
}
