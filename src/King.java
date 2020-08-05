import java.util.Vector;

public class King extends Piece {
	public boolean hasMoved;

	public King(int number, char letter, char colour, int score) {
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

		if (Math.abs(newNumber - number) > 1 && Math.abs(newLetter - letter) > 1) {
			return false;
		}

		if (Math.abs(newNumber - number) == 1 && newLetter == letter) {
			if (matrix[8 - newNumber][newLetter - 'a'] != null) {
				return matrix[8 - newNumber][newLetter - 'a'].colour != colour;
			}

			return true;
		}

		if (Math.abs(newLetter - letter) == 1 && newNumber == number) {
			if (matrix[8 - newNumber][newLetter - 'a'] != null) {
				return matrix[8 - newNumber][newLetter - 'a'].colour != colour;
			}

			return true;
		}

		if (Math.abs(newLetter - letter) == 1 && Math.abs(newNumber - number) == 1) {
			if (matrix[8 - newNumber][newLetter - 'a'] != null) {
				return matrix[8 - newNumber][newLetter - 'a'].colour != colour;
			}

			return true;
		}

		if (Math.abs(newLetter - letter) == 2 && newNumber == number && !hasMoved) {
			if (newLetter > letter) {
				Piece piece = matrix[8 - newNumber]['h' - 'a'];

				if (piece instanceof Rook) {
					Rook rook = (Rook) piece;
					if (rook.hasMoved) {
						return false;
					} else if (matrix[8 - newNumber]['g' - 'a'] == null
							&& matrix[8 - newNumber]['f' - 'a'] == null) {
						return true;
					}
				}
			} else {
				Piece piece = matrix[8 - newNumber][0];

				if (piece instanceof Rook) {
					Rook rook = (Rook) piece;
					if (rook.hasMoved) {
						return false;
					} else if (matrix[8 - newNumber]['b' - 'a'] == null
							&& matrix[8 - newNumber]['c' - 'a'] == null
							&& matrix[8 - newNumber]['d' - 'a'] == null) {
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public Vector<String> validMoves() {
		Vector<String> result = new Vector<>();

		if (isValidMove(number, (char) (letter + 1))) {
			result.add("" + letter + number + (char) (letter + 1) + number);
		}

		if (isValidMove(number, (char) (letter - 1))) {
			result.add("" + letter + number + (char) (letter - 1) + number);
		}

		if (isValidMove(number + 1, letter)) {
			result.add("" + letter + number + letter + (number + 1));
		}

		if (isValidMove(number - 1, letter)) {
			result.add("" + letter + number + letter + (number - 1));
		}

		if (isValidMove(number + 1, (char) (letter + 1))) {
			result.add("" + letter + number + (char) (letter + 1) + (number + 1));
		}

		if (isValidMove(number - 1, (char) (letter + 1))) {
			result.add("" + letter + number + (char) (letter + 1) + (number - 1));
		}

		if (isValidMove(number + 1, (char) (letter - 1))) {
			result.add("" + letter + number + (char) (letter - 1) + (number + 1));
		}

		if (isValidMove(number - 1, (char) (letter - 1))) {
			result.add("" + letter + number + (char) (letter - 1) + (number - 1));
		}

		if (isValidMove(number, (char) (letter + 2))) {
			result.add("" + letter + number + (char) (letter + 2) + number);
		}

		if (isValidMove(number, (char) (letter - 2))) {
			result.add("" + letter + number + (char) (letter - 2) + number);
		}

		return result;
	}
}
