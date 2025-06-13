package org.footballworldcup.livescoreboard;

import org.footballworldcup.livescoreboard.exceptions.BlankTeamNameException;
import org.footballworldcup.livescoreboard.exceptions.ClashingTeamsException;
import org.footballworldcup.livescoreboard.exceptions.LowerScoreException;
import org.footballworldcup.livescoreboard.exceptions.MatchNotFoundException;

import java.util.List;

public interface Scoreboard {

    /**
     * Returns a summary of all matches, including both running and finished ones.
     * Matches are ordered according to:
     * total score (descending),
     * order of addition (descending, if total score is equal).
     *
     * @return a list of summarized match results
     */
    List<SummarizedMatch> getSummary();

    /**
     * Starts a new match between two distinct teams, with score 0-0.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     * @throws ClashingTeamsException if either team is already playing another match
     * @throws BlankTeamNameException if either team name is empty, all-whitespace or null
     */
    void start(String homeTeam, String awayTeam)
            throws ClashingTeamsException, BlankTeamNameException;

    /**
     * Updates the score of an ongoing match.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     * @param homeTeamScore the new score for the home team
     * @param awayTeamScore the new score for the away team
     * @throws MatchNotFoundException if there is no such running match
     * @throws LowerScoreException if the new score is lower than the current score for either team
     */
    void update(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore)
            throws LowerScoreException, MatchNotFoundException;

    /**
     * Finishes an ongoing match, making it impossible to update.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     * @throws MatchNotFoundException if there is no such running match
     */
    void finish(String homeTeam, String awayTeam) throws MatchNotFoundException;

}
