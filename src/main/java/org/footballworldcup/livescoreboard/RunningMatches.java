package org.footballworldcup.livescoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

class RunningMatches {

    private final List<Match> matches;

    RunningMatches() {
        this.matches = new ArrayList<>();
    }

    List<Match> getMatches() {
        return matches;
    }

    void add(String homeTeam, String awayTeam) throws ClashingTeamsException, BlankTeamNameException {
        if (homeTeam == null || homeTeam.isBlank()) throw new BlankTeamNameException("Home team name is empty");
        if (awayTeam == null || awayTeam.isBlank()) throw new BlankTeamNameException("Away team name is empty");
        if (homeTeam.equals(awayTeam)) throw new ClashingTeamsException("A team can't play a match against itself");
        if (!areTeamsFreeToPlay(homeTeam, awayTeam)) throw new ClashingTeamsException("");
        matches.add(new Match(homeTeam, awayTeam));
    }

    void update(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore)
            throws LowerScoreException, MatchNotFoundException {
        for (Match match : matches) {
            if (match.isMatchOfTeams(homeTeam, awayTeam)) {
                if (!match.isHigherScore(homeTeamScore, awayTeamScore)) throw new LowerScoreException("");
                match.setHomeTeamScore(homeTeamScore);
                match.setAwayTeamScore(awayTeamScore);
                return;
            }
        }
        throw new MatchNotFoundException("");
    }

    Match finish(String homeTeam, String awayTeam) throws MatchNotFoundException {
        ListIterator<Match> matchIter = matches.listIterator();
        while(matchIter.hasNext()){
            Match match = matchIter.next();
            if(match.isMatchOfTeams(homeTeam, awayTeam)){
                matchIter.remove();
                return match;
            }
        }
        throw new MatchNotFoundException("");
    }

    private boolean areTeamsFreeToPlay(String homeTeam, String awayTeam) {
        for (Match match : matches) {
            if (match.isClashingByTeamName(homeTeam, awayTeam)) return false;
        }
        return true;
    }

}
