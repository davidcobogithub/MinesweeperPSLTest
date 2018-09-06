package model;

import java.util.Random;

/**
 * 
 * @author David Cobo
 *
 */

/**
 * Board: class that contains the attributes of the board
 */
public class Board {

	
	/**
	 * Represents a matrix of cells or also board of cells
	 */
	private Cell[][] squares;

	/**
	 * Represents the board's height or matrix height
	 */
	private int height;


	/**
	 * Represents the board's width or matrix width
	 */
	private int width;

	/**
	 * Represents the number of mines on the board or matrix
	 */
	private int numberMines;

	/**
	 * Board constructor: load the height, width, numberMines and the matrix of cells
	 * @param: rows, it represents the board's height
	 * @param: colums, it represents the board's width
	 * @param: mines, it represents the amount of mines on the board
	 */
	public Board(int rows, int colums, int mines) {

		height = colums;
		width = rows;
		numberMines = mines;

		squares= new Cell[height+1][width+1]; 

		loadCellsBoard();
		loadRandomMinesBoard();
	}

	
	/**
	 * Allow plays one turn of the current round. A cell can be marked or uncovered. If a cell
	 * contains a mine and it's uncovered, the game is over
	 * 
	 * @param: heightParam, represents the rows on the board
	 * @param: widthParam, represents the columns on the board
	 * @param: command, represents the action to do. It can mark or uncover a hidden cell
	 * @return: true if the player has uncovered a mine, false otherwise
	 */
	public boolean playsTurnRound(int heightParam, int widthParam, char command) {

		boolean gameOver = false;
		Cell cell = squares[heightParam][widthParam];

		if (command == Cell.UNCOVER_COMMAND && cell.isMarked() == !Cell.FLAG_CELL) {

			switch (cell.getValue()) {
			case Cell.MINE_CELL:

				showMinesGameOver();
				cell.setMarked(!Cell.FLAG_CELL);
				gameOver = true;
				break;

			case Cell.DISABLED_CELL:
				showAdjacents(heightParam, widthParam);
				break;
			default:
				cell.setHide(!Cell.HIDE_CELL);
				cell.setMarked(!Cell.FLAG_CELL);
				break;
			}

		} else if (command == Cell.MARK_COMMAND && cell.isHide() == Cell.HIDE_CELL) {
			if (cell.isMarked()) {
				cell.setMarked(!Cell.FLAG_CELL);
			}
			else {
				cell.setMarked(Cell.FLAG_CELL);
			}
		}

		return gameOver;
	}

	/**
	 * Print in console the current board to show the player
	 */
	public void showBoard() {

		System.out.println("\nLook the board: ");

		for (int i = 1; i <= height; i++) {
			if (i == 1)
				System.out.print("	");
			System.out.print(i + "	");
		}
		System.out.println();
		for (int i = 1; i <= height; i++) {
			if (i == 1)
				System.out.print("	");
			System.out.print("________");
		}
		System.out.println();
		for (int i = 1; i <= width; i++) {
			for (int j = 1; j <= height; j++) {
				if (j == 1)
					System.out.print(i + "	|");
				Cell cellValue = squares[j][i];
				if (cellValue.isMarked())
					System.out.print("P	");
				else {
					if (cellValue.isHide())
						System.out.print(".	");
					else
						System.out.print(cellValue.getValue() + "	");
				}

			}
			System.out.print("|\n");
		}

		System.out.print("	|");
		for (int i = 1; i <= height; i++) {

			System.out.print("________");
		}
		System.out.println();
	}
	
	
	/**
	 * Show the board cells with with all mines when the game is over
	 */
	public void showMinesGameOver() {

		for (int i = 1; i <= height; i++) {
			for (int j = 1; j <= width; j++) {
				Cell cell = squares[i][j];
				if (cell.getValue()==Cell.MINE_CELL) {
					cell.setHide(!Cell.HIDE_CELL);
				}

			}
		}

	}
	
