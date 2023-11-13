/** A class representing a square board.
 * Allows for the implementation of games that
 * incorporate selecting tiles.
 *
 * @author Angel Delgado
 * @version 1.0 10/6/2023
 * @since 10/6/2023
 *
 */

public class Board {
    /** Listen to changes on a board. */
    public interface BoardChangeListener {

        /** Called when a stone is placed at a place by a player. */
        void stonePlaced(int x, int y, Player player);

    }

    /** Determines the size n of an n * n board */
    private final int size;

    /** Stores values representing tiles in board*/
    private int [][] board;


    /**
     *
     */
    public Board(int size) {
        this.size = size;
        initBoard();
    }

    public Board() {
        this.size = 15;
        initBoard();
    }

    /**
     *
     */
    public void initBoard() {
        board = new int[size][size];
    }

    /**
     *
     */
    public int getSize() {
        return this.size;
    }

    public int[][] getArray() {
        return this.board;
    }

    public boolean isEmpty(int x, int y) {
        return board[y][x] == 0; // checking if there is no name in place
    }

    public void clear() {
        for (int i = 0; i < board.length; i += 1) {
            for (int j = 0; j < board[0].length; j += 1) {
                board[i][j] = 0; // clearing board by making spaces null
            }
        }
    }
    /**
     *
     */
    public void printBoard() {  // printing of board
        System.out.print("   ");
        for (int i = 0; i < size; i += 1) {
            System.out.printf(" %-2d", i);
        }
        System.out.print("\n");
        for (int y = 0; y < size; y += 1) {
            System.out.printf(" %2d", y);
            for (int x = 0; x < size; x += 1) {
                if (board[x][y] == 1) {
                    System.out.print(" X "); // Place p1 tiles
                }
                else if (board[x][y] == 2) {
                    System.out.print(" O "); // Place p2 tiles
                }

                else {
                    if (y == 0 || y == size - 1) {
                        System.out.print(" - ");
                    }
                    else if (x == 0 || x == size - 1) {
                        System.out.print(" | ");
                    }
                    else {
                        System.out.print(" + ");
                    }
                }

            }
            System.out.print("\n");
        }
    }

    /**
     *
     */
    public void updateBoard(Tile tile, int id) {
        board[tile.y()][tile.x()] = id;
    }

}
