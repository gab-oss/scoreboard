package org.footballworldcup.livescoreboard;

public class Match {

    private String homeTeam;
    private String awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;

    Match () {}

    Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
    }

    String getHomeTeam() {
        return homeTeam;
    }

    String getAwayTeam() {
        return awayTeam;
    }

    int getHomeTeamScore() {
        return homeTeamScore;
    }

    int getAwayTeamScore() {
        return awayTeamScore;
    }

    void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    boolean isMatchOfTeams(String homeTeam, String awayTeam) {
        return this.homeTeam.equals(homeTeam) && this.awayTeam.equals(awayTeam);
    }

    boolean isNotLowerScore(int homeTeamScore, int awayTeamScore) {
        return this.homeTeamScore <= homeTeamScore && this.awayTeamScore <= awayTeamScore;
    }

    boolean isClashingByTeamName(String homeTeam, String awayTeam) {
        return this.homeTeam.equals(homeTeam)
                || this.homeTeam.equals(awayTeam)
                || this.awayTeam.equals(homeTeam)
                || this.awayTeam.equals(awayTeam);
    }

}
