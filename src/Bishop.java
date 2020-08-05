import java.util.Vector;

public class Bishop extends Piece {
    public Bishop(int number, char letter, char colour, int score) {
        super(number, letter, colour, score);
    }

    @Override
    public boolean isValidMove(int newNumber, char newLetter) {
        if (newNumber < 1 || newNumber > 8 || newLetter < 'a' || newLetter > 'h') {
            return false;
        }

        Piece[][] matrix = ChessBoard.getChessBoard().matrix;

        if (newNumber == number && newLetter == letter) {
            return false;
        }

        if (Math.abs(newNumber - number) != Math.abs(newLetter - letter)) {
            return false;
        }

        // DREAPTA SUS
        for (int i = number + 1, j = letter + 1; i <= newNumber && j <= newLetter; i++, j++) {
            if (matrix[8 - i][j - 'a'] != null) {
                return i == newNumber && j == newLetter && matrix[8 - i][j - 'a'].colour != colour;
            }
        }

        // DREAPTA JOS
        for (int i = number - 1, j = letter + 1; i >= newNumber && j <= newLetter; i--, j++) {
            if (matrix[8 - i][j - 'a'] != null) {
                return i == newNumber && j == newLetter && matrix[8 - i][j - 'a'].colour != colour;
            }
        }

        // STANGA JOS
        for (int i = number - 1, j = letter - 1; i >= newNumber && j >= newLetter; i--, j--) {
            if (matrix[8 - i][j - 'a'] != null) {
                return i == newNumber && j == newLetter && matrix[8 - i][j - 'a'].colour != colour;
            }
        }

        // STANGA SUS
        for (int i = number + 1, j = letter - 1; i <= newNumber && j >= newLetter; i++, j--) {
            if (matrix[8 - i][j - 'a'] != null) {
                return i == newNumber && j == newLetter && matrix[8 - i][j - 'a'].colour != colour;
            }
        }

        return true;
    }

    @Override
    public Vector<String> validMoves() {
        Vector<String> result = new Vector<>();

        for (int i = 1; i <= 8; i++) {
            if (isValidMove(number + i, (char) (letter + i))) {
                result.add("" + letter + number + (char) (letter + i) + (number + i));
            }

            if (isValidMove(number - i, (char) (letter + i))) {
                result.add("" + letter + number + (char) (letter + i) + (number - i));
            }

            if (isValidMove(number + i, (char) (letter - i))) {
                result.add("" + letter + number + (char) (letter - i) + (number + i));
            }

            if (isValidMove(number - i, (char) (letter - i))) {
                result.add("" + letter + number + (char) (letter - i) + (number - i));
            }
        }

        return result;
    }
}
