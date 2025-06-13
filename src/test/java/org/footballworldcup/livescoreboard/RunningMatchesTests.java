package org.footballworldcup.livescoreboard;

import org.footballworldcup.livescoreboard.exceptions.BlankTeamNameException;
import org.footballworldcup.livescoreboard.exceptions.ClashingTeamsException;
import org.footballworldcup.livescoreboard.exceptions.LowerScoreException;
import org.footballworldcup.livescoreboard.exceptions.MatchNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThrows;

public class RunningMatchesTests {

    RunningMatches runningMatches;

    @Before
    public void initialize() {
        this.runningMatches = new RunningMatches();
    }

    @Test
    public void givenNoMatches_getShouldReturnEmptyList() {
        Assert.assertTrue(runningMatches.getMatches().isEmpty());
    }

    @Test
    public void add_whenNoClashingMatches_shouldAddItWithZeroScore()
            throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";

        // act
        runningMatches.add(homeTeam, awayTeam, 0);

        // assert
        List<Match> matches = runningMatches.getMatches();
        Match match = matches.getFirst();
        Assert.assertEquals(1, matches.size());
        assertMatchAsExpected(match, homeTeam, awayTeam, 0, 0);
    }

    @Test
    public void add_whenHomeTeamNameBlank_shouldThrowException() {
        String homeTeam = " ";
        String awayTeam = "Away";

        Exception exception = assertThrows(BlankTeamNameException.class, () -> {
            runningMatches.add(homeTeam, awayTeam, 0);
        });

        String expectedMessage = "Home team name is empty";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void add_whenAwayTeamNameBlank_shouldThrowException() {
        String homeTeam = "Home";
        String awayTeam = " ";

        Exception exception = assertThrows(BlankTeamNameException.class, () -> {
            runningMatches.add(homeTeam, awayTeam, 0);
        });

        String expectedMessage = "Away team name is empty";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void add_whenHomeTeamNameNull_shouldThrowException() {
        String homeTeam = null;
        String awayTeam = "Away";

        Exception exception = assertThrows(BlankTeamNameException.class, () -> {
            runningMatches.add(homeTeam, awayTeam, 0);
        });

        String expectedMessage = "Home team name is empty";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void add_whenAwayTeamNameNull_shouldThrowException() {
        String homeTeam = "Home";
        String awayTeam = null;

        Exception exception = assertThrows(BlankTeamNameException.class, () -> {
            runningMatches.add(homeTeam, awayTeam, 0);
        });

        String expectedMessage = "Away team name is empty";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void add_whenHomeAndAwayAreTheSame_shouldThrowException() {
        String team = "Team";

        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(team, team, 0);
        });

        String expectedMessage = "A team can't play a match against itself";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void add_whenHomeAlreadyPlayingMatchAsHome_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        // add a valid match
        String homeTeam = "Home";
        String awayTeam = "Away";

        runningMatches.add(homeTeam, awayTeam, 0);

        // when acting add another match with the same "home"
        String awayTeam2 = "Away2";

        //act and assert
        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(homeTeam, awayTeam2, 1);
        });

        String expectedMessage = "Team already playing";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void add_whenAwayAlreadyPlayingMatchAsAway_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        // add a valid match
        String homeTeam = "Home";
        String awayTeam = "Away";

        runningMatches.add(homeTeam, awayTeam, 0);

        // when acting add another match with the same "away"
        String homeTeam2 = "Home2";

        // act and assert
        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(homeTeam2, awayTeam, 1);
        });

        String expectedMessage = "Team already playing";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void add_whenHomeAlreadyPlayingMatchAsAway_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        // add a valid match
        String homeTeam = "Home";
        String awayTeam = "Away";

        runningMatches.add(homeTeam, awayTeam, 0);

        // when acting add another match with Away playing as "home"
        String awayTeam2 = "Away2";

        // act and assert
        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(awayTeam, awayTeam2, 1);
        });

        String expectedMessage = "Team already playing";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void add_whenAwayAlreadyPlayingMatchAsHome_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        // add a valid match
        String homeTeam = "Home";
        String awayTeam = "Away";

        runningMatches.add(homeTeam, awayTeam, 0);

        // when acting add another match with Home playing as "away"
        String home2 = "Home2";

        // act and assert
        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            runningMatches.add(home2, homeTeam, 1);
        });

        String expectedMessage = "Team already playing";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void update_whenMatchRunning_shouldUpdateScore()
            throws ClashingTeamsException, LowerScoreException, MatchNotFoundException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";
        int newHomeScore = 1;
        int newAwayScore = 2;

        runningMatches.add(homeTeam, awayTeam, 0);

        // act
        runningMatches.update(homeTeam, awayTeam, newHomeScore, newAwayScore);

        // assert
        List<Match> matches = runningMatches.getMatches();
        Match match = matches.getFirst();
        Assert.assertEquals(1, matches.size());
        assertMatchAsExpected(match, homeTeam, awayTeam, newHomeScore, newAwayScore);
    }

    @Test
    public void update_whenHomeScoreIsTheSameAsPrevious_shouldKeepScore()
            throws ClashingTeamsException, LowerScoreException, MatchNotFoundException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";
        int newHomeScore = 0;
        int newAwayScore = 1;

        runningMatches.add(homeTeam, awayTeam, 0);

        // act
        runningMatches.update(homeTeam, awayTeam, newHomeScore, newAwayScore);

        // assert
        List<Match> matches = runningMatches.getMatches();
        Match match = matches.getFirst();
        Assert.assertEquals(1, matches.size());
        assertMatchAsExpected(match, homeTeam, awayTeam, newHomeScore, newAwayScore);
    }

    @Test
    public void update_whenAwayScoreIsTheSameAsPrevious_shouldKeepScore()
            throws ClashingTeamsException, LowerScoreException, MatchNotFoundException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";
        int newHomeScore = 1;
        int newAwayScore = 0;

        runningMatches.add(homeTeam, awayTeam, 0);

        // act
        runningMatches.update(homeTeam, awayTeam, newHomeScore, newAwayScore);

        // assert
        List<Match> matches = runningMatches.getMatches();
        Match match = matches.getFirst();
        Assert.assertEquals(1, matches.size());
        assertMatchAsExpected(match, homeTeam, awayTeam, newHomeScore, newAwayScore);
    }

    @Test
    public void update_whenUpdatingWithLowerHomeScore_shouldThrowException()
            throws ClashingTeamsException, LowerScoreException, MatchNotFoundException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";
        int firstUpdateHomeScore = 1;
        int secondUpdateHomeScore = 0;
        int newAwayScore = 2;

        runningMatches.add(homeTeam, awayTeam, 0);
        runningMatches.update(homeTeam, awayTeam, firstUpdateHomeScore, newAwayScore);

        // act and assert
        Exception exception = assertThrows(LowerScoreException.class, () -> {
            runningMatches.update(homeTeam, awayTeam, secondUpdateHomeScore, newAwayScore);
        });

        String expectedMessage = "Score can't be lowered";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void update_whenUpdatingWithLowerAwayScore_shouldThrowException()
            throws ClashingTeamsException, LowerScoreException, MatchNotFoundException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";
        int newHomeScore = 2;
        int firstUpdateAwayScore = 1;
        int secondUpdateAwayScore = 0;

        runningMatches.add(homeTeam, awayTeam, 0);
        runningMatches.update(homeTeam, awayTeam, newHomeScore, firstUpdateAwayScore);

        // act and assert
        Exception exception = assertThrows(LowerScoreException.class, () -> {
            runningMatches.update(homeTeam, awayTeam, newHomeScore, secondUpdateAwayScore);
        });

        String expectedMessage = "Score can't be lowered";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void update_whenUpdatingNotRunningMatch_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";
        int newHomeTeamScore = 1;
        int newAwayTeamScore = 2;

        // add matches of Home and Away with other teams to check if they won't be updated
        String homeTeam2 = "Home2";
        String awayTeam2 = "Away2";
        runningMatches.add(homeTeam, awayTeam2, 0);
        runningMatches.add(homeTeam2, awayTeam, 1);

        // act and assert
        Exception exception = assertThrows(MatchNotFoundException.class, () -> {
            runningMatches.update(homeTeam, awayTeam, newHomeTeamScore, newAwayTeamScore);
        });

        String expectedMessage = "Match not found";
        Assert.assertEquals(expectedMessage, exception.getMessage());

        List<Match> matches = runningMatches.getMatches();
        assertMatchAsExpected(matches.get(0), homeTeam, awayTeam2, 0, 0);
        assertMatchAsExpected(matches.get(1), homeTeam2, awayTeam, 0, 0);
    }

    @Test
    public void finish_whenNoMatchesRunning_shouldThrowException() {
        String homeTeam = "Home";
        String awayTeam = "Away";

        Exception exception = assertThrows(MatchNotFoundException.class, () -> {
            runningMatches.finish(homeTeam, awayTeam);
        });

        String expectedMessage = "Match not found";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void finish_whenMatchIsRunning_shouldRemoveAndReturnIt()
            throws MatchNotFoundException, ClashingTeamsException, BlankTeamNameException {
        String homeTeam = "Home";
        String awayTeam = "Away";

        runningMatches.add(homeTeam, awayTeam, 0);

        Match match = runningMatches.finish(homeTeam, awayTeam);
        Assert.assertTrue(runningMatches.getMatches().isEmpty());
        assertMatchAsExpected(match, homeTeam, awayTeam, 0, 0);
    }

    @Test
    public void finish_whenThereAreRunningMatches_ButGivenMatchIsNotRunning_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";

        // add matches of Home and Away with other teams to check if they won't be removed
        String homeTeam2 = "Home2";
        String awayTeam2 = "Away2";
        runningMatches.add(homeTeam, awayTeam2, 0);
        runningMatches.add(homeTeam2, awayTeam, 1);

        // act and assert
        Exception exception = assertThrows(MatchNotFoundException.class, () -> {
            runningMatches.finish(homeTeam, awayTeam);
        });

        String expectedMessage = "Match not found";
        Assert.assertEquals(expectedMessage, exception.getMessage());

        List<Match> matches = runningMatches.getMatches();
        Assert.assertEquals(2, matches.size());
        assertMatchAsExpected(matches.get(0), homeTeam, awayTeam2, 0, 0);
        assertMatchAsExpected(matches.get(1), homeTeam2, awayTeam, 0, 0);
    }

    private static void assertMatchAsExpected(Match match, String homeTeam, String awayTeam,
                                              int homeTeamScore, int awayTeamScore) {
        Assert.assertEquals(homeTeam, match.getHomeTeam());
        Assert.assertEquals(awayTeam, match.getAwayTeam());
        Assert.assertEquals(homeTeamScore, match.getHomeTeamScore());
        Assert.assertEquals(awayTeamScore, match.getAwayTeamScore());
    }

}
