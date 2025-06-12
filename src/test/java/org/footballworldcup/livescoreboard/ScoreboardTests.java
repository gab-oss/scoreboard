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

}
