package org.footballworldcup.livescoreboard;

public class BlankTeamNameException extends Exception { // todo make it illegal argument exc
    public BlankTeamNameException(String errorMessage) {
        super(errorMessage);
    }
}
