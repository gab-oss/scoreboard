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
    public void afterAddingOneMatch_getShouldReturnIt_withZeroScores()
            throws ClashingTeamsException, BlankTeamNameException {
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
    public void whenAddingMatchWithBlankHome_shouldThrowException() {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = " ";
        String awayTeam = "Away";
        String expectedMessage = "Home team name is empty";

        Exception exception = assertThrows(BlankTeamNameException.class, () -> {
            runningMatches.add(homeTeam, awayTeam);
        });

        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void whenAddingMatchWithBlankAway_shouldThrowException() {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = " ";
        String expectedMessage = "Away team name is empty";

        Exception exception = assertThrows(BlankTeamNameException.class, () -> {
            runningMatches.add(homeTeam, awayTeam);
        });

        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void whenAddingMatchWithNullHome_shouldThrowException() {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = null;
        String awayTeam = "Away";
        String expectedMessage = "Home team name is empty";

        Exception exception = assertThrows(BlankTeamNameException.class, () -> {
            runningMatches.add(homeTeam, awayTeam);
        });

        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void whenAddingMatchWithNullAway_shouldThrowException() {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = null;
        String expectedMessage = "Away team name is empty";

        Exception exception = assertThrows(BlankTeamNameException.class, () -> {
            runningMatches.add(homeTeam, awayTeam);
        });

        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void whenAddingMatchOfTeamAgainstItself_shouldThrowException() {
        RunningMatches runningMatches = new RunningMatches();
        String team = "Team";
        String expectedMessage = "A team can't play a match against itself";

        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(team, team);
        });

        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void whenAddingMatchWithHomeAlreadyPlayingAsHome_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // add a valid match with Home
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";
        runningMatches.add(homeTeam, awayTeam);

        String expectedMessage = "Team already playing";

        // add another match with Home
        String awayTeam2 = "Away2";
        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(homeTeam, awayTeam2);
        });

        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void whenAddingMatchWithHomeAlreadyPlayingAsAway_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // add a valid match with Home
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";
        runningMatches.add(homeTeam, awayTeam);

        String expectedMessage = "Team already playing";

        // add another match with Home
        String awayTeam2 = "Away2";
        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(awayTeam2, homeTeam);
        });

        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void whenAddingMatchWithAwayAlreadyPlayingAsHome_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // add a valid match with Home
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";
        runningMatches.add(homeTeam, awayTeam);

        String awayTeam2 = "Away2";

        String expectedMessage = "Team already playing";

        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(awayTeam, awayTeam2);
        });

        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void whenAddingMatchWithAwayAlreadyPlayingAsAway_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // add a valid match with Home
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";
        runningMatches.add(homeTeam, awayTeam);

        String awayTeam2 = "Away2";

        String expectedMessage = "Team already playing";

        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(awayTeam2, awayTeam);
        });

        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void afterUpdatingMatch_shouldHaveUpdatedScore()
            throws ClashingTeamsException, LowerScoreException, MatchNotFoundException, BlankTeamNameException {
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
    public void whenUpdatingMatchWithLowerScoreForHome_shouldThrowException()
            throws ClashingTeamsException, LowerScoreException, MatchNotFoundException, BlankTeamNameException {
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
    public void whenUpdatingMatchWithLowerScoreForAway_shouldThrowException()
            throws ClashingTeamsException, LowerScoreException, MatchNotFoundException, BlankTeamNameException {
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

    @Test
    public void whenUpdatingNonrunningMatch_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";
        int newHomeTeamScore = 1;
        int newAwayTeamScore = 2;

        // matches of teams with same names to check if they won't be updated
        String homeTeam2 = "Home2";
        String awayTeam2 = "Away2";

        runningMatches.add(homeTeam, awayTeam2);
        runningMatches.add(homeTeam2, awayTeam);

        assertThrows(MatchNotFoundException.class, () -> {
            runningMatches.update(homeTeam, awayTeam, newHomeTeamScore, newAwayTeamScore);
        });

        List<Match> matches = runningMatches.getMatches();
        Assert.assertEquals(0, matches.get(0).getHomeTeamScore());
        Assert.assertEquals(0, matches.get(0).getAwayTeamScore());
        Assert.assertEquals(0, matches.get(1).getHomeTeamScore());
        Assert.assertEquals(0, matches.get(1).getHomeTeamScore()); // to fix
    }

    @Test
    public void givenNoMatches_whenFinishingMatch_throwException() {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";

        assertThrows(MatchNotFoundException.class, () -> {
            runningMatches.finish(homeTeam, awayTeam);
        });
    }

    @Test
    public void givenRunningMatch_finishMatchShouldReturnIt()
            throws MatchNotFoundException, ClashingTeamsException, BlankTeamNameException {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";

        runningMatches.add(homeTeam, awayTeam);

        Match match = runningMatches.finish(homeTeam, awayTeam);
        Assert.assertTrue(runningMatches.getMatches().isEmpty());
        Assert.assertEquals(homeTeam, match.getHomeTeam());
        Assert.assertEquals(awayTeam, match.getAwayTeam());
    }

    @Test
    public void givenMatches_whenFinishingNonrunningMatch_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        RunningMatches runningMatches = new RunningMatches();
        String homeTeam = "Home";
        String awayTeam = "Away";

        // matches of teams with same names to check if they won't be removed
        String homeTeam2 = "Home2";
        String awayTeam2 = "Away2";

        runningMatches.add(homeTeam, awayTeam2);
        runningMatches.add(homeTeam2, awayTeam);

        assertThrows(MatchNotFoundException.class, () -> {
            runningMatches.finish(homeTeam, awayTeam);
        });

        List<Match> matches = runningMatches.getMatches();
        Assert.assertEquals(2, matches.size());
        Assert.assertEquals(homeTeam, matches.get(0).getHomeTeam());
        Assert.assertEquals(awayTeam2, matches.get(0).getAwayTeam());
        Assert.assertEquals(homeTeam2, matches.get(1).getHomeTeam());
        Assert.assertEquals(awayTeam, matches.get(1).getAwayTeam());
    }

}
