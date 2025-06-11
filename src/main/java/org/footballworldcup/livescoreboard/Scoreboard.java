package org.footballworldcup.livescoreboard;

import java.util.LinkedList;
import java.util.List;

public class Scoreboard {

    private List<Match> matches;

    Scoreboard() {
        this.matches = new LinkedList<>();
    }

    public List<Match> getSummary() {
        return matches;
    }

    public void start(String homeTeam, String awayTeam) {
        matches.add(new Match());
    }
}
