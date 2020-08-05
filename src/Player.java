import java.util.Vector;

public class Player {
    public char colour;
    public Vector<Piece> pieces;

    public Player(char colour) {
        pieces = new Vector<>(16);

        /*for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn())
        }*/

        this.colour = colour;
    }
}
