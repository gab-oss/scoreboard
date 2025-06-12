package org.footballworldcup.livescoreboard.exceptions;

public class ClashingTeamsException extends IllegalArgumentException {
    public ClashingTeamsException(String errorMessage) {
        super(errorMessage);
    }
}
