package org.footballworldcup.livescoreboard;

import org.footballworldcup.livescoreboard.exceptions.BlankTeamNameException;
import org.footballworldcup.livescoreboard.exceptions.ClashingTeamsException;
import org.footballworldcup.livescoreboard.exceptions.LowerScoreException;
import org.footballworldcup.livescoreboard.exceptions.MatchNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThrows;

public class LiveScoreboardTests {

    @Test
    public void whenNoMatches_matchesShouldBeEmpty() {
        LiveScoreboard scoreboard = new LiveScoreboard();
        Assert.assertTrue(scoreboard.getMatches().isEmpty());
    }

    @Test
    public void afterStartingMatch_matchesShouldHaveIt() throws ClashingTeamsException, BlankTeamNameException {
        LiveScoreboard scoreboard = new LiveScoreboard();
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start(homeTeam, awayTeam);

        List<Match> matches = scoreboard.getMatches();
        Assert.assertEquals(1, matches.size());
        Assert.assertEquals(homeTeam, matches.getFirst().getHomeTeam());
        Assert.assertEquals(awayTeam, matches.getFirst().getAwayTeam());
    }

    @Test
    public void givenFinishedMatch_matchesShouldHaveIt()
            throws ClashingTeamsException, BlankTeamNameException, MatchNotFoundException {
        LiveScoreboard scoreboard = new LiveScoreboard();
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start(homeTeam, awayTeam);
        scoreboard.finish(homeTeam, awayTeam);

        List<Match> matches = scoreboard.getMatches();
        Assert.assertEquals(1, matches.size());
        Assert.assertEquals(homeTeam, matches.getFirst().getHomeTeam());
        Assert.assertEquals(awayTeam, matches.getFirst().getAwayTeam());
    }

    @Test
    public void givenStartedMatch_afterUpdate_matchesShouldHaveUpdatedScore()
            throws LowerScoreException, MatchNotFoundException, ClashingTeamsException, BlankTeamNameException {
        LiveScoreboard scoreboard = new LiveScoreboard();
        String homeTeam = "Home";
        String awayTeam = "Away";
        int homeTeamScore = 1;
        int awayTeamScore = 2;

        scoreboard.start(homeTeam, awayTeam);
        scoreboard.update(homeTeam, awayTeam, homeTeamScore, awayTeamScore);

        Match match = scoreboard.getMatches().getFirst();
        Assert.assertEquals(homeTeamScore, match.getHomeTeamScore());
        Assert.assertEquals(awayTeamScore, match.getAwayTeamScore());
    }

    @Test
    public void givenFinishedMatch_whenUpdating_shouldThrowError()
            throws ClashingTeamsException, BlankTeamNameException, MatchNotFoundException {
        LiveScoreboard scoreboard = new LiveScoreboard();
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

        // try to update the finished match
        assertThrows(MatchNotFoundException.class, () -> {
            scoreboard.update(homeTeam, awayTeam, homeTeamScore, awayTeamScore);
        });

        // check if matches have both
        List<Match> matches = scoreboard.getMatches();
        Assert.assertEquals(2, matches.size());

        // no update, so all scores should be 0
        Assert.assertEquals(0, matches.getFirst().getHomeTeamScore());
        Assert.assertEquals(0, matches.getFirst().getAwayTeamScore());
        Assert.assertEquals(0, matches.getLast().getHomeTeamScore());
        Assert.assertEquals(0, matches.getLast().getAwayTeamScore());
    }

    @Test
    public void givenMatchesWithDifferentScores_matchesShouldHaveThemOrdered()
            throws ClashingTeamsException, BlankTeamNameException, LowerScoreException, MatchNotFoundException {
        LiveScoreboard scoreboard = new LiveScoreboard();

        // set-up:
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

        // check order
        List<Match> matches = scoreboard.getMatches();
        Assert.assertEquals(homeTeam2, matches.getFirst().getHomeTeam());
        Assert.assertEquals(awayTeam2, matches.getFirst().getAwayTeam());

        Assert.assertEquals(homeTeam4, matches.get(1).getHomeTeam());
        Assert.assertEquals(awayTeam4, matches.get(1).getAwayTeam());

        Assert.assertEquals(homeTeam3, matches.get(2).getHomeTeam());
        Assert.assertEquals(awayTeam3, matches.get(2).getAwayTeam());

        Assert.assertEquals(homeTeam, matches.getLast().getHomeTeam());
        Assert.assertEquals(awayTeam, matches.getLast().getAwayTeam());
    }

    @Test
    public void givenMatchesWithSameScores_matchesShouldHaveThemOrdered()
            throws ClashingTeamsException, BlankTeamNameException, LowerScoreException, MatchNotFoundException {
        LiveScoreboard scoreboard = new LiveScoreboard();

        // set-up:
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

        // check order
        List<Match> matches = scoreboard.getMatches();
        Assert.assertEquals(homeTeam2, matches.getFirst().getHomeTeam());
        Assert.assertEquals(awayTeam2, matches.getFirst().getAwayTeam());

        Assert.assertEquals(homeTeam4, matches.get(1).getHomeTeam());
        Assert.assertEquals(awayTeam4, matches.get(1).getAwayTeam());

        Assert.assertEquals(homeTeam3, matches.get(2).getHomeTeam());
        Assert.assertEquals(awayTeam3, matches.get(2).getAwayTeam());

        Assert.assertEquals(homeTeam, matches.getLast().getHomeTeam());
        Assert.assertEquals(awayTeam, matches.getLast().getAwayTeam());
    }

    private static void assertMatchAsExpected(SummarizedMatch match, String homeTeam, String awayTeam,
                                              int homeTeamScore, int awayTeamScore) {
        Assert.assertEquals(homeTeam, match.homeTeam());
        Assert.assertEquals(awayTeam, match.awayTeam());
        Assert.assertEquals(homeTeamScore, match.homeTeamScore());
        Assert.assertEquals(awayTeamScore, match.awayTeamScore());
    }

    private static void startAndUpdate(Scoreboard scoreboard, String homeTeam, String awayTeam,
                                       int homeTeamScore, int awayTeamScore) {
        scoreboard.start(homeTeam, awayTeam);
        scoreboard.update(homeTeam, awayTeam, homeTeamScore, awayTeamScore);
    }

    private static void startUpdateAndFinish(Scoreboard scoreboard, String homeTeam, String awayTeam,
                                             int homeTeamScore, int awayTeamScore) {
        startAndUpdate(scoreboard, homeTeam, awayTeam, homeTeamScore, awayTeamScore);
        scoreboard.finish(homeTeam, awayTeam);
    }

}

