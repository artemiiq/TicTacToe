import java.util.Scanner;

public class Main {

    private static final int BOARD_SIZE = 9;
    private static final char PLAYER_SYMBOL = 'X';
    private static final char AI_SYMBOL = 'O';
    private static final char EMPTY_BOX = ' ';

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        char[] board = initializeBoard();
        GameStatus status = GameStatus.ONGOING;

        while (status == GameStatus.ONGOING) {
            displayBoard(board);
            playerMove(scan, board);
            status = checkGameStatus(board, PLAYER_SYMBOL);
            if (status != GameStatus.ONGOING) break;

            aiMove(board);
            status = checkGameStatus(board, AI_SYMBOL);
        }

        displayBoard(board);
        displayResult(status);
    }

    private static char[] initializeBoard() {
        char[] board = new char[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = EMPTY_BOX;
        }
        return board;
    }

    private static void displayBoard(char[] board) {
        System.out.println("\n " + board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("---+---+---");
        System.out.println(" " + board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("---+---+---");
        System.out.println(" " + board[6] + " | " + board[7] + " | " + board[8]);
    }

    private static void playerMove(Scanner scan, char[] board) {
        int move;
        while (true) {
            System.out.print("Enter your move (1-9): ");
            move = scan.nextInt() - 1;
            if (move >= 0 && move < BOARD_SIZE && board[move] == EMPTY_BOX) {
                board[move] = PLAYER_SYMBOL;
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    private static void aiMove(char[] board) {
        int move;
        while (true) {
            move = (int) (Math.random() * BOARD_SIZE);
            if (board[move] == EMPTY_BOX) {
                board[move] = AI_SYMBOL;
                break;
            }
        }
        System.out.println("AI chose position: " + (move + 1));
    }

    private static GameStatus checkGameStatus(char[] board, char symbol) {
        if (isWinner(board, symbol)) {
            return symbol == PLAYER_SYMBOL ? GameStatus.PLAYER_WON : GameStatus.AI_WON;
        }
        if (isDraw(board)) {
            return GameStatus.DRAW;
        }
        return GameStatus.ONGOING;
    }

    private static boolean isWinner(char[] board, char symbol) {
        int[][] winningCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };
        for (int[] combination : winningCombinations) {
            if (board[combination[0]] == symbol &&
                    board[combination[1]] == symbol &&
                    board[combination[2]] == symbol) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDraw(char[] board) {
        for (char cell : board) {
            if (cell == EMPTY_BOX) return false;
        }
        return true;
    }

    private static void displayResult(GameStatus status) {
        switch (status) {
            case PLAYER_WON -> System.out.println("Congratulations, you won!");
            case AI_WON -> System.out.println("AI won. Better luck next time!");
            case DRAW -> System.out.println("It's a draw!");
        }
    }

    enum GameStatus {
        PLAYER_WON,
        AI_WON,
        DRAW,
        ONGOING
    }
}
