package org.footballworldcup.livescoreboard;

import org.footballworldcup.livescoreboard.exceptions.BlankTeamNameException;
import org.footballworldcup.livescoreboard.exceptions.ClashingTeamsException;
import org.footballworldcup.livescoreboard.exceptions.LowerScoreException;
import org.footballworldcup.livescoreboard.exceptions.MatchNotFoundException;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Scoreboard {

    private final RunningMatches runningMatches;
    private final List<Match> matches;
    private final MatchesComparator matchesComparator;
    private int nextMatchNo;

    Scoreboard() {
        this.runningMatches = new RunningMatches();
        this.matches = new LinkedList<>();
        this.matchesComparator = new MatchesComparator();
        this.nextMatchNo = 0;
    }

    public List<Match> getSummary() {
        return Stream
                .concat(runningMatches.getMatches().stream(), matches.stream())
                .sorted(matchesComparator)
                .toList();
    }

    public void start(String homeTeam, String awayTeam)
            throws ClashingTeamsException, BlankTeamNameException {
        runningMatches.add(homeTeam, awayTeam, nextMatchNo);
        nextMatchNo += 1;
    }

    public void finish(String homeTeam, String awayTeam) throws MatchNotFoundException {
        matches.add(runningMatches.finish(homeTeam, awayTeam));
    }

    public void update(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) 
            throws LowerScoreException, MatchNotFoundException {
        runningMatches.update(homeTeam, awayTeam, homeTeamScore, awayTeamScore);
    }
    
}
