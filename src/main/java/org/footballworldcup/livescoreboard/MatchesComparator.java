package org.footballworldcup.livescoreboard;

import java.util.Comparator;

class MatchesComparator implements Comparator<Match> {

    @Override
    public int compare(Match match1, Match match2) {
        int totalScore1 = match1.getTotalScore();
        int totalScore2 = match2.getTotalScore();

        if (totalScore1 == totalScore2) return Integer.compare(match2.getOrderNo(), match1.getOrderNo());
        return Integer.compare(match2.getTotalScore(), match1.getTotalScore());
    }

}
