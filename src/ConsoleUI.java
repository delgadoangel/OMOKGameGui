import java.io.*;
import java.util.Scanner;

public class ConsoleUI {
    private Game omok = new Game();
    private InputStream in;
    private PrintStream out;
    private Scanner scan;

    public ConsoleUI() {
    }
    public ConsoleUI(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
        scan = new Scanner(in);
    }

    public Game getOmok() {
        return this.omok;
    }

    public InputStream getIn() {
        return this.in;
    }

    public OutputStream getOut() {
        return this.out;
    }

    public void setIn(InputStream in) {
        this.in = in;
        scan = new Scanner(in);
    }
    public void welcomeMessage() {
        out.print("Welcome to the OMOK App!\n");
    }

    public void setBoardSize() { // Prompts user for the size
//        out.print("What size board would you like to play on? (enter the number from sizes below)\n");
//        out.print("- 15 - 20 - 25 -\n\n");
//        out.print("$ ");

//        int optIn;
//        do {
//            optIn = scan.nextInt();
//            if (optIn != 15 && optIn != 20 && optIn != 25) { out.print("INVALID INPUT! Enter 15, 20, or 25 please\n\n$ "); }
//        } while (optIn != 15 && optIn != 20 && optIn != 25);
    }
    public void setGameMode() {
        out.print("What game mode would you like to play? (enter 1 or 2 respectively)\n");
        out.print("- HUMAN v HUMAN - or - HUMAN v COMPUTER -\n\n");
        out.print("$ ");

        int optIn;
        do {
            optIn = scan.nextInt();
            if (optIn < 1 || optIn > 2) { out.print("INVALID INPUT! Enter 1 or 2 please\n\n$ "); }
        } while (optIn != 1 && optIn != 2);

        out.print("What will the name of player 1 be?\n\n");
        out.print("$ ");
        String p1Name = scan.next();

        out.printf("Does %s want hints? (Enter y for hints)\n\n", p1Name);
        out.print("$ ");

        String wantsHint = scan.next();
        boolean p1Hints = false;
        if (wantsHint.equals("y")) {
            p1Hints = true;
        }

        if (optIn == 1) {
            out.print("What will the name of player 2 be?\n\n");
            out.print("$ ");
            String p2Name = scan.next();

            out.printf("Does %s want hints? (Enter y for hints)\n\n", p1Name);
            out.print("$ ");

            wantsHint = scan.next();
            boolean p2Hints = false;
            if (wantsHint.equals("y")) {
                p2Hints = true;
            }

            omok.setupHumanGame(p1Name, p1Hints, p2Name, p2Hints);

            return;
        }

        omok.setupComputerGame(p1Name, p1Hints);
    }

    public void printBoard() {  // printing of board
        int size = omok.getBoard().getSize();
        int[][] board = omok.getBoard().getArray();

        out.print("- Here is the OMOK board -\n");

        out.print("   ");
        for (int i = 0; i < size; i += 1) {
            out.printf(" %-2d", i);
        }
        out.print("\n");
        for (int y = 0; y < size; y += 1) {
            out.printf(" %2d", y);
            for (int x = 0; x < size; x += 1) {
                if (board[x][y] == 1) {
                    out.print(" X "); // Place p1 tiles
                }
                else if (board[x][y] == 2) {
                    out.print(" O "); // Place p2 tiles
                }

                else {
                    if (y == 0 || y == size - 1) {
                        out.print(" - ");
                    }
                    else if (x == 0 || x == size - 1) {
                        out.print(" | ");
                    }
                    else {
                        out.print(" + ");
                    }
                }

            }
            out.print("\n");
        }
    }

    public void promptMove() throws IOException {
        Player currP = omok.currentPlayer();
        String name = currP.getName();

        Tile move;

        out.printf("It is %s's turn\n", name);
        if (currP.getClass() == CPU.class) {  // Computer playing a move
            move = omok.getCheck().getMove(currP, omok.getBoard());
            omok.playTurn(move, currP);
            return;
        }

        out.print("Choose an unselected tile from the board by choosing a coordinate x (numbers above board) and y (numbers to the left of board)\n");
        out.print("Enter numbers separated by a space\n\n");

        Tile hint = omok.getCheck().getMove(currP, omok.getBoard());
        if (hint != null) {
            out.printf("Hint: play %s\n", hint);
        }

        boolean valid;
        int x, y;

        do {
            out.print("$ ");
            x = scan.nextInt();
            y = scan.nextInt();

            move = new Tile(x, y);

            valid = omok.getCheck().isValidMove(move, omok.getBoard());
            if (!valid) {
                out.print("INVALID INPUT! Enter a different coordinate please\n\n");
            }

        } while(!valid);

        omok.playTurn(move, currP);
    }

    public boolean end() {
        if (omok.gameEnd) {

            printBoard();
            out.print("- GAME HAS FINISHED! -\n");
            if (omok.hasWonGame()) {
                winnerMessage(omok.winnerName);
            }
            else {
                tieMessage();
            }

            return true;
        }
        return false;
    }

    public void winnerMessage(String winnerName) {
        out.printf("- %s has won OMOK!\n", winnerName);
    }
    public void tieMessage() {
        out.print("There is no winner game tied.");
    }
}
