package org.footballworldcup.livescoreboard;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThrows;

public class RunningMatchesTests {

    @Test
    public void whenNoMatches_getShouldReturnEmptyList() {
        RunningMatches runningMatches = new RunningMatches();
        Assert.assertTrue(runningMatches.getMatches().isEmpty());
    }

    @Test
    public void whenMatchAddedWithDifferentTeams_getShouldReturnIt_withZeroScores() throws ClashingTeamsException {
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

    @Test
    public void whenMatchAddedOfTeamAgainstItself_shouldThrowException() {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(homeTeam, homeTeam);
        });
    }

    @Test
    public void whenMatchAddedButHomeTeamAlreadyPlayingAsHome_shouldThrowException() throws ClashingTeamsException {
        // add a valid match with Home
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";
        runningMatches.add(homeTeam, awayTeam);

        // add another match with Home
        String awayTeam2 = "Away2";

        assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(homeTeam, awayTeam2);
        });
    }

    @Test
    public void whenMatchAddedButHomeTeamAlreadyPlayingAsAway_shouldThrowException() throws ClashingTeamsException {
        // add a valid match with Home
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";
        runningMatches.add(homeTeam, awayTeam);

        // add another match with Home
        String awayTeam2 = "Away2";

        assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(awayTeam2, homeTeam);
        });
    }

}
