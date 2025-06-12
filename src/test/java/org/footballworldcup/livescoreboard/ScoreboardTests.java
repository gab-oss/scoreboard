package org.footballworldcup.livescoreboard;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ScoreboardTests {

    @Test
    public void whenNoMatches_summaryShouldBeEmpty() {
        Scoreboard scoreboard = new Scoreboard();
        Assert.assertTrue(scoreboard.getSummary().isEmpty());
    }

    @Test
    public void afterStartingMatch_summaryShouldHaveIt() throws ClashingTeamsException, BlankTeamNameException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.start("Home", "Away");
        List<Match> summary = scoreboard.getSummary();
        Assert.assertEquals(1, summary.size());
    }

    @Test
    public void givenFinishedMatch_summaryShouldHaveIt() throws ClashingTeamsException, BlankTeamNameException {
        Scoreboard scoreboard = new Scoreboard();
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start("Home", "Away"); // todo refactor
        scoreboard.finish(homeTeam, awayTeam);

        List<Match> summary = scoreboard.getSummary();
        Assert.assertEquals(1, summary.size());
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

}
