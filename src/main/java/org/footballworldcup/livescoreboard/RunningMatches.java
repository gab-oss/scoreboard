package org.footballworldcup.livescoreboard;

import java.util.LinkedList;
import java.util.List;

public class RunningMatches {

    List<Match> matches;

    RunningMatches() {
        this.matches = new LinkedList<>();
    }

    void add(String homeTeam, String awayTeam) throws ClashingTeamsException {
        if (homeTeam.equals(awayTeam)) throw new ClashingTeamsException("");
        for (Match match : matches) {
            if (match.getHomeTeam().equals(homeTeam)
            || match.getHomeTeam().equals(awayTeam)
            || match.getAwayTeam().equals(homeTeam)) throw new ClashingTeamsException("");
        }
        matches.add(new Match(homeTeam, awayTeam));
    }

    List<Match> getMatches() {
        return matches;
    }
}
