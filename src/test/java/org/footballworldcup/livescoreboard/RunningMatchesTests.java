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
        Match match = matches.get(0);

        Assert.assertEquals(matches.size(), 1);
        Assert.assertEquals(match.getHomeTeam(), homeTeam);
        Assert.assertEquals(match.getAwayTeam(), awayTeam);
        Assert.assertEquals(match.getHomeTeamScore(), 0);
        Assert.assertEquals(match.getAwayTeamScore(), 0);
    }
}
