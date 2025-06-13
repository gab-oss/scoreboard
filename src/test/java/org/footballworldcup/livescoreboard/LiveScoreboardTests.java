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
    public void start_whenNoClashingMatches_shouldAddMatch() throws ClashingTeamsException, BlankTeamNameException {
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start(homeTeam, awayTeam);

        List<Match> matches = scoreboard.getMatches();
        Assert.assertEquals(1, matches.size());
        assertMatchAsExpected(matches.getFirst(), homeTeam, awayTeam, 0, 0);
    }

    @Test
    public void finish_whenMatchWasRunning_shouldKeepItInMatches()
            throws ClashingTeamsException, BlankTeamNameException, MatchNotFoundException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start(homeTeam, awayTeam);

        // act
        scoreboard.finish(homeTeam, awayTeam);

        // assert
        List<Match> matches = scoreboard.getMatches();
        Assert.assertEquals(1, matches.size());
        assertMatchAsExpected(matches.getFirst(), homeTeam, awayTeam, 0, 0);
    }

    @Test
    public void update_givenRunningMatch_shouldSetNewScore()
            throws LowerScoreException, MatchNotFoundException, ClashingTeamsException, BlankTeamNameException {
        // arrange
        String homeTeam = "Home";
        String awayTeam = "Away";
        int homeTeamScore = 1;
        int awayTeamScore = 2;

        scoreboard.start(homeTeam, awayTeam);

        // act
        scoreboard.update(homeTeam, awayTeam, homeTeamScore, awayTeamScore);

        // assert
        Match match = scoreboard.getMatches().getFirst();
        assertMatchAsExpected(match, homeTeam, awayTeam, homeTeamScore, awayTeamScore);
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
        // check if getMatches returns both matches
        List<Match> matches = scoreboard.getMatches();
        Assert.assertEquals(2, matches.size());

        // no update, so all scores should be 0
        // ordered by starting order
        assertMatchAsExpected(matches.getFirst(), homeTeam2, awayTeam2, 0, 0);
        assertMatchAsExpected(matches.getLast(), homeTeam, awayTeam, 0, 0);
    }

    @Test
    public void getMatches_givenMatchesWithDifferentTotalScores_shouldHaveThemOrderedByScore()
            throws ClashingTeamsException, BlankTeamNameException, LowerScoreException, MatchNotFoundException {
        // arrange:
        // running: match1: total score 0, match2: total score 3
        // finished: match3: total score 1, match4: total score 2
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
        scoreboard.finish(homeTeam3, awayTeam3);

        // match 4
        String homeTeam4 = "Home4";
        String awayTeam4 = "Away4";
        int newHome4TeamScore = 2;
        int newAway4TeamScore = 0;
        scoreboard.start(homeTeam4, awayTeam4);
        scoreboard.update(homeTeam4, awayTeam4, newHome4TeamScore, newAway4TeamScore);
        scoreboard.finish(homeTeam4, awayTeam4);

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
        // running: match1: added 1st, match2: added 4th
        // finished: match3: added 2nd, match4: added 3rd
        // expected order: match2, match4, match3, match1

        // match 1
        String homeTeam = "Home";
        String awayTeam = "Away";
        scoreboard.start(homeTeam, awayTeam);

        // match 3
        String homeTeam3 = "Home3";
        String awayTeam3 = "Away3";
        scoreboard.start(homeTeam3, awayTeam3);
        scoreboard.finish(homeTeam3, awayTeam3);

        // match 4
        String homeTeam4 = "Home4";
        String awayTeam4 = "Away4";
        scoreboard.start(homeTeam4, awayTeam4);
        scoreboard.finish(homeTeam4, awayTeam4);

        // match 2
        String homeTeam2 = "Home2";
        String awayTeam2 = "Away2";
        scoreboard.start(homeTeam2, awayTeam2);

        // act
        List<Match> matches = scoreboard.getMatches();

        // assert
        assertTeamsAsExpected(matches.get(0), homeTeam2, awayTeam2);
        assertTeamsAsExpected(matches.get(1), homeTeam4, awayTeam4);
        assertTeamsAsExpected(matches.get(2), homeTeam3, awayTeam3);
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

