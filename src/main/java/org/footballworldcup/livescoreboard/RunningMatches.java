package org.footballworldcup.livescoreboard;

import java.util.LinkedList;
import java.util.List;

public class RunningMatches {

    List<Match> matches;

    RunningMatches() {
        this.matches = new LinkedList<>();
    }

    void add(String homeTeam, String awayTeam) {
        matches.add(new Match(homeTeam, awayTeam));
    }

    List<Match> getMatches() {
        return matches;
    }
}
