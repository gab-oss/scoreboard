package org.footballworldcup.livescoreboard;

import java.util.LinkedList;
import java.util.List;

public class RunningMatches {

    List<Match> matches;

    RunningMatches() {
        this.matches = new LinkedList<>();
    }

    void add(String homeTeam, String awayTeam) throws ClashingTeamsException, BlankTeamNameException {
        if (homeTeam.isBlank() || awayTeam.isBlank()) throw new BlankTeamNameException("");
        if (homeTeam.equals(awayTeam)) throw new ClashingTeamsException("");
        for (Match match : matches) {
            if (match.getHomeTeam().equals(homeTeam)
            || match.getHomeTeam().equals(awayTeam)
            || match.getAwayTeam().equals(homeTeam)
            || match.getAwayTeam().equals(awayTeam)) throw new ClashingTeamsException("");
        }
        matches.add(new Match(homeTeam, awayTeam));
    }

    List<Match> getMatches() {
        return matches;
    }

    public void update(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) throws LowerScoreException {
        for (Match match : matches) {
            if (match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam) ) {
                if (match.getHomeTeamScore() > homeTeamScore
                || match.getAwayTeamScore() > awayTeamScore) throw new LowerScoreException("");
                match.setHomeTeamScore(homeTeamScore);
                match.setAwayTeamScore(awayTeamScore);
            }
        }
    }
}
