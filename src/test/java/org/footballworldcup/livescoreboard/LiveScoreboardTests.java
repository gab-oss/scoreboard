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

public class LiveScoreboardTests {

    LiveScoreboard scoreboard;

    @Before
    public void initialize() {
        this.scoreboard = new LiveScoreboard();
    }

    @Test
    public void getMatches_whenNoRunningMatches_shouldBeEmpty() {
        Assert.assertTrue(scoreboard.getMatches().isEmpty());
    }

    @Test
    public void start_whenNoClashingMatches_shouldAddItWithZeroScore()
            throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";

        // act
        scoreboard.start(homeTeam, awayTeam);

        // assert
        List<Match> matches = scoreboard.getMatches();
        Match match = matches.getFirst();
        Assert.assertEquals(1, matches.size());
        assertMatchAsExpected(match, homeTeam, awayTeam, 0, 0);
    }


    @Test
    public void start_whenHomeTeamNameBlank_shouldThrowException() {
        String homeTeam = " ";
        String awayTeam = "Away";

        Exception exception = assertThrows(BlankTeamNameException.class, () -> {
            scoreboard.start(homeTeam, awayTeam);
        });

        String expectedMessage = "Home team name is empty";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void start_whenAwayTeamNameBlank_shouldThrowException() {
        String homeTeam = "Home";
        String awayTeam = " ";

        Exception exception = assertThrows(BlankTeamNameException.class, () -> {
            scoreboard.start(homeTeam, awayTeam);
        });

        String expectedMessage = "Away team name is empty";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void start_whenHomeTeamNameNull_shouldThrowException() {
        String homeTeam = null;
        String awayTeam = "Away";

        Exception exception = assertThrows(BlankTeamNameException.class, () -> {
            scoreboard.start(homeTeam, awayTeam);
        });

        String expectedMessage = "Home team name is empty";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void start_whenAwayTeamNameNull_shouldThrowException() {
        String homeTeam = "Home";
        String awayTeam = null;

        Exception exception = assertThrows(BlankTeamNameException.class, () -> {
            scoreboard.start(homeTeam, awayTeam);
        });

        String expectedMessage = "Away team name is empty";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void start_whenHomeAndAwayAreTheSame_shouldThrowException() {
        String team = "Team";

        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            scoreboard.start(team, team);
        });

        String expectedMessage = "A team can't play a match against itself";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void start_whenHomeAlreadyPlayingMatchAsHome_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        // add a valid match
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start(homeTeam, awayTeam);

        // when acting add another match with the same "home"
        String awayTeam2 = "Away2";

        //act and assert
        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            scoreboard.start(homeTeam, awayTeam2);
        });

        String expectedMessage = "Team already playing";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void start_whenAwayAlreadyPlayingMatchAsAway_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        // add a valid match
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start(homeTeam, awayTeam);

        // when acting add another match with the same "away"
        String homeTeam2 = "Home2";

        // act and assert
        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            scoreboard.start(homeTeam2, awayTeam);
        });

        String expectedMessage = "Team already playing";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void start_whenHomeAlreadyPlayingMatchAsAway_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        // add a valid match
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start(homeTeam, awayTeam);

        // when acting add another match with Away playing as "home"
        String awayTeam2 = "Away2";

        // act and assert
        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            scoreboard.start(awayTeam, awayTeam2);
        });

        String expectedMessage = "Team already playing";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void start_whenAwayAlreadyPlayingMatchAsHome_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        // add a valid match
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start(homeTeam, awayTeam);

        // when acting add another match with Home playing as "away"
        String home2 = "Home2";

        // act and assert
        Exception exception = assertThrows(ClashingTeamsException.class, () -> {
            scoreboard.start(home2, homeTeam);
        });

        String expectedMessage = "Team already playing";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void start_whenMatchWithSameTeamsFinished_shouldAddMatch() throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start(homeTeam, awayTeam);
        scoreboard.finish(homeTeam, awayTeam);

        // act
        scoreboard.start(homeTeam, awayTeam);

        // assert
        List<Match> matches = scoreboard.getMatches();
        Assert.assertEquals(1, matches.size());
        assertMatchAsExpected(matches.getFirst(), homeTeam, awayTeam, 0, 0);
    }

    @Test
    public void update_whenMatchRunning_shouldUpdateScore()
            throws ClashingTeamsException, LowerScoreException, MatchNotFoundException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";
        int newHomeScore = 1;
        int newAwayScore = 2;

        scoreboard.start(homeTeam, awayTeam);

        // act
        scoreboard.update(homeTeam, awayTeam, newHomeScore, newAwayScore);

        // assert
        List<Match> matches = scoreboard.getMatches();
        Match match = matches.getFirst();
        Assert.assertEquals(1, matches.size());
        assertMatchAsExpected(match, homeTeam, awayTeam, newHomeScore, newAwayScore);
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
        scoreboard.start(homeTeam, awayTeam2);
        scoreboard.start(homeTeam2, awayTeam);

        // act and assert
        Exception exception = assertThrows(MatchNotFoundException.class, () -> {
            scoreboard.update(homeTeam, awayTeam, newHomeTeamScore, newAwayTeamScore);
        });

        String expectedMessage = "Match not found";
        Assert.assertEquals(expectedMessage, exception.getMessage());

        List<Match> matches = scoreboard.getMatches();
        assertMatchAsExpected(matches.get(0), homeTeam2, awayTeam, 0, 0);
        assertMatchAsExpected(matches.get(1), homeTeam, awayTeam2, 0, 0);
    }

    @Test
    public void update_whenMatchFinished_shouldThrowException()
            throws ClashingTeamsException, BlankTeamNameException, MatchNotFoundException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";
        int homeTeamScore = 1;
        int awayTeamScore = 2;

        scoreboard.start(homeTeam, awayTeam);

        // start another match to make sure it doesn't get affected
        String homeTeam2 = "Home2";
        String awayTeam2 = "Away2";
        scoreboard.start(homeTeam2, awayTeam2);

        // finish the first match
        scoreboard.finish(homeTeam, awayTeam);

        // act - try to update the finished match
        assertThrows(MatchNotFoundException.class, () -> {
            scoreboard.update(homeTeam, awayTeam, homeTeamScore, awayTeamScore);
        });

        // assert
        // check if getMatches returns the unfinished match
        List<Match> matches = scoreboard.getMatches();
        Assert.assertEquals(1, matches.size());

        // no update, so all scores should be 0
        // ordered by starting order
        assertMatchAsExpected(matches.getFirst(), homeTeam2, awayTeam2, 0, 0);
    }

    @Test
    public void update_whenMatchWithSameTeamsFinishedAndNewStarted_shouldUpdateRunningMatch()
            throws ClashingTeamsException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";
        int newHomeTeamScore = 1;

        scoreboard.start(homeTeam, awayTeam);
        scoreboard.finish(homeTeam, awayTeam);
        scoreboard.start(homeTeam, awayTeam);

        // act
        scoreboard.update(homeTeam, awayTeam, newHomeTeamScore, 0);

        // assert
        List<Match> matches = scoreboard.getMatches();
        Assert.assertEquals(1, matches.size());
        assertMatchAsExpected(matches.getFirst(), homeTeam, awayTeam, newHomeTeamScore, 0);
    }


    @Test
    public void update_whenHomeScoreIsTheSameAsPrevious_shouldKeepScore()
            throws ClashingTeamsException, LowerScoreException, MatchNotFoundException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";
        int newHomeScore = 0;
        int newAwayScore = 1;

        scoreboard.start(homeTeam, awayTeam);

        // act
        scoreboard.update(homeTeam, awayTeam, newHomeScore, newAwayScore);

        // assert
        List<Match> matches = scoreboard.getMatches();
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

        scoreboard.start(homeTeam, awayTeam);

        // act
        scoreboard.update(homeTeam, awayTeam, newHomeScore, newAwayScore);

        // assert
        List<Match> matches = scoreboard.getMatches();
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

        scoreboard.start(homeTeam, awayTeam);
        scoreboard.update(homeTeam, awayTeam, firstUpdateHomeScore, newAwayScore);

        // act and assert
        Exception exception = assertThrows(LowerScoreException.class, () -> {
            scoreboard.update(homeTeam, awayTeam, secondUpdateHomeScore, newAwayScore);
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

        scoreboard.start(homeTeam, awayTeam);
        scoreboard.update(homeTeam, awayTeam, newHomeScore, firstUpdateAwayScore);

        // act and assert
        Exception exception = assertThrows(LowerScoreException.class, () -> {
            scoreboard.update(homeTeam, awayTeam, newHomeScore, secondUpdateAwayScore);
        });

        String expectedMessage = "Score can't be lowered";
        Assert.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void finish_whenMatchRunning_shouldRemoveItFromMatches()
            throws ClashingTeamsException, BlankTeamNameException, MatchNotFoundException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start(homeTeam, awayTeam);

        // act
        scoreboard.finish(homeTeam, awayTeam);

        // assert
        List<Match> matches = scoreboard.getMatches();
        Assert.assertTrue(matches.isEmpty());
    }

    @Test
    public void finish_whenNoMatchesRunning_shouldThrowException() {
        String homeTeam = "Home";
        String awayTeam = "Away";

        Exception exception = assertThrows(MatchNotFoundException.class, () -> {
            scoreboard.finish(homeTeam, awayTeam);
        });

        String expectedMessage = "Match not found";
        Assert.assertEquals(expectedMessage, exception.getMessage());
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
        scoreboard.start(homeTeam, awayTeam2);
        scoreboard.start(homeTeam2, awayTeam);

        // act and assert
        Exception exception = assertThrows(MatchNotFoundException.class, () -> {
            scoreboard.finish(homeTeam, awayTeam);
        });

        String expectedMessage = "Match not found";
        Assert.assertEquals(expectedMessage, exception.getMessage());

        List<Match> matches = scoreboard.getMatches();
        Assert.assertEquals(2, matches.size());
        assertMatchAsExpected(matches.get(0), homeTeam2, awayTeam, 0, 0);
        assertMatchAsExpected(matches.get(1), homeTeam, awayTeam2, 0, 0);
    }

    @Test
    public void getMatches_givenMatchesWithDifferentTotalScores_shouldHaveThemOrderedByScore()
            throws ClashingTeamsException, BlankTeamNameException, LowerScoreException, MatchNotFoundException {
        // arrange:
        // match1: total score 0, match2: total score 3
        // match3: total score 1, match4: total score 2
        // expected order: match2, match4, match3, match1

        // match 1
        String homeTeam = "Home";
        String awayTeam = "Away";
        scoreboard.start(homeTeam, awayTeam);

        // match 2
        String homeTeam2 = "Home2";
        String awayTeam2 = "Away2";
        int newHomeTeam2Score = 1;
        int newAwayTeam2Score = 2;
        scoreboard.start(homeTeam2, awayTeam2);
        scoreboard.update(homeTeam2, awayTeam2, newHomeTeam2Score, newAwayTeam2Score);

        // match 3
        String homeTeam3 = "Home3";
        String awayTeam3 = "Away3";
        int newHomeTeam3Score = 0;
        int newAwayTeam3Score = 1;
        scoreboard.start(homeTeam3, awayTeam3);
        scoreboard.update(homeTeam3, awayTeam3, newHomeTeam3Score, newAwayTeam3Score);

        // match 4
        String homeTeam4 = "Home4";
        String awayTeam4 = "Away4";
        int newHome4TeamScore = 2;
        int newAway4TeamScore = 0;
        scoreboard.start(homeTeam4, awayTeam4);
        scoreboard.update(homeTeam4, awayTeam4, newHome4TeamScore, newAway4TeamScore);

        // act
        List<Match> matches = scoreboard.getMatches();

        // assert
        assertTeamsAsExpected(matches.get(0), homeTeam2, awayTeam2);
        assertTeamsAsExpected(matches.get(1), homeTeam4, awayTeam4);
        assertTeamsAsExpected(matches.get(2), homeTeam3, awayTeam3);
        assertTeamsAsExpected(matches.get(3), homeTeam, awayTeam);
    }

    @Test
    public void getMatches_givenMatchesWithSameScore_shouldHaveThemOrderedByStartingOrder()
            throws ClashingTeamsException, BlankTeamNameException, LowerScoreException, MatchNotFoundException {
        // arrange:

        // match 1
        String homeTeam = "Home";
        String awayTeam = "Away";
        scoreboard.start(homeTeam, awayTeam);

        // match 2
        String homeTeam2 = "Home2";
        String awayTeam2 = "Away2";
        scoreboard.start(homeTeam2, awayTeam2);

        // match 3
        String homeTeam3 = "Home3";
        String awayTeam3 = "Away3";
        scoreboard.start(homeTeam3, awayTeam3);

        // match 4
        String homeTeam4 = "Home4";
        String awayTeam4 = "Away4";
        scoreboard.start(homeTeam4, awayTeam4);

        // act
        List<Match> matches = scoreboard.getMatches();

        // assert
        assertTeamsAsExpected(matches.get(0), homeTeam4, awayTeam4);
        assertTeamsAsExpected(matches.get(1), homeTeam3, awayTeam3);
        assertTeamsAsExpected(matches.get(2), homeTeam2, awayTeam2);
        assertTeamsAsExpected(matches.get(3), homeTeam, awayTeam);
    }

    private static void assertTeamsAsExpected(Match match, String homeTeam, String awayTeam) {
        Assert.assertEquals(homeTeam, match.getHomeTeam());
        Assert.assertEquals(awayTeam, match.getAwayTeam());
    }

    private static void assertMatchAsExpected(Match match, String homeTeam, String awayTeam,
                                              int homeTeamScore, int awayTeamScore) {
        Assert.assertEquals(homeTeam, match.getHomeTeam());
        Assert.assertEquals(awayTeam, match.getAwayTeam());
        Assert.assertEquals(homeTeamScore, match.getHomeTeamScore());
        Assert.assertEquals(awayTeamScore, match.getAwayTeamScore());
    }

}

