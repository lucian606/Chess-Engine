import java.util.Vector;

public class Knight extends Piece {
    public Knight(int number, char letter, char colour, int score) {
        super(number, letter, colour, score);
    }

    @Override
    public boolean isValidMove(int newNumber, char newLetter) {
        if (newNumber < 1 || newNumber > 8 || newLetter < 'a' || newLetter > 'h') {
            return false;
        }

        Piece[][] matrix = ChessBoard.getChessBoard().matrix;

        if (matrix[8 - newNumber][newLetter - 'a'] == null) {
            if (Math.abs(newNumber - number) == 2 && Math.abs(newLetter - letter) == 1) {
                return true;
            } else if (Math.abs(newNumber - number) == 1 && Math.abs(newLetter - letter) == 2) {
                return true;
            }
        } else if (Math.abs(newNumber - number) == 2 && Math.abs(newLetter - letter) == 1
                && matrix[8 - newNumber][newLetter - 'a'].colour == colour) {
            return false;
        } else if (Math.abs(newNumber - number) == 2 && Math.abs(newLetter - letter) == 1
                && matrix[8 - newNumber][newLetter - 'a'].colour != colour) {
            return true;
        } else if (Math.abs(newNumber - number) == 1 && Math.abs(newLetter - letter) == 2
                && matrix[8 - newNumber][newLetter - 'a'].colour == colour) {
            return false;
        } else if (Math.abs(newNumber - number) == 1 && Math.abs(newLetter - letter) == 2
                && matrix[8 - newNumber][newLetter - 'a'].colour != colour) {
            return true;
        }

        return false;
    }

    @Override
    public Vector<String> validMoves() {
        Vector<String> result = new Vector<>();

        if (isValidMove(number + 2, (char) (letter + 1))) {
            result.add("" + letter + number + (char) (letter + 1) + (number + 2));
        }

        if (isValidMove(number + 2, (char) (letter - 1))) {
            result.add("" + letter + number + (char) (letter - 1) + (number + 2));
        }

        if (isValidMove(number - 2, (char) (letter + 1))) {
            result.add("" + letter + number + (char) (letter + 1) + (number - 2));
        }

        if (isValidMove(number - 2, (char) (letter - 1))) {
            result.add("" + letter + number + (char) (letter - 1) + (number - 2));
        }

        if (isValidMove(number + 1, (char) (letter + 2))) {
            result.add("" + letter + number + (char) (letter + 2) + (number + 1));
        }

        if (isValidMove(number + 1, (char) (letter - 2))) {
            result.add("" + letter + number + (char) (letter - 2) + (number + 1));
        }

        if (isValidMove(number - 1, (char) (letter + 2))) {
            result.add("" + letter + number + (char) (letter + 2) + (number - 1));
        }

        if (isValidMove(number - 1, (char) (letter - 2))) {
            result.add("" + letter + number + (char) (letter - 2) + (number - 1));
        }

        return result;
    }
}
