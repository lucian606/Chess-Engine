import java.util.Vector;

public abstract class Piece {
    public char letter;
    public int number;
    public char colour;
    public int score;

    public Piece(int number, char letter, char colour, int score) {
        this.number = number;
        this.letter = letter;
        this.colour = colour;
        this.score = score;
    }

    // Metoda ce muta piesa pe pozitia data
    public void move(int newNumber, char newLetter) {
        ChessBoard.getChessBoard().matrix[8 - number][letter - 'a'] = null;

        if (this instanceof Pawn) {
            if (newNumber == 8 && colour == 'w') {
                Queen queen = new Queen(newNumber, newLetter, colour, 9);
                ChessBoard.getChessBoard().matrix[8 - newNumber][newLetter - 'a'] = queen;
                return;
            } else if (newNumber == 1 && colour == 'b') {
                Queen queen = new Queen(newNumber, newLetter, colour, 9);
                ChessBoard.getChessBoard().matrix[8 - newNumber][newLetter - 'a'] = queen;
                return;
            }
        }

        letter = newLetter;
        number = newNumber;

        ChessBoard.getChessBoard().matrix[8 - number][letter - 'a'] = this;
    }

    // Verifica daca miscarea este valida
    public abstract boolean isValidMove(int newNumber, char newLetter);

    // Retine intr-un vector miscarile posibile
    public abstract Vector<String> validMoves();

    @Override
    public String toString() {
        return this.getClass().getName().charAt(0)+ "  " + colour;
    }
}
