package org.footballworldcup.livescoreboard;

import org.footballworldcup.livescoreboard.exceptions.BlankTeamNameException;
import org.footballworldcup.livescoreboard.exceptions.ClashingTeamsException;
import org.footballworldcup.livescoreboard.exceptions.LowerScoreException;
import org.footballworldcup.livescoreboard.exceptions.MatchNotFoundException;

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

    void add(String homeTeam, String awayTeam, int orderNo) throws ClashingTeamsException, BlankTeamNameException {
        if (homeTeam == null || homeTeam.isBlank()) throw new BlankTeamNameException("Home team name is empty");
        if (awayTeam == null || awayTeam.isBlank()) throw new BlankTeamNameException("Away team name is empty");
        if (homeTeam.equals(awayTeam)) throw new ClashingTeamsException("A team can't play a match against itself");
        if (!areTeamsFreeToPlay(homeTeam, awayTeam)) throw new ClashingTeamsException("Team already playing");
        matches.add(new Match(homeTeam, awayTeam, orderNo));
    }

    void update(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore)
            throws LowerScoreException, MatchNotFoundException {
        for (Match match : matches) {
            if (match.isMatchOfTeams(homeTeam, awayTeam)) {
                if (!match.isNotLowerScore(homeTeamScore, awayTeamScore))
                    throw new LowerScoreException("Score can't be lowered");
                match.setHomeTeamScore(homeTeamScore);
                match.setAwayTeamScore(awayTeamScore);
                return;
            }
        }
        throw new MatchNotFoundException("Match not found");
    }

    void finish(String homeTeam, String awayTeam) throws MatchNotFoundException {
        ListIterator<Match> matchIter = matches.listIterator();
        while(matchIter.hasNext()){
            Match match = matchIter.next();
            if(match.isMatchOfTeams(homeTeam, awayTeam)){
                matchIter.remove();
                return;
            }
        }
        throw new MatchNotFoundException("Match not found");
    }

    private boolean areTeamsFreeToPlay(String homeTeam, String awayTeam) {
        for (Match match : matches) {
            if (match.isClashingByTeamName(homeTeam, awayTeam)) return false;
        }
        return true;
    }

}
