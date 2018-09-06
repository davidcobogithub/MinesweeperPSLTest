package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.Cell;
import model.Minesweeper;

/**
 * 
 * @author David Cobo
 *
 */



/**
 * MinesweeperTest: Allow to make unit test from class Minesweeper
 */
public class MinesweeperTest {

	
	/**
	 * Allow to access to the class Minesweeper
	 */
	private Minesweeper minesweeper;

	/**
	 * Creates the first scenario with seven rows, nine columns and one mine
	 */
	public void firstScenario() {
		minesweeper = new Minesweeper();
		minesweeper.loadGame(7, 9, 1);
	}


	/**
	 * Creates the second scenario with six rows, five columns and fifteen mines
	 */
	public void secondScenario() {
		minesweeper = new Minesweeper();
		minesweeper.loadGame(6, 5, 15);
	}

	/**
	 * Creates the third scenario with four rows, three columns and twelve mines
	 */
	public void thirdScenario() {
		minesweeper = new Minesweeper();
		minesweeper.loadGame(4, 3, 12);
	}


	/**
	 * Execute the test for the first scenario, with the objective to decide if player has won
	 */
	@Test
	public void verifyVictoryFirstScenario() {
		firstScenario();

		int width = minesweeper.getBoard().getWidth();
		int height = minesweeper.getBoard().getHeight();
		Cell[][] cells=minesweeper.getBoard().getSquares();

		for (int i = 1; i <= width; i++) {
			for (int j = 1; j <= height; j++) {
				if (cells[j][i].getValue() == Cell.MINE_CELL) {
					minesweeper.play(j, i, Cell.MARK_COMMAND);
				}
			}
		}
		assertTrue(minesweeper.hasWonGame(), "It should be a victory");
	}

	
	/**
	 * Execute the test for the second scenario, with the objective to decide if player has won
	 */
	@Test
	public void verifyVictorySecondScenario() {
		secondScenario();

		int width = minesweeper.getBoard().getWidth();
		int height = minesweeper.getBoard().getHeight();
		Cell[][] cells=minesweeper.getBoard().getSquares();
		for (int i = 1; i <= width; i++) {
			for (int j = 1; j <= height; j++) {
				if (cells[j][i].getValue() == Cell.MINE_CELL) {
					minesweeper.play(j, i, Cell.MARK_COMMAND);
				}
			}
		}
		assertTrue(minesweeper.hasWonGame());
	}

	/**
	 * Execute the test for the third scenario, with the objective to decide if player has won
	 */
	@Test
	public void verifyVictoryThirdScenario() {
		thirdScenario();

		int width = minesweeper.getBoard().getWidth();
		int height = minesweeper.getBoard().getHeight();
		Cell[][] cells=minesweeper.getBoard().getSquares();

		for (int i = 1; i <= width; i++) {
			for (int j = 1; j <= height; j++) {
				if (cells[j][i].getValue() == Cell.MINE_CELL) {
					minesweeper.play(j, i, Cell.MARK_COMMAND);
				}
			}
		}
		assertTrue(minesweeper.hasWonGame());
	}

}
