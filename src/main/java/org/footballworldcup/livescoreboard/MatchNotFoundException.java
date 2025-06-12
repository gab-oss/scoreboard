package org.footballworldcup.livescoreboard;

public class MatchNotFoundException extends Exception {
    public MatchNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
