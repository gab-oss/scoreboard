package org.footballworldcup.livescoreboard.exceptions;

public class MatchNotFoundException extends IllegalArgumentException {
    public MatchNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
