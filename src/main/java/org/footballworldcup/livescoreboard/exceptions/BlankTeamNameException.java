package org.footballworldcup.livescoreboard.exceptions;

public class BlankTeamNameException extends IllegalArgumentException {
    public BlankTeamNameException(String errorMessage) {
        super(errorMessage);
    }
}
