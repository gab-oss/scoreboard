package org.footballworldcup.livescoreboard;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThrows;

public class RunningMatchesTests {

    @Test
    public void givenNoMatches_getShouldReturnEmptyList() {
        RunningMatches runningMatches = new RunningMatches();
        Assert.assertTrue(runningMatches.getMatches().isEmpty());
    }

    @Test
    public void afterAddingOneMatch_getShouldReturnIt_withZeroScores() throws ClashingTeamsException {
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
    public void whenAddingMatchWithNullHome_shouldThrowException() {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        assertThrows(NullTeamNameException.class, () -> {
           runningMatches.add(homeTeam, null);
        });
    }

    @Test
    public void whenAddingMatchWithNullAway_shouldThrowException() {
        RunningMatches runningMatches = new RunningMatches();
        String awayTeam = "Away";
        assertThrows(NullTeamNameException.class, () -> {
            runningMatches.add(null, awayTeam);
        });
    }

    @Test
    public void whenAddingMatchOfTeamAgainstItself_shouldThrowException() {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(homeTeam, homeTeam);
        });
    }

    @Test
    public void whenAddingMatchWithHomeAlreadyPlayingAsHome_shouldThrowException() throws ClashingTeamsException {
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
    public void whenAddingMatchWithHomeAlreadyPlayingAsAway_shouldThrowException() throws ClashingTeamsException {
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
    public void whenAddingMatchWithAwayAlreadyPlayingAsHome_shouldThrowException() throws ClashingTeamsException {
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
    public void whenAddingMatchWithAwayAlreadyPlayingAsAway_shouldThrowException() throws ClashingTeamsException {
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
    public void afterUpdatingMatch_shouldHaveUpdatedScore() throws ClashingTeamsException, LowerScoreException {
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
    public void whenUpdatingMatchWithLowerScoreForHome_shouldThrowException() throws ClashingTeamsException, LowerScoreException {
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
    public void whenUpdatingMatchWithLowerScoreForAway_shouldThrowException() throws ClashingTeamsException, LowerScoreException {
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
