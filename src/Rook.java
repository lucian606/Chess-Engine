import java.util.Vector;

public class Rook extends Piece {
	public boolean hasMoved;

	public Rook(int number, char letter, char colour, int score) {
		super(number, letter, colour, score);
		hasMoved = false;
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

		if (newNumber != number && newLetter == letter) {
			// SUS
			for (int i = number + 1; i <= newNumber; i++) {
				if (matrix[8 - i][newLetter - 'a'] != null) {
					return i == newNumber && matrix[8 - i][newLetter - 'a'].colour != colour;
				}
			}

			// JOS
			for (int i = number - 1; i >= newNumber; i--) {
				if (matrix[8 - i][newLetter - 'a'] != null) {
					return i == newNumber && matrix[8 - i][newLetter - 'a'].colour != colour;
				}
			}
		} else if (newNumber == number && newLetter != letter) {
			// DREAPTA
			for (int i = letter + 1; i <= newLetter; i++) {
				if (matrix[8 - newNumber][i - 'a'] != null) {
					return i == newLetter && matrix[8 - newNumber][i - 'a'].colour != colour;
				}
			}

			// STANGA
			for (int i = letter - 1; i >= newLetter; i--) {
				if (matrix[8 - newNumber][i - 'a'] != null) {
					return i == newLetter && matrix[8 - newNumber][i - 'a'].colour != colour;
				}
			}
		}

		return true;
	}

	@Override
	public Vector<String> validMoves() {
		Vector<String> result = new Vector<>();

		for (int i = 1; i <= 8; i++) {
			if (isValidMove(i, letter)) {
				result.add("" + letter + number + letter + i);
			}
		}

		for (char i = 'a'; i <= 'h'; i++) {
			if (isValidMove(number, i)) {
				result.add("" + letter + number + i + number);
			}
		}

		return result;
	}
}
