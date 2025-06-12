package org.footballworldcup.livescoreboard;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Scoreboard {

    private RunningMatches runningMatches;
    private List<Match> matches; // todo change name, final
    private MatchesComparator matchesComparator;

    Scoreboard() {
        this.runningMatches = new RunningMatches();
        this.matches = new LinkedList<>();
        this.matchesComparator = new MatchesComparator();
    }

    public List<Match> getSummary() {
        return Stream
                .concat(runningMatches.getMatches().stream(), matches.stream())
                .sorted(matchesComparator)
                .toList();
    }

    public void start(String homeTeam, String awayTeam)
            throws ClashingTeamsException, BlankTeamNameException {
        runningMatches.add(homeTeam, awayTeam);
    }

    public void finish(String homeTeam, String awayTeam) throws MatchNotFoundException {
        matches.add(runningMatches.finish(homeTeam, awayTeam));
    }

    public void update(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) 
            throws LowerScoreException, MatchNotFoundException {
        runningMatches.update(homeTeam, awayTeam, homeTeamScore, awayTeamScore);
    }
    
}
