package org.footballworldcup.livescoreboard;

import org.footballworldcup.livescoreboard.exceptions.BlankTeamNameException;
import org.footballworldcup.livescoreboard.exceptions.ClashingTeamsException;
import org.footballworldcup.livescoreboard.exceptions.LowerScoreException;
import org.footballworldcup.livescoreboard.exceptions.MatchNotFoundException;

import java.util.List;

public class LiveScoreboard implements Scoreboard {

    private final RunningMatches runningMatches;
    private final MatchesComparator matchesComparator;
    private int nextMatchNo;

    LiveScoreboard() {
        this.runningMatches = new RunningMatches();
        this.matchesComparator = new MatchesComparator();
        this.nextMatchNo = 0;
    }

    @Override
    public List<SummarizedMatch> getSummary() {
        return summarize(getMatches());
    }

    @Override
    public void start(String homeTeam, String awayTeam)
            throws ClashingTeamsException, BlankTeamNameException {
        runningMatches.add(homeTeam, awayTeam, nextMatchNo);
        nextMatchNo += 1;
    }

    @Override
    public void update(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore)
            throws LowerScoreException, MatchNotFoundException {
        runningMatches.update(homeTeam, awayTeam, homeTeamScore, awayTeamScore);
    }

    @Override
    public void finish(String homeTeam, String awayTeam) throws MatchNotFoundException {
        runningMatches.finish(homeTeam, awayTeam);
    }

    List<Match> getMatches() {
        return runningMatches.getMatches()
                .stream()
                .sorted(matchesComparator)
                .toList();
    }

    private List<SummarizedMatch> summarize(List<Match> matches) {
        return matches
                .stream()
                .map(match -> new SummarizedMatch(
                        match.getHomeTeam(), match.getAwayTeam(), match.getHomeTeamScore(), match.getAwayTeamScore())
                )
                .toList();
    }

}
