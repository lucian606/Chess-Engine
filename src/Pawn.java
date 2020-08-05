import java.util.Vector;

public class Pawn extends Piece {
    private int initialPosition;

    public Pawn(int number, char letter, char colour, int score) {
        super(number, letter, colour, score);

        switch (colour) {
            case 'b':
                initialPosition = 7;
                break;
            case 'w':
                initialPosition = 2;
                break;
        }
    }

    // Verifica daca miscarea este valida
    @Override
    public boolean isValidMove(int newNumber, char newLetter) {
        if (newNumber < 1 || newNumber > 8 || newLetter < 'a' || newLetter > 'h') {
            return false;
        }

        Piece[][] matrix = ChessBoard.getChessBoard().matrix;

        if (newLetter == letter && matrix[8 - newNumber][newLetter - 'a'] == null) {
            if (colour == 'b') {
                if (number == initialPosition
                        && number - newNumber <= 2
                        && number - newNumber > 0
                        && matrix[8 - newNumber - (number - newNumber - 1)][newLetter - 'a'] == null) {
                    return true;
                } else if (number != initialPosition
                                && number - newNumber == 1
                                && matrix[8-newNumber][newLetter - 'a'] == null) {
                    return true;
                }
            } else if (colour == 'w') {
                if (number == initialPosition
                        && newNumber - number <= 2
                        && number - newNumber < 0
                        && matrix[8 - newNumber + (newNumber - number - 1)][newLetter - 'a'] == null) {
                    return true;
                } else if (number != initialPosition
                                && newNumber - number == 1
                                && matrix[8-newNumber][newLetter-'a'] == null) {
                    return true;
                }
            }
        } else if(Math.abs(newLetter - letter) == 1 && Math.abs(newNumber - number) == 1) {
            if (colour == 'b' && number - newNumber == 1) {
                if (matrix[8 - newNumber][newLetter - 'a'] != null
                        && matrix[8 - newNumber][newLetter - 'a'].colour != colour) {
                    return true;
                }
            } else if (colour == 'w' && newNumber - number == 1) {
                if (matrix[8 - newNumber][newLetter - 'a'] != null
                        && matrix[8 - newNumber][newLetter - 'a'].colour != colour) {
                    return true;
                }
            }
        }

        return false;
    }

    // Retine intr-un vector miscarile posibile
    @Override
    public Vector<String> validMoves() {
        Vector<String> result = new Vector<>();

        if (colour == 'w') {
            if (isValidMove(number + 2, letter)) {
                result.add("" + letter + number + letter + (number + 2));
            }

            if (isValidMove(number + 1, letter)) {
                result.add("" + letter + number + letter + (number + 1));
            }

            if (isValidMove(number + 1, (char) (letter + 1))) {
                result.add("" + letter + number + (char) (letter + 1) + (number + 1));
            }

            if (isValidMove(number + 1, (char) (letter - 1))) {
                result.add("" + letter + number + (char) (letter - 1) + (number + 1));
            }
        }


        if (colour == 'b') {
            if (isValidMove(number - 2, letter)) {
                result.add("" + letter + number + letter + (number - 2));
            }

            if (isValidMove(number - 1, letter)) {
                result.add("" + letter + number + letter + (number - 1));
            }

            if (isValidMove(number - 1, (char) (letter + 1))) {
                result.add("" + letter + number + (char) (letter + 1) + (number - 1));
            }

            if (isValidMove(number - 1, (char) (letter - 1))) {
                result.add("" + letter + number + (char) (letter - 1) + (number - 1));
            }
        }

        return result;
    }
}
