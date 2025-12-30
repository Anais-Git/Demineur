package exceptions;

public class GameWonException extends InvalidMoveException {
    public GameWonException() {
        super("Félicitations ! Vous avez gagné la partie.");
    }
}
