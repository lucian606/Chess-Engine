public class ChessBoard {
    private static ChessBoard chessBoard = new ChessBoard();
    private static final int MAX_DIMENSION = 8;
    private static final int INFINITY = 1000;

    public Piece[][] matrix;

    // Initializarea tablei la inceputul meciului
    private ChessBoard() {
        matrix = new Piece[MAX_DIMENSION][MAX_DIMENSION];

        for (int i = 0; i < MAX_DIMENSION; i++) {
            matrix[1][i] = new Pawn(7, (char) ('a' + i), 'b', 1);
            matrix[6][i] = new Pawn(2, (char) ('a' + i), 'w', 1);
        }

        matrix[0][0] = new Rook(8, 'a', 'b', 4);
        matrix[0][7] = new Rook(8, 'h', 'b', 4);
        matrix[0][1] = new Knight(8, 'b', 'b', 3);
        matrix[0][6] = new Knight(8, 'g', 'b', 3);
        matrix[0][2] = new Bishop(8, 'c', 'b', 4);
        matrix[0][5] = new Bishop(8, 'f', 'b', 4);
        matrix[0][3] = new Queen(8, 'd', 'b', 9);
        matrix[0][4] = new King(8, 'e', 'b', INFINITY);

        matrix[7][0] = new Rook(1, 'a', 'w', 4);
        matrix[7][7] = new Rook(1, 'h', 'w', 4);
        matrix[7][1] = new Knight(1, 'b', 'w', 3);
        matrix[7][6] = new Knight(1, 'g', 'w', 3);
        matrix[7][2] = new Bishop(1, 'c', 'w', 4);
        matrix[7][5] = new Bishop(1, 'f', 'w', 4);
        matrix[7][3] = new Queen(1, 'd', 'w', 9);
        matrix[7][4] = new King(1, 'e', 'w', INFINITY);
    }

    // Resetarea tablei dupa apelul functiei "new"
    public void newBoard() {
        for(int i = 0; i < MAX_DIMENSION; i++) {
            for(int j = 0; j < MAX_DIMENSION; j++) {
                matrix[i][j]=null;
            }
        }

        for (int i = 0; i < MAX_DIMENSION; i++) {
            matrix[1][i] = new Pawn(7, (char) ('a' + i), 'b', 1);
            matrix[6][i] = new Pawn(2, (char) ('a' + i), 'w', 1);
        }

        matrix[0][0] = new Rook(8, 'a', 'b', 4);
        matrix[0][7] = new Rook(8, 'h', 'b', 4);
        matrix[0][1] = new Knight(8, 'b', 'b', 3);
        matrix[0][6] = new Knight(8, 'g', 'b', 3);
        matrix[0][2] = new Bishop(8, 'c', 'b', 4);
        matrix[0][5] = new Bishop(8, 'f', 'b', 4);
        matrix[0][3] = new Queen(8, 'd', 'b', 9);
        matrix[0][4] = new King(8, 'e', 'b', INFINITY);

        matrix[7][0] = new Rook(1, 'a', 'w', 4);
        matrix[7][7] = new Rook(1, 'h', 'w', 4);
        matrix[7][1] = new Knight(1, 'b', 'w', 3);
        matrix[7][6] = new Knight(1, 'g', 'w', 3);
        matrix[7][2] = new Bishop(1, 'c', 'w', 4);
        matrix[7][5] = new Bishop(1, 'f', 'w', 4);
        matrix[7][3] = new Queen(1, 'd', 'w', 9);
        matrix[7][4] = new King(1, 'e', 'w', INFINITY);
    }

    // Metoda ce intoarce tabla de sah
    public static ChessBoard getChessBoard() {
        return chessBoard;
    }
}
