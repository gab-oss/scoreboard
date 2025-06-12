package org.footballworldcup.livescoreboard;

public record SummarizedMatch(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) {
    @Override
    public String toString() {
        return "";
    }
}
