import java.util.Scanner;
import java.util.Vector;

public class MainClass {
	public static final int INFINITY = 123456789;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Piece[][] matrix = ChessBoard.getChessBoard().matrix;

        // Initializare vectori de piese
        Vector<Piece> whitePieces = new Vector<>(16);
        Vector<Piece> blackPieces = new Vector<>(16);

        // Adaugarea pionilor in vectori
        for (int i = 0; i < 8; i++) {
            blackPieces.add(matrix[1][i]);
            whitePieces.add(matrix[6][i]);
        }

        // Adaugare cai
        blackPieces.add(matrix[0][1]);
        blackPieces.add(matrix[0][6]);
        whitePieces.add(matrix[7][1]);
        whitePieces.add(matrix[7][6]);

        // Adaugare ture
        blackPieces.add(matrix[0][0]);
        blackPieces.add(matrix[0][7]);
        whitePieces.add(matrix[7][0]);
        whitePieces.add(matrix[7][7]);

        // Adaugare nebuni
		blackPieces.add(matrix[0][2]);
		blackPieces.add(matrix[0][5]);
		whitePieces.add(matrix[7][2]);
		whitePieces.add(matrix[7][5]);

		// Adaugare regine
		blackPieces.add(matrix[0][3]);
		whitePieces.add(matrix[7][3]);

		// Adaugare regi
		blackPieces.add(matrix[0][4]);
		whitePieces.add(matrix[7][4]);

        String command = "";
        String lastForcedMove= "";
        boolean isEngineBlack = true;
        boolean isForceMode = false;

