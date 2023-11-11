import java.util.HashSet;

public interface Playable {
    Tile selectMove(HashSet<Tile> myTiles, HashSet<Tile> opponentTiles, int size);
    String getName();
    int getId();
}
