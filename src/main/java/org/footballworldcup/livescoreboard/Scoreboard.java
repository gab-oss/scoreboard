package org.footballworldcup.livescoreboard;

import org.footballworldcup.livescoreboard.exceptions.BlankTeamNameException;
import org.footballworldcup.livescoreboard.exceptions.ClashingTeamsException;
import org.footballworldcup.livescoreboard.exceptions.LowerScoreException;
import org.footballworldcup.livescoreboard.exceptions.MatchNotFoundException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Scoreboard {

    private final RunningMatches runningMatches;
    private final List<Match> finishedMatches;
    private final MatchesComparator matchesComparator;
    private int nextMatchNo;

    Scoreboard() {
        this.runningMatches = new RunningMatches();
        this.finishedMatches = new LinkedList<>();
        this.matchesComparator = new MatchesComparator();
        this.nextMatchNo = 0;
    }

    List<Match> getMatches() { // todo move
        return Stream
                .concat(runningMatches.getMatches().stream(), finishedMatches.stream())
                .sorted(matchesComparator)
                .toList();
    }

    List<SummarizedMatch> getSummary() {
        return new ArrayList<>();
    }

    public void start(String homeTeam, String awayTeam)
            throws ClashingTeamsException, BlankTeamNameException {
        runningMatches.add(homeTeam, awayTeam, nextMatchNo);
        nextMatchNo += 1;
    }

    public void finish(String homeTeam, String awayTeam) throws MatchNotFoundException {
        finishedMatches.add(runningMatches.finish(homeTeam, awayTeam));
    }

    public void update(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) 
            throws LowerScoreException, MatchNotFoundException {
        runningMatches.update(homeTeam, awayTeam, homeTeamScore, awayTeamScore);
    }
    
}
