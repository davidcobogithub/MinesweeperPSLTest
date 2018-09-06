package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 
 * @author David Cobo
 *
 */

/**
 * Minesweeper: class that represents the game
 */
public class Minesweeper {

	/**
	 * Represents the number of the current round
	 */
	private int round;

	/**
	 *  Represents the current board
	 */
	private Board board;

	/*
	 * Constructor of the game
	 */
	/**
	 * Minesweeper Constructor
	 */
	public Minesweeper() {

	}


	/**
	 * Method main that create the game
	 */
	public static void main(String[] args) {
		Minesweeper game = new Minesweeper();
		game.initGame();
	}


	/**
	 * Allow to start and read the entire game and control every command if the player wins or lose the game
	 */
	public void initGame() {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("HELLO!     ¡WELCOME TO THE GAME MINESWEEPER! "+"\n"
					+ "You must enter the dimensions height, width of the board and the number of mines. Each number must be separated by a blank space (For example:8 15 10)");
			String stringRead = bufferedReader.readLine();

			initialDataEntryBoard(stringRead);

			boolean isPlaying = true;
			while (isPlaying) {
				boolean hasWon = false;
				boolean gameOver = false;
				while (!hasWon) {
					System.out.println("\n"+"Now, you must enter the position of the row, col and the command to be executed. Again, each number must be separated by a blank space (For example:1 1 U or 1 1 M)");
					stringRead = bufferedReader.readLine();
					String[] split = stringRead.split(" ");
					int row = Integer.parseInt(split[1]);
					int col = Integer.parseInt(split[0]);
					char command = split[2].charAt(0);

					boolean hasGameOver = play(row, col, command);

					if (hasGameOver) {

						restartToInitialRound();
						gameOver = true;
						break;
					}
					if (hasWonGame()) {
						hasWon = true;
						goToNextRound();
					}
				}

				System.out.println("¿Do you want to continue playing the level " + getRound() + "? (y/n)\n");
				String answerRead = bufferedReader.readLine();
				if (answerRead.toUpperCase().equals("N")) {
					System.out.println("Goodbye! Have a nice day");
					isPlaying = false;
				} else {
					if (answerRead.toUpperCase().equals("Y") && gameOver) {
						loadGame(board.getWidth(), board.getHeight(), board.getNumberMines());
					}
				}

			}
		} catch (Exception e) {

			System.out.println("Ohh!! Unfortunately, the game stopped. Please, restart the game to play again!!");
		}

	}

	/**
	 * Obtain and process the data that has been written by the player. Data is about width, height and 
	 * number of mines on the board.
	 * @param: stringRead, String that contains the line written by the player
	 */
	public void initialDataEntryBoard(String stringRead) throws Exception {

		int height = 0;
		int width = 0; 
		int minesAmount = 0;

		String[] split = stringRead.split(" ");
		try {
			height = Integer.parseInt(split[0]);
			width = Integer.parseInt(split[1]);
			minesAmount = Integer.parseInt(split[2]);

			if (minesAmount > height * width) {
				throw new Exception("Hey! There are more mines than number of cells on the board");
			}

			loadGame(height, width, minesAmount);

		} catch (NumberFormatException e) {
			System.out.println("Hey! Remember that the type of entry numbers must be separated by blank spaces and enter the dimensions height, width of the board and the number of mines");

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());

		}
	}

	/**
	 * Load the game with height, width and the number of mines
	 * @param: height, represents the board's height
	 * @param: width, represents the board's width
	 * @param: mines, represents the number of mines on the board
	 */
	public void loadGame(int height, int width, int mines) {

		board = new Board(height, width, mines);
		restartToInitialRound();
		System.out.println("\nROUND " + round);
		board.showBoard();
	}

	/**
	 * This method allow to verify if the game is over. The game is over if the number of marked mines is equal to
	 * the number of mines on the board.
	 * @return true if the player has won the game, false otherwise.
	 */
	public boolean hasWonGame() {

		boolean hasWon = false;
		Cell[][] cells=board.getSquares();

		int markedMinesFlag = 0;
		int markedCellsFlag = 0;

		for (int i = 1; i <= cells[0].length-1; i++) {
			for (int j = 1; j <= cells.length-1; j++) {

				if (cells[j][i].getValue() == Cell.MINE_CELL && cells[j][i].isMarked()) {

					markedMinesFlag++;
				}
				if (cells[j][i].isMarked()) {

					markedCellsFlag++;
				}
			}
		}

		if (markedMinesFlag == markedCellsFlag && markedCellsFlag == board.getNumberMines()) {
			hasWon = true;
			System.out.println("Congratulations! You won this game! Let's go, play the next round!!");
		}

		return hasWon;
	}

	/**
	 * Allow plays one turn of the current round. A cell can be marked or uncovered. If a cell
	 * contains a mine and it's uncovered, the game is over
	 * @param: row, represents the rows on the board
	 * @param: col, represents the columns on the board
	 * @param: command, represents the action to do. It can mark or uncover a hidden cell.
	 * @return: true if the player has uncovered a mine, false otherwise
	 */
	public boolean play(int row, int col, char command) {
		boolean gameOver = false;
		gameOver = board.playsTurnRound(row, col, command);

		board.showBoard();

		if (gameOver) {
			System.out.println("Ohh! I'm sorry, you've found a mine and you've exploded!. Let's go, continue the game!!");
		}
		return gameOver;
	}

	/**
	 * Allows go to the next round and creates a new game with the same dimensions before
	 */
	public void goToNextRound() {

		board = new Board(board.getWidth(), board.getHeight(), board.getNumberMines());
		System.out.print("\n"+"You have advanced to the next round, enjoy the game!");
		board.showBoard();
		System.out.println();
		round++;
	}

	/**
	 *  Restart to the initial round
	 */
	public void restartToInitialRound() {
		round = 1;
	}

	
	public int getRound() {
		return round;
	}

	public void setRound(int roundParam) {
		round = roundParam;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board boardParam) {
		board = boardParam;
	}
}



