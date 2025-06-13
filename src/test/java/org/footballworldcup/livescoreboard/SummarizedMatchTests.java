package org.footballworldcup.livescoreboard;

import org.junit.Assert;
import org.junit.Test;

public class SummarizedMatchTests {

    @Test
    public void toString_givenMatchData_shouldReturnFormattedString() {
        SummarizedMatch match = new SummarizedMatch("Mexico", "Canada", 0, 5);
        Assert.assertEquals("Mexico 0 - Canada 5", match.toString());
    }

}
