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
    public void afterStartingMatch_summaryShouldHaveIt() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.start("Home", "Away");
        List<Match> summary = scoreboard.getSummary();
        Assert.assertEquals(1, summary.size());
    }

    @Test
    public void givenFinishedMatch_summaryShouldHaveIt() {
        Scoreboard scoreboard = new Scoreboard();
        String homeTeam = "Home";
        String awayTeam = "Away";

        scoreboard.start("Home", "Away");
        scoreboard.finish(homeTeam, awayTeam);

        List<Match> summary = scoreboard.getSummary();
        Assert.assertEquals(1, summary.size());
    }

}
