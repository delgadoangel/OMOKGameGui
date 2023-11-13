import java.util.ArrayList;
import java.util.List;

public class Game {

    public interface TurnListener {
        void turnChanged(boolean turn);
    }

    public interface GameEndListener {
        void gameEnd(boolean gameEnd);
    }

    private List<TurnListener> turnListeners = new ArrayList<>();
    private List<GameEndListener> gameEndListeners = new ArrayList<>();
    private Board board;
    private Intersection check;
    boolean gameEnd;
    String winnerName;
    boolean p1turn;

    public Game() {
        p1turn = true;
        gameEnd = false;
        board = new Board();
    }

    public Intersection getCheck() {
        return check;
    }

    public Board getBoard () {
        return board;
    }

    public void restartBoard() { board.clear(); }

    public void setupHumanGame(String name1, boolean p1Hints, String name2, boolean p2Hints) {
        check = new Intersection(name1, p1Hints, name2, p2Hints);
    }

    public void setupHumanGame() {
        check = new Intersection("Black", false, "White", false);
    }

    public void setupComputerGame(String name1, boolean hints) {
        check = new Intersection(name1, hints);
    }

    public void setupComputerGame() {
        check = new Intersection("Black", false);
    }

    public void showBoard() {
        board.printBoard();
    }

    public Player currentPlayer() {
        if (p1turn) {
            return check.p1();
        }
        return check.p2();
    }

    public void playTurn(Tile currMove, Player currP) {
        if (gameEnd)
            return;

        if (currMove == null) {
            currMove = check.getMove(currP, board);
        }

        check.updateBoard(currP, currMove, board);
        if (check.isWinningSequence(currMove, p1turn)) {
            winnerName = currP.getName();
            gameEnd = true;
        }
        if (check.isUnplayable(board.getSize())){
            gameEnd = true;
        }

        p1turn = !p1turn;
        turnListeners.forEach(listener -> listener.turnChanged(p1turn));
        gameEndListeners.forEach(listener -> listener.gameEnd(gameEnd));
    }
    public void addTurnListener(TurnListener listener) {
        turnListeners.add(listener);
    }

    public void addGameEndListener(GameEndListener listener) {
        gameEndListeners.add(listener);
    }

    public boolean hasWonGame() {
        return winnerName != null;
    }

    public void gameReset() {
        p1turn = true;
        gameEnd = false;
        board.clear();
    }
}
