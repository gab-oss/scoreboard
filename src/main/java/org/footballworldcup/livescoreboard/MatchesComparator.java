package org.footballworldcup.livescoreboard;

import java.util.Comparator;

public class MatchesComparator implements Comparator<Match> {

    @Override
    public int compare(Match match1, Match match2) {
        return Integer.compare(match2.getTotalScore(), match1.getTotalScore());
    }

}
