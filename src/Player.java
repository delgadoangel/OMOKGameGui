import java.util.HashSet;
import java.util.Random;

public abstract class Player implements Playable{

    private String name;
    private int id;
    private boolean usingStrat;


    public Player(int id, String name, boolean usingStrat) {
        this.id = id;
        this.name = name;
        this.usingStrat = usingStrat;
    }

    public abstract Tile selectMove(HashSet<Tile> myTiles, HashSet<Tile> opponentTiles, int size);
    public String getName() { return this.name; }
    public int getId() { return this.id; }
    public boolean getUsingStrat() { return this.usingStrat; }
    public Tile stratMove(HashSet<Tile> myTiles, HashSet<Tile> opponentTiles, int size) {
        Tile move;

        HashSet<Tile> onPlay = new HashSet<>();
        onPlay.addAll(myTiles);
        onPlay.addAll(opponentTiles);


        // Prioritizing winning over defending
        move = makesHorizontalSequence(myTiles, size);
        if (move != null) {
            if (!onPlay.contains(move)) {
                return move;
            }
        }
        move = makesVerticalSequence(myTiles, size);
        if (move != null) {
            if (!onPlay.contains(move)) {
                return move;
            }
        }
        move = makesDiagonalSequence(myTiles, size);
        if (move != null) {
            if (!onPlay.contains(move)) {
                return move;
            }
        }

        // Defending moves
        move = makesHorizontalSequence(opponentTiles, size);
        if (move != null) {
            if (!onPlay.contains(move)) {
                return move;
            }
        }
        move = makesVerticalSequence(opponentTiles, size);
        if (move != null) {
            if (!onPlay.contains(move)) {
                return move;
            }
        }
        move = makesDiagonalSequence(opponentTiles, size);
        if (move != null) {
            if (!onPlay.contains(move)) {
                return move;
            }
        }

        int xoffset = 1;
        int yoffset = 1;

        for (Tile tile : myTiles) {
            if (tile.x() + xoffset >= size || tile.y() + yoffset < 0) {
               xoffset = 0;
            }
            if (tile.y() + yoffset >= size || tile.y() + yoffset < 0) {
                yoffset = 0;
            }
            move = new Tile(tile.x() + xoffset, tile.y() + yoffset);
            if (!onPlay.contains(move)) {
                return move;
            }
        }

        for (Tile tile : opponentTiles) {
            if (tile.x() + xoffset >= size || tile.y() + yoffset < 0) {
                xoffset = 0;
            }
            if (tile.y() + yoffset >= size || tile.y() + yoffset < 0) {
                yoffset = 0;
            }
            move = new Tile(tile.x() + xoffset, tile.y() + yoffset);
            if (!onPlay.contains(move)) {
                return move;
            }
        }

        Random rand = new Random();
        do {
            move = new Tile(rand.nextInt(size), rand.nextInt(size));
        } while (onPlay.contains(move));

        return move;
    }

    private Tile makesHorizontalSequence(HashSet<Tile> tiles, int size) {
        Tile suggest = null;

        for (int y = 0; y < size; y += 1) {
            for (int x = 0; x < size; x += 1) {
                if (horizontalSequence(new Tile(x, y), tiles)) {
                    suggest = new Tile(x, y);
                }
            }
        }

        return suggest;
    }

    private Tile makesVerticalSequence(HashSet<Tile> tiles, int size) {
        Tile suggest = null;

        for (int y = 0; y < size; y += 1) {
            for (int x = 0; x < size; x += 1) {
                if (verticalSequence(new Tile(x, y), tiles)) {
                    suggest = new Tile(x, y);
                }
            }
        }

        return suggest;
    }

    private Tile makesDiagonalSequence(HashSet<Tile> tiles, int size) {
        Tile suggest = null;

        for (int y = 0; y < size; y += 1) {
            for (int x = 0; x < size; x += 1) {
                if (diagonalSequence(new Tile(x, y), tiles)) {
                    suggest = new Tile(x, y);
                }
            }
        }

        return suggest;
    }

    private boolean verticalSequence(Tile newTile, HashSet<Tile> tiles) { // checks a vertical sequence
        int offset = 1;
        int sequence = 1;
        while (tiles.contains(new Tile(newTile.x(), newTile.y() + offset))) {
            sequence += 1;
            offset += 1;
        }
        offset = 1;
        while (tiles.contains(new Tile(newTile.x(), newTile.y() - offset))) {
            sequence += 1;
            offset += 1;
        }
        return sequence >= 5;
    }

    private boolean horizontalSequence(Tile newTile, HashSet<Tile> tiles) { /// checks for a horizontal sequence
        int offset = 1;
        int sequence = 1;
        while (tiles.contains(new Tile(newTile.x() + offset, newTile.y()))) {
            sequence += 1;
            offset += 1;
        }
        offset = 1;
        while (tiles.contains(new Tile(newTile.x() - offset, newTile.y()))) {
            sequence += 1;
            offset += 1;
        }
        return sequence >= 5;
    }
    private boolean diagonalSequence(Tile newTile, HashSet<Tile> tiles) { // checks for a diagonal sequence
        int offset = 1;
        int sequence = 1;
        while (tiles.contains(new Tile(newTile.x() + offset, newTile.y() + offset))) {
            sequence += 1;
            offset += 1;
        }
        offset = 1;
        while (tiles.contains(new Tile(newTile.x() - offset, newTile.y() - offset))) {
            sequence += 1;
            offset += 1;
        }
        if (sequence >= 5) {
            return true;
        }

        offset = 1;
        sequence = 1;
        while (tiles.contains(new Tile(newTile.x() + offset, newTile.y() - offset))) {
            sequence += 1;
            offset += 1;
        }
        offset = 1;
        while (tiles.contains(new Tile(newTile.x() - offset, newTile.y() + offset))) {
            sequence += 1;
            offset += 1;
        }
        return sequence >= 5;
    }


}
