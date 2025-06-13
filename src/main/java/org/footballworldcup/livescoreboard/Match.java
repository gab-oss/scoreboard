package org.footballworldcup.livescoreboard;

class Match {

    private final String homeTeam;
    private final String awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;
    private final int orderNo;

    Match(String homeTeam, String awayTeam, int orderNo) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
        this.orderNo = orderNo;
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

    int getOrderNo() {
        return orderNo;
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

    int getTotalScore() {
        return homeTeamScore + awayTeamScore;
    }

}
