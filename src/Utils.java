import java.util.Vector;

public class Utils {
	public static final int MAX_DIMENSION = 8;

	public static int eval(char colour, Vector<Piece> whitePieces, Vector<Piece> blackPieces) {
		int score = 0;

		for (Piece piece : whitePieces) {
			score += piece.score;
		}

		for (Piece piece : blackPieces) {
			score -= piece.score;
		}

		return colour == 'w' ? score : -score;
	}

	public static Vector<String> getAllMoves(char colour, Vector<Piece> whitePieces,
											 Vector<Piece> blackPieces) {
		Vector<String> allMoves = new Vector<>();

		if (colour == 'w') {
			for (Piece piece : whitePieces) {
				allMoves.addAll(piece.validMoves());
			}
		} else if (colour == 'b') {
			for (Piece piece : blackPieces) {
				allMoves.addAll(piece.validMoves());
			}
		}

		return allMoves;
	}

	private static void applyMove(char colour, Piece[][] copy, String move,
									   Vector<Piece> whitePieces,
									   Vector<Piece> blackPieces) {
		Piece piece = copy[8 - (move.charAt(1) - '0')][move.charAt(0) - 'a'];

		copy[8 - (move.charAt(1) - '0')][move.charAt(0) - 'a'] = null;

		Piece toRemove = copy[8 - (move.charAt(3) - '0')][move.charAt(2) - 'a'];

		if (toRemove != null) {
			if (colour == 'w') {
				blackPieces.remove(toRemove);
			} else if (colour == 'b') {
				whitePieces.remove(toRemove);
			}
		}

		copy[8 - (move.charAt(3) - '0')][move.charAt(2) - 'a'] = piece;
	}

	public static void cloneMatrix(Piece[][] copy, Piece[][] matrix) {
		for (int i = 0; i < MAX_DIMENSION; i++) {
			for (int j = 0; j < MAX_DIMENSION; j++) {
				copy[i][j] = matrix[i][j];
			}
		}
	}

	public static Pair<Integer, String> negamax(int depth, Piece[][] matrix,
												Vector<Piece> whitePieces,
												Vector<Piece> blackPieces, char colour,
												String cMove) {
		if (depth == 0) {
			int score = eval(colour, whitePieces, blackPieces);
			return new Pair<>(score, cMove);
		}

		Vector<String> allMoves = getAllMoves(colour, whitePieces, blackPieces);
		int bestScore = Integer.MIN_VALUE;

		if (allMoves.size() == 0) {
			return new Pair<>(bestScore, null);
		}

		String bestMove = allMoves.get(0);

		for (String move : allMoves) {
			Piece[][] copy = new Piece[MAX_DIMENSION][MAX_DIMENSION];

			cloneMatrix(copy, matrix);

			Vector<Piece> whitePiecesCopy = new Vector<>(whitePieces);
			Vector<Piece> blackPiecesCopy = new Vector<>(blackPieces);

			applyMove(colour, matrix, move, whitePiecesCopy, blackPiecesCopy);

			Pair<Integer, String> currentResult = negamax(depth - 1, matrix, whitePiecesCopy,
					blackPiecesCopy, colour == 'w' ? 'b' : 'w', move);

			int score = -currentResult.first;

			if (score > bestScore && currentResult.second != null) {
				bestScore = score;
				bestMove = move;
			}

			cloneMatrix(matrix, copy);
		}

		return new Pair<>(bestScore, bestMove);
	}
}
