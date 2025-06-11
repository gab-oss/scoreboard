package org.footballworldcup.livescoreboard;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RunningMatchesTests {

    @Test
    public void whenNoMatches_getShouldReturnEmptyList() {
        RunningMatches runningMatches = new RunningMatches();
        Assert.assertTrue(runningMatches.getMatches().isEmpty());
    }

    @Test
    public void whenMatchAddedWithDifferentTeams_getShouldReturnIt_withZeroScores() {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";
        runningMatches.add(homeTeam, awayTeam);
        List<Match> matches = runningMatches.getMatches();
        Match match = matches.getFirst();

        Assert.assertEquals(1, matches.size());
        Assert.assertEquals(homeTeam, match.getHomeTeam());
        Assert.assertEquals(awayTeam, match.getAwayTeam());
        Assert.assertEquals(0, match.getHomeTeamScore());
        Assert.assertEquals(0, match.getAwayTeamScore());
    }

}
