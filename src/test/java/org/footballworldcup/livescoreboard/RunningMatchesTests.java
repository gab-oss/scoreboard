package org.footballworldcup.livescoreboard;

import org.junit.Assert;
import org.junit.Test;

public class RunningMatchesTests {

    @Test
    public void whenNoMatches_getShouldReturnEmptyList() {
        RunningMatches runningMatches = new RunningMatches();
        Assert.assertTrue(runningMatches.getMatches().isEmpty());
    }
}