	/**
	 * Recursive method that to show the adjacent cells in the board 
	 * @Param: heightParam, cell row to be uncovered
	 * @Param: widthParam, cell column to be uncovered
	 */
	private void showAdjacents(int heightParam, int widthParam) {

		Cell cell = squares[heightParam][widthParam];
		if (cell.getValue() > '0' && cell.getValue() <= '8' && !cell.isMarked()) {
			cell.setHide(!Cell.HIDE_CELL);
		}
		else {
			if (cell.getValue() == Cell.DISABLED_CELL && !cell.isMarked() && cell.isHide()) {
				cell.setHide(!Cell.HIDE_CELL);
				// uncover cells that are over and below
				if (heightParam > 1) {
					showAdjacents(heightParam - 1, widthParam);
					if (heightParam < height) {
						showAdjacents(heightParam + 1, widthParam);
					}
				} else {
					if (heightParam < height) {
						showAdjacents(heightParam + 1, widthParam);
					}
				}
				// uncover cells that are on the left and right
				if (widthParam > 1) {
					showAdjacents(heightParam, widthParam - 1);
					if (widthParam < width) {
						showAdjacents(heightParam, widthParam + 1);
					}
				} else {
					if (widthParam < width) {
						showAdjacents(heightParam, widthParam + 1);
					}
				}

				// uncover cells that are on diagonals
				if (heightParam > 1 && widthParam > 1) {
					showAdjacents(heightParam - 1, widthParam - 1);
				}
				if (heightParam < height && widthParam < width) {
					showAdjacents(heightParam + 1, widthParam + 1);
				}
				if (heightParam > 1 && widthParam < width) {
					showAdjacents(heightParam - 1, widthParam + 1);
				}
				if (heightParam < height && widthParam > 1) {
					showAdjacents(heightParam + 1, widthParam - 1);
				}
			}
		}

	}


	/**
	 * Load the board cells initially with values
	 */
	private void loadCellsBoard() {

		for (int i = 1; i <= height; i++) {
			for (int j = 1; j <= width; j++) {
				Cell cell = new Cell();
				cell.initCell();

				squares[i][j]=cell;

			}
		}
	}

	/**
	 * Load the board cells initially with mines
	 */
	private void loadRandomMinesBoard() {
		Random randomNumber = new Random();

		for (int i = 0; i < numberMines; i++) {
			boolean carryOn = true;
			int randomHeight = randomNumber.nextInt(height) + 1;
			int randomWidth = randomNumber.nextInt(width) + 1;

			do {
				if (squares[randomHeight][randomWidth].getValue() == Cell.MINE_CELL) {
					randomHeight = randomNumber.nextInt(height) + 1;
					randomWidth = randomNumber.nextInt(width) + 1;
				} else {
					addMineBoard(randomHeight, randomWidth);
					carryOn = false;
				}
			} while (carryOn);
		}
	}
	

	/**
	 * Adds a mine in a cell and increments the value inside the adjacent cells
	 * @param: randomHeight, represents the cell row where the mine to be
	 * @param: randomWidth, represents the cell column where the mine to be
	 */
	private void addMineBoard(int randomHeight, int randomWidth) {
		Cell mine = new Cell();
		mine.initMine();
		squares[randomHeight][randomWidth]=mine;

		/*
		 * increments the amount of adjacent mines over and below the mine position
		 */
		if (randomHeight > 1) {
			squares[randomHeight - 1][randomWidth].valuesAdjacentMines();
			if (randomHeight < height) {
				squares[randomHeight + 1][randomWidth].valuesAdjacentMines();
			}
		} else {
			if (randomHeight < height) {
				squares[randomHeight + 1][randomWidth].valuesAdjacentMines();
			}
		}

		/*
		 * increments the amount of adjacent mines on left and right of the mine
		 * position
		 */
		if (randomWidth > 1) {
			squares[randomHeight][randomWidth - 1].valuesAdjacentMines();
			if (randomWidth < width) {
				squares[randomHeight][randomWidth + 1].valuesAdjacentMines();
			}
		} else {
			if (randomWidth < width) {
				squares[randomHeight][randomWidth + 1].valuesAdjacentMines();
			}
		}

		/*
		 * increments the amount of adjacent mines on diagonals to mine position
		 */
		if (randomWidth > 1 && randomHeight > 1) {
			squares[randomHeight - 1][randomWidth - 1].valuesAdjacentMines();
		}
		if (randomWidth < width && randomHeight < height) {
			squares[randomHeight + 1][randomWidth + 1].valuesAdjacentMines();
		}
		if (randomWidth > 1 && randomHeight < height) {
			squares[randomHeight + 1][randomWidth - 1].valuesAdjacentMines();
		}
		if (randomWidth < width && randomHeight > 1) {
			squares[randomHeight - 1][randomWidth + 1].valuesAdjacentMines();
		}
	}

	public Cell[][] getSquares() {
		return squares;
	}

	public void setSquares(Cell[][] squaresParam) {
		squares = squaresParam;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int heightParam) {
		height = heightParam;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int widthParam) {
		width = widthParam;
	}

	public int getNumberMines() {
		return numberMines;
	}

	public void setNumberMines(int numberMinesParam) {
		numberMines = numberMinesParam;
	}


}

