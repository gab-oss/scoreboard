package org.footballworldcup.livescoreboard;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class ScoreboardTests {

    @Test
    public void getSummary_whenNoMatchesInSystem_shouldBeEmpty() {
        Scoreboard scoreboard = new LiveScoreboard();
        Assert.assertTrue(scoreboard.getSummary().isEmpty());
    }

    @Test
    public void getSummary_whenMatchesGiven_shouldBeOrderedByTotalScoreAndStartingOrder() {
        Scoreboard scoreboard = new LiveScoreboard();

        // arrange (running unless otherwise stated):
        // Mexico - Canada: 0 – 5 - finished
        // Spain - Brazil: 10 – 2 - finished
        // Germany - France: 2 – 2
        // Uruguay - Italy: 6 – 6
        // Argentina - Australia: 3 - 1

        String mexico = "Mexico";
        String canada = "Canada";
        int mexicoScore = 0;
        int canadaScore = 5;
        startUpdateAndFinish(scoreboard, mexico, canada, mexicoScore, canadaScore);

        String spain = "Spain";
        String brazil = "Brazil";
        int spainScore = 10;
        int brazilScore = 2;
        startUpdateAndFinish(scoreboard, spain, brazil, spainScore, brazilScore);

        String germany = "Germany";
        String france = "France";
        int germanyScore = 2;
        int franceScore = 2;
        startAndUpdate(scoreboard, germany, france, germanyScore, franceScore);

        String uruguay = "Uruguay";
        String italy = "Italy";
        int uruguayScore = 6;
        int italyScore = 6;
        startAndUpdate(scoreboard, uruguay, italy, uruguayScore, italyScore);

        String argentina = "Argentina";
        String australia = "Australia";
        int argentinaScore = 3;
        int australiaScore = 1;
        startAndUpdate(scoreboard, argentina, australia, argentinaScore, australiaScore);

        // expected result:
        // Uruguay 6 - Italy 6
        // Spain 10 - Brazil 2
        // Mexico 0 - Canada 5
        // Argentina 3 - Australia 1
        // Germany 2 - France 2

        // act
        List<SummarizedMatch> matches = scoreboard.getSummary();

        // assert
        assertMatchAsExpected(matches.get(0), uruguay, italy, uruguayScore, italyScore);
        assertMatchAsExpected(matches.get(1), spain, brazil, spainScore, brazilScore);
        assertMatchAsExpected(matches.get(2), mexico, canada, mexicoScore, canadaScore);
        assertMatchAsExpected(matches.get(3), argentina, australia, argentinaScore, australiaScore);
        assertMatchAsExpected(matches.get(4), germany, france, germanyScore, franceScore);
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