        while (true) {
            // Daca force mode a fost activat nu se mai face inca o citire
            if (!isForceMode) {
                command = scanner.next();
            } else {
                command = lastForcedMove;
            }

            if (command.equals("protover")) {
                System.out.println("feature sigint=0 sigterm=0 done=1");
            } else if (command.equals("quit")) {
                break;
            } else if (command.equals("new")) {
                // Reinitializarea matricei si a vectorilor
                isEngineBlack = true;
                ChessBoard.getChessBoard().newBoard();
                matrix = ChessBoard.getChessBoard().matrix;
                whitePieces.removeAllElements();
                blackPieces.removeAllElements();

                for (int i = 0; i < 8; i++) {
                    blackPieces.add(matrix[1][i]);
                    whitePieces.add(matrix[6][i]);
                }

                // Adaugare cai
                blackPieces.add(matrix[0][1]);
                blackPieces.add(matrix[0][6]);
                whitePieces.add(matrix[7][1]);
                whitePieces.add(matrix[7][6]);

                // Adaugare ture
                blackPieces.add(matrix[0][0]);
                blackPieces.add(matrix[0][7]);
                whitePieces.add(matrix[7][0]);
                whitePieces.add(matrix[7][7]);

				// Adaugare nebuni
				blackPieces.add(matrix[0][2]);
				blackPieces.add(matrix[0][5]);
				whitePieces.add(matrix[7][2]);
				whitePieces.add(matrix[7][5]);

				// Adaugare regine
				blackPieces.add(matrix[0][3]);
				whitePieces.add(matrix[7][3]);

				// Adaugare regi
				blackPieces.add(matrix[0][4]);
				whitePieces.add(matrix[7][4]);
            } else if (command.equals("white") && isEngineBlack) {
                // Schimba engine-ul pe alb si executa o miscare
                isEngineBlack = false;

				String bestMove = Utils.getAllMoves('w', whitePieces, blackPieces).get(0);
				int bestScore = Integer.MIN_VALUE;

				for (String move : Utils.getAllMoves('w', whitePieces, blackPieces)) {
					Pair<Integer, String> currentResult = Utils.negamax(4,
							ChessBoard.getChessBoard().matrix,
							whitePieces, blackPieces, 'w', move);
					String cMove = currentResult.second;
					int score = -currentResult.first;

					if (score > bestScore && currentResult.second != null) {
						bestScore = score;
						bestMove = currentResult.second;
					}
				}

				Piece piece = matrix[8 - (bestMove.charAt(1) - '0')][bestMove.charAt(0) - 'a'];
				Piece toRemove = matrix[8 - (bestMove.charAt(3) - '0')][bestMove.charAt(2) - 'a'];

				if (toRemove != null) {
					blackPieces.remove(toRemove);
				}

				if (piece instanceof King) {
					King king = (King) piece;
					king.hasMoved = true;
					piece = king;
				}

				if (piece instanceof Rook) {
					Rook rook = (Rook) piece;
					rook.hasMoved = true;
					piece = rook;
				}

				if (piece instanceof King) {
					if (Math.abs(bestMove.charAt(2) - bestMove.charAt(0)) == 2) {
						if (bestMove.charAt(2) > bestMove.charAt(0)) {
							Piece piece1 = matrix[8 - (bestMove.charAt(1) - '0')][7];
							piece1.move(bestMove.charAt(1) - '0', 'f');
						} else {
							Piece piece1 = matrix[8 - (bestMove.charAt(1) - '0')][0];
							piece1.move(bestMove.charAt(1) - '0', 'd');
						}
					}
				}

				piece.move(bestMove.charAt(3) - '0', bestMove.charAt(2));

				if (piece instanceof Pawn && bestMove.charAt(3) == '8') {
					whitePieces.remove(piece);
					whitePieces.add(matrix[0][bestMove.charAt(2) - 'a']);
				}

				System.out.println("move " + bestMove);
            } else if (command.equals("black") && !isEngineBlack) {
                // Schimba engine-ul pe negru si executa o miscare
                isEngineBlack = true;

				String bestMove = Utils.getAllMoves('b', whitePieces, blackPieces).get(0);
				int bestScore = Integer.MIN_VALUE;

				for (String move : Utils.getAllMoves('b', whitePieces, blackPieces)) {
					Pair<Integer, String> currentResult = Utils.negamax(4,
							ChessBoard.getChessBoard().matrix,
							whitePieces, blackPieces, 'b', move);
					String cMove = currentResult.second;
					int score = -currentResult.first;

					if (score > bestScore && currentResult.second != null) {
						bestScore = score;
						bestMove = currentResult.second;
					}
				}

				Piece piece = matrix[8 - (bestMove.charAt(1) - '0')][bestMove.charAt(0) - 'a'];
				Piece toRemove = matrix[8 - (bestMove.charAt(3) - '0')][bestMove.charAt(2) - 'a'];

				if (toRemove != null) {
					whitePieces.remove(toRemove);
				}

				if (piece instanceof King) {
					King king = (King) piece;
					king.hasMoved = true;
					piece = king;
				}

				if (piece instanceof Rook) {
					Rook rook = (Rook) piece;
					rook.hasMoved = true;
					piece = rook;
				}

				if (piece instanceof King) {
					if (Math.abs(bestMove.charAt(2) - bestMove.charAt(0)) == 2) {
						if (bestMove.charAt(2) > bestMove.charAt(0)) {
							Piece piece1 = matrix[8 - (bestMove.charAt(1) - '0')][7];
							piece1.move(bestMove.charAt(1) - '0', 'f');
						} else {
							Piece piece1 = matrix[8 - (bestMove.charAt(1) - '0')][0];
							piece1.move(bestMove.charAt(1) - '0', 'd');
						}
					}
				}

				piece.move(bestMove.charAt(3) - '0', bestMove.charAt(2));

				if (piece instanceof Pawn && bestMove.charAt(3) == '1') {
					blackPieces.remove(piece);
					blackPieces.add(matrix[7][bestMove.charAt(2) - 'a']);
				}

				System.out.println("move " + bestMove);
            } else if (command.equals("analyze")) {
                // Modul force
                while (true) {
                    command = scanner.next();
                    // Variabila setata pe true, daca se intra in force mode
                    isForceMode = true;

                    if (command.equals("white")) {
                        // Engine-ul trece pe alb
                        isEngineBlack = false;
                    } else if (command.equals("black")) {
                        // Engine-ul trece pe negru
                        isEngineBlack = true;
                    } else if (command.length() == 4
                            && command.charAt(1) >= '1'
                            && command.charAt(1) <= '8') {

                        Piece piece = matrix[8 - (command.charAt(1) - '0')]
                                            [command.charAt(0) - 'a'];

                        // Piesa ce trebuie scoasa de pe tabla
                        Piece toRemove = matrix[8 - (command.charAt(3) - '0')]
                                                [command.charAt(2) - 'a'];

                        // Scoatere piesei luate din vectorul de piese
                        if(toRemove != null){
                            if(toRemove.colour == 'w'){
                                whitePieces.remove(toRemove);
                            } else if(toRemove.colour == 'b'){
                                blackPieces.remove(toRemove);
                            }
                        }

                        piece.move(command.charAt(3) - '0', command.charAt(2));
                        lastForcedMove = command;

                    } else if (command.equals("new")) {
                        // Reinitializarea matricei si a vectorilor
                        isEngineBlack = true;
                        isForceMode = false;

                        ChessBoard.getChessBoard().newBoard();
                        matrix = ChessBoard.getChessBoard().matrix;
                        whitePieces.removeAllElements();
                        blackPieces.removeAllElements();

                        for (int i = 0; i < 8; i++) {
                            blackPieces.add(matrix[1][i]);
                            whitePieces.add(matrix[6][i]);
                        }

                        blackPieces.add(matrix[0][1]);
                        blackPieces.add(matrix[0][6]);
                        whitePieces.add(matrix[7][1]);
                        whitePieces.add(matrix[7][6]);

                        // Adaugare ture
                        blackPieces.add(matrix[0][0]);
                        blackPieces.add(matrix[0][7]);
                        whitePieces.add(matrix[7][0]);
                        whitePieces.add(matrix[7][7]);

						// Adaugare nebuni
						blackPieces.add(matrix[0][2]);
						blackPieces.add(matrix[0][5]);
						whitePieces.add(matrix[7][2]);
						whitePieces.add(matrix[7][5]);

						// Adaugare regine
						blackPieces.add(matrix[0][3]);
						whitePieces.add(matrix[7][3]);

						// Adaugare regi
						blackPieces.add(matrix[0][4]);
						blackPieces.add(matrix[7][4]);

                        break;
                    } else if (command.equals("go")) {
                        // Se iese din force mode
                        break;
                    } else if (command.equals("print")) {
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                System.out.print(matrix[i][j] + " ");
                            }
                            System.out.println();
                        }
                    } else if (command.equals("printBlack")) {
						System.out.println(blackPieces);
					} else if (command.equals("printWhite")) {
						System.out.println(whitePieces);
					}
                }
            } else if (isEngineBlack
                    && (command.length() == 4 || command.length() == 5)
                    && command.charAt(1) >= '1'
                    && command.charAt(1) <= '8'
					&& command.charAt(0) >= 'a'
					&& command.charAt(0) <= 'h') {

                /*
                 * Daca nu s-a iesit recent din force mode, se actualizeaza matricea si vectorul,
                 * dupa miscarea albului
                 */
                if (!isForceMode) {
                    Piece piece = matrix[8 - (command.charAt(1) - '0')][command.charAt(0) - 'a'];

                    Piece toRemove = matrix[8 - (command.charAt(3) - '0')][command.charAt(2) - 'a'];

                    if (toRemove != null) {
                        blackPieces.remove(toRemove);
                    }

					if (piece instanceof King) {
						King king = (King) piece;
						king.hasMoved = true;
						piece = king;
					}

					if (piece instanceof Rook) {
						Rook rook = (Rook) piece;
						rook.hasMoved = true;
						piece = rook;
					}

					if (piece instanceof King) {
						if (Math.abs(command.charAt(2) - command.charAt(0)) == 2) {
							if (command.charAt(2) > command.charAt(0)) {
								Piece piece1 = matrix[8 - (command.charAt(1) - '0')][7];
								piece1.move(command.charAt(1) - '0', 'f');
							} else {
								Piece piece1 = matrix[8 - (command.charAt(1) - '0')][0];
								piece1.move(command.charAt(1) - '0', 'd');
							}
						}
					}

                    piece.move(command.charAt(3) - '0', command.charAt(2));

					if (piece instanceof Pawn && command.charAt(3) == '8') {
						whitePieces.remove(piece);
						whitePieces.add(matrix[0][command.charAt(2) - 'a']);
					}

                    if (blackPieces.size() == 0){
                        System.out.println("resign");
                        continue;
                    }
                }

                isForceMode = false;

                // Daca nu mai poate muta pioni, engine-ul da resign
                if (blackPieces.size() == 1) {
                    System.out.println("resign");
                    continue;
                }

                String bestMove = Utils.getAllMoves('b', whitePieces, blackPieces).get(0);
                int bestScore = Integer.MIN_VALUE;

                for (String move : Utils.getAllMoves('b', whitePieces, blackPieces)) {
					Pair<Integer, String> currentResult = Utils.negamax(3,
							ChessBoard.getChessBoard().matrix,
							whitePieces, blackPieces, 'b', move);
					String cMove = currentResult.second;
					int score = -currentResult.first;

					if (score > bestScore && currentResult.second != null) {
						bestScore = score;
						bestMove = currentResult.second;
					}
				}

                Piece piece = matrix[8 - (bestMove.charAt(1) - '0')][bestMove.charAt(0) - 'a'];
				Piece toRemove = matrix[8 - (bestMove.charAt(3) - '0')][bestMove.charAt(2) - 'a'];

				if (toRemove != null) {
					whitePieces.remove(toRemove);
				}

				if (piece instanceof King) {
					King king = (King) piece;
					king.hasMoved = true;
					piece = king;
				}

				if (piece instanceof Rook) {
					Rook rook = (Rook) piece;
					rook.hasMoved = true;
					piece = rook;
				}

				if (piece instanceof King) {
					if (Math.abs(bestMove.charAt(2) - bestMove.charAt(0)) == 2) {
						if (bestMove.charAt(2) > bestMove.charAt(0)) {
							Piece piece1 = matrix[8 - (bestMove.charAt(1) - '0')][7];
							piece1.move(bestMove.charAt(1) - '0', 'f');
						} else {
							Piece piece1 = matrix[8 - (bestMove.charAt(1) - '0')][0];
							piece1.move(bestMove.charAt(1) - '0', 'd');
						}
					}
				}

				piece.move(bestMove.charAt(3) - '0', bestMove.charAt(2));

				boolean isPromote = false;

				if (piece instanceof Pawn && bestMove.charAt(3) == '1') {
					blackPieces.remove(piece);
					blackPieces.add(matrix[7][bestMove.charAt(2) - 'a']);
					isPromote = true;
				}
				//System.out.println(whitePieces);

				if (isPromote) {
					System.out.println("move " + bestMove + "q");
				} else {
					System.out.println("move " + bestMove);
				}
            } else if (!isEngineBlack
					&& (command.length() == 4 || command.length() == 5)
                    && command.charAt(1) >= '1'
                    && command.charAt(1) <= '8'
					&& command.charAt(0) >= 'a'
					&& command.charAt(0) <= 'h') {

                /*
                 * Daca nu s-a iesit recent din force mode, se actualizeaza matricea si vectorul,
                 * dupa miscarea albului
                 */
                if(!isForceMode) {
                    Piece piece = matrix[8 - (command.charAt(1) - '0')][command.charAt(0) - 'a'];

                    Piece toRemove =
                            matrix[8 - (command.charAt(3) - '0')][command.charAt(2) - 'a'];

                    if (toRemove != null) {
                        whitePieces.remove(toRemove);
                    }

					if (piece instanceof King) {
						King king = (King) piece;
						king.hasMoved = true;
						piece = king;
					}

					if (piece instanceof Rook) {
						Rook rook = (Rook) piece;
						rook.hasMoved = true;
						piece = rook;
					}

					if (piece instanceof King) {
						if (Math.abs(command.charAt(2) - command.charAt(0)) == 2) {
							if (command.charAt(2) > command.charAt(0)) {
								Piece piece1 = matrix[8 - (command.charAt(1) - '0')][7];
								piece1.move(command.charAt(1) - '0', 'f');
							} else {
								Piece piece1 = matrix[8 - (command.charAt(1) - '0')][0];
								piece1.move(command.charAt(1) - '0', 'd');
							}
						}
					}

                    piece.move(command.charAt(3) - '0', command.charAt(2));

					if (piece instanceof Pawn && command.charAt(3) == '1') {
						blackPieces.remove(piece);
						blackPieces.add(matrix[7][command.charAt(2) - 'a']);
					}

                    if(whitePieces.size() == 0){
                        System.out.println("resign");
                        continue;
                    }
                }

                isForceMode = false;

                // Daca nu mai poate muta pioni, engine-ul da resign
                if (whitePieces.size() == 1) {
                    System.out.println("resign");
                    continue;
                }

				String bestMove = Utils.getAllMoves('w', whitePieces, blackPieces).get(0);
				int bestScore = Integer.MIN_VALUE;

				for (String move : Utils.getAllMoves('w', whitePieces, blackPieces)) {
					Pair<Integer, String> currentResult = Utils.negamax(3,
							ChessBoard.getChessBoard().matrix,
							whitePieces, blackPieces, 'w', move);
					String cMove = currentResult.second;
					int score = -currentResult.first;

					if (score > bestScore && currentResult.second != null) {
						bestScore = score;
						bestMove = currentResult.second;
					}
				}

				Piece piece = matrix[8 - (bestMove.charAt(1) - '0')][bestMove.charAt(0) - 'a'];
				Piece toRemove = matrix[8 - (bestMove.charAt(3) - '0')][bestMove.charAt(2) - 'a'];

				if (toRemove != null) {
					blackPieces.remove(toRemove);
				}

				if (piece instanceof King) {
					King king = (King) piece;
					king.hasMoved = true;
					piece = king;
				}

				if (piece instanceof Rook) {
					Rook rook = (Rook) piece;
					rook.hasMoved = true;
					piece = rook;
				}

				if (piece instanceof King) {
					if (Math.abs(bestMove.charAt(2) - bestMove.charAt(0)) == 2) {
						if (bestMove.charAt(2) > bestMove.charAt(0)) {
							Piece piece1 = matrix[8 - (bestMove.charAt(1) - '0')][7];
							piece1.move(bestMove.charAt(1) - '0', 'f');
						} else {
							Piece piece1 = matrix[8 - (bestMove.charAt(1) - '0')][0];
							piece1.move(bestMove.charAt(1) - '0', 'd');
						}
					}
				}

				piece.move(bestMove.charAt(3) - '0', bestMove.charAt(2));

				boolean isPromote = false;

				if (piece instanceof Pawn && bestMove.charAt(3) == '8') {
					whitePieces.remove(piece);
					whitePieces.add(matrix[0][bestMove.charAt(2) - 'a']);
					isPromote = true;
				}

				if (isPromote) {
					System.out.println("move " + bestMove + "q");
				} else {
					System.out.println("move " + bestMove);
				}
            } else if (command.equals("print")) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        System.out.print(matrix[i][j] + " ");
                    }
                    System.out.println();
                }
            } else if (command.equals("printBlack")) {
				System.out.println(blackPieces);
			} else if (command.equals("printWhite")) {
				System.out.println(whitePieces);
			}
        }
        scanner.close();
    }
}