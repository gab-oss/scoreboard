package org.footballworldcup.livescoreboard.exceptions;

public class LowerScoreException extends IllegalArgumentException {
    public LowerScoreException(String errorMessage) {
        super(errorMessage);
    }
}
