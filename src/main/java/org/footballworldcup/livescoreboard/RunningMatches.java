package org.footballworldcup.livescoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class RunningMatches {

    List<Match> matches;

    RunningMatches() {
        this.matches = new ArrayList<>();
    }

    void add(String homeTeam, String awayTeam) throws ClashingTeamsException, BlankTeamNameException {
        if ( homeTeam == null || homeTeam.isBlank()
                || awayTeam == null || awayTeam.isBlank()) throw new BlankTeamNameException("");
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

    public void update(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) throws LowerScoreException, MatchNotFoundException {
        for (Match match : matches) {
            if (match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam) ) {
                if (match.getHomeTeamScore() > homeTeamScore
                || match.getAwayTeamScore() > awayTeamScore) throw new LowerScoreException("");
                match.setHomeTeamScore(homeTeamScore);
                match.setAwayTeamScore(awayTeamScore);
                return;
            }
        }
        throw new MatchNotFoundException("");
    }

    public Match finish(String homeTeam, String awayTeam) throws MatchNotFoundException {
        ListIterator<Match> iter = matches.listIterator();
        while(iter.hasNext()){
            Match match = iter.next();
            if(match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam)){
                iter.remove();
                return match;
            }
        }
        throw new MatchNotFoundException("");
    }
}
