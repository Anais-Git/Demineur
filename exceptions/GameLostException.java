package exceptions;

public class GameLostException extends InvalidMoveException {
    public GameLostException() {
        super("BOOM ! Vous avez déclenché une bombe.");
    }
}
