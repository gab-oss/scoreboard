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

}
