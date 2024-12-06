import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}

class Game {
    private static final int BOARD_SIZE = 9;
    private static final char PLAYER_SYMBOL = 'X';
    private static final char AI_SYMBOL = 'O';
    private static final char EMPTY_BOX = ' ';
    private final Scanner scan;
    private final char[] board;

    public Game() {
        this.scan = new Scanner(System.in);
        this.board = initializeBoard();
    }

    public void start() {
        GameStatus status = GameStatus.ONGOING;

        while (status == GameStatus.ONGOING) {
            displayBoard();
            playerMove();
            status = checkGameStatus(PLAYER_SYMBOL);
            if (status != GameStatus.ONGOING) break;

            aiMove();
            status = checkGameStatus(AI_SYMBOL);
        }

        displayBoard();
        displayResult(status);
    }

    private char[] initializeBoard() {
        char[] board = new char[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = EMPTY_BOX;
        }
        return board;
    }

    private void displayBoard() {
        System.out.println("\n " + board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("---+---+---");
        System.out.println(" " + board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("---+---+---");
        System.out.println(" " + board[6] + " | " + board[7] + " | " + board[8]);
    }

    private void playerMove() {
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

    private void aiMove() {
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

    private GameStatus checkGameStatus(char symbol) {
        if (isWinner(symbol)) {
            return symbol == PLAYER_SYMBOL ? GameStatus.PLAYER_WON : GameStatus.AI_WON;
        }
        if (isDraw()) {
            return GameStatus.DRAW;
        }
        return GameStatus.ONGOING;
    }

    private boolean isWinner(char symbol) {
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

    private boolean isDraw() {
        for (char cell : board) {
            if (cell == EMPTY_BOX) return false;
        }
        return true;
    }

    private void displayResult(GameStatus status) {
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
