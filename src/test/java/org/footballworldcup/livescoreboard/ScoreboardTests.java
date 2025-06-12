package org.footballworldcup.livescoreboard;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThrows;

public class ScoreboardTests {

    @Test
    public void whenNoMatches_summaryShouldBeEmpty() {
        Scoreboard scoreboard = new Scoreboard();
        Assert.assertTrue(scoreboard.getSummary().isEmpty());
    }

    @Test
    public void afterStartingMatch_summaryShouldHaveIt() throws ClashingTeamsException, BlankTeamNameException {
        Scoreboard scoreboard = new Scoreboard();
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start(homeTeam, awayTeam);

        List<Match> summary = scoreboard.getSummary();
        Assert.assertEquals(1, summary.size());
        Assert.assertEquals(homeTeam, summary.getFirst().getHomeTeam());
        Assert.assertEquals(awayTeam, summary.getFirst().getAwayTeam());
    }

    @Test
    public void givenFinishedMatch_summaryShouldHaveIt()
            throws ClashingTeamsException, BlankTeamNameException, MatchNotFoundException {
        Scoreboard scoreboard = new Scoreboard();
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start(homeTeam, awayTeam);
        scoreboard.finish(homeTeam, awayTeam);

        List<Match> summary = scoreboard.getSummary();
        Assert.assertEquals(1, summary.size());
        Assert.assertEquals(homeTeam, summary.getFirst().getHomeTeam());
        Assert.assertEquals(awayTeam, summary.getFirst().getAwayTeam());
    }

    @Test
    public void givenStartedMatch_afterUpdate_summaryShouldHaveUpdatedScore()
            throws LowerScoreException, MatchNotFoundException, ClashingTeamsException, BlankTeamNameException {
        Scoreboard scoreboard = new Scoreboard();
        String homeTeam = "Home";
        String awayTeam = "Away";
        int homeTeamScore = 1;
        int awayTeamScore = 2;

        scoreboard.start(homeTeam, awayTeam);
        scoreboard.update(homeTeam, awayTeam, homeTeamScore, awayTeamScore);

        Match match = scoreboard.getSummary().getFirst();
        Assert.assertEquals(homeTeamScore, match.getHomeTeamScore());
        Assert.assertEquals(awayTeamScore, match.getAwayTeamScore());
    }

    @Test
    public void givenFinishedMatch_whenUpdating_shouldThrowError()
            throws ClashingTeamsException, BlankTeamNameException, MatchNotFoundException {
        Scoreboard scoreboard = new Scoreboard();
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

        // check if summary has both matches
        List<Match> matches = scoreboard.getSummary();
        Assert.assertEquals(2, matches.size());

        // no update, so all scores should be 0
        Assert.assertEquals(0, matches.getFirst().getHomeTeamScore());
        Assert.assertEquals(0, matches.getFirst().getAwayTeamScore());
        Assert.assertEquals(0, matches.getLast().getHomeTeamScore());
        Assert.assertEquals(0, matches.getLast().getAwayTeamScore());
    }

    @Test
    public void givenMatchesWithDifferentScores_summaryShouldHaveThemOrdered()
            throws ClashingTeamsException, BlankTeamNameException, LowerScoreException, MatchNotFoundException {
        Scoreboard scoreboard = new Scoreboard();

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
        List<Match> matches = scoreboard.getSummary();
        Assert.assertEquals(homeTeam2, matches.getFirst().getHomeTeam());
        Assert.assertEquals(awayTeam2, matches.getFirst().getAwayTeam());

        Assert.assertEquals(homeTeam4, matches.get(1).getHomeTeam());
        Assert.assertEquals(awayTeam4, matches.get(1).getAwayTeam());

        Assert.assertEquals(homeTeam3, matches.get(2).getHomeTeam());
        Assert.assertEquals(awayTeam3, matches.get(2).getAwayTeam());

        Assert.assertEquals(homeTeam, matches.getLast().getHomeTeam());
        Assert.assertEquals(awayTeam, matches.getLast().getAwayTeam());
    }


}
