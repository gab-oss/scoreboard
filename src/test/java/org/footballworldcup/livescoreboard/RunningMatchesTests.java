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

    @Test
    public void whenMatchAddedButAwayTeamAlreadyPlayingAsHome_shouldThrowException() throws ClashingTeamsException {
        // add a valid match with Home
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";
        runningMatches.add(homeTeam, awayTeam);

        String awayTeam2 = "Away2";

        assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(awayTeam, awayTeam2);
        });
    }

    @Test
    public void whenMatchAddedButAwayTeamAlreadyPlayingAsAway_shouldThrowException() throws ClashingTeamsException {
        // add a valid match with Home
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";
        runningMatches.add(homeTeam, awayTeam);

        String awayTeam2 = "Away2";

        assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(awayTeam2, awayTeam);
        });
    }

    @Test
    public void whenMatchUpdatedAndExists_shouldHaveUpdatedScore() throws ClashingTeamsException, LowerScoreException {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";
        int newHomeScore = 1;
        int newAwayScore = 2;
        runningMatches.add(homeTeam, awayTeam);

        runningMatches.update(homeTeam, awayTeam, newHomeScore, newAwayScore);

        List<Match> matches = runningMatches.getMatches();
        Match match = matches.getFirst();

        Assert.assertEquals(1, matches.size());
        Assert.assertEquals(homeTeam, match.getHomeTeam());
        Assert.assertEquals(awayTeam, match.getAwayTeam());
        Assert.assertEquals(newHomeScore, match.getHomeTeamScore());
        Assert.assertEquals(newAwayScore, match.getAwayTeamScore());
    }

    @Test
    public void whenMatchUpdatedWithLowerScoreForHome_shouldThrowException() throws ClashingTeamsException, LowerScoreException {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";
        int firstUpdateHomeScore = 1;
        int secondUpdateHomeScore = 0;
        int newAwayScore = 2;
        runningMatches.add(homeTeam, awayTeam);
        runningMatches.update(homeTeam, awayTeam, firstUpdateHomeScore, newAwayScore);

        assertThrows(LowerScoreException.class, () -> {
            runningMatches.update(homeTeam, awayTeam, secondUpdateHomeScore, newAwayScore);
        });
    }

    @Test
    public void whenMatchUpdatedWithLowerScoreForAway_shouldThrowException() throws ClashingTeamsException, LowerScoreException {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";
        int newHomeScore = 2;
        int firstUpdateAwayScore = 1;
        int secondUpdateAwayScore = 0;

        runningMatches.add(homeTeam, awayTeam);
        runningMatches.update(homeTeam, awayTeam, newHomeScore, firstUpdateAwayScore);

        assertThrows(LowerScoreException.class, () -> {
            runningMatches.update(homeTeam, awayTeam, newHomeScore, secondUpdateAwayScore);
        });
    }

}
