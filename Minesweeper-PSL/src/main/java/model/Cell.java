package model;


/**
 * 
 * @author David Cobo
 *
 */

/**
 * Cell: class that contains the attributes of a cell
 */
public class Cell {

	/**
	 * The cell is unselected initially. This value can be uncovered or flag marked
	 */
	public static final char UNSELECTED_CELL = '.';

	/**
	 * The cell is disabled or empty. Means that there are not adjacent mines
	 */
	public static final char DISABLED_CELL = '-';

	/**
	 * The value of the cell is a mine
	 */
	public static final char MINE_CELL = '*';

	/**
	 * The cell is marked with the flag
	 */
	public static final boolean FLAG_CELL = true;

	/**
	 * The cell has not been shown
	 */
	public static final boolean HIDE_CELL = true;

	/**
	 * Command for the cell has been uncovered
	 */
	public static final char UNCOVER_COMMAND = 'U';


	/**
	 * Command for the cell has been marked
	 */
	public static final char MARK_COMMAND = 'M';

	/**
	 * The current value of a cell. Can be disabled, a number the adjacent mines, it is a mine or is marked.
	 */
	private char value;

	/**
	 * Current value if the mine is hidden
	 */
	private boolean hide;

	/**
	 *  Current value if the mine is marked
	 */
	private boolean mark;

	/**
	 *  Cell constructor 
	 */
	public Cell() {

	}

	/**
	 * Method that to init a cell with value disabled, hidden and the flag is not marked
	 */
	public void initCell() {
		value = DISABLED_CELL;
		hide = HIDE_CELL;
		mark = !FLAG_CELL;
	}


	/**
	 * Method that to init a cell with value mine, hidden and the flag is not marked
	 */
	public void initMine() {
		value = MINE_CELL;
		hide = HIDE_CELL;
		mark = !FLAG_CELL;
	}

	/**
	 * Method that add the amount of adjacent mines
	 */
	public void valuesAdjacentMines() {
		if(value!='*') {
			if(value=='-') {
				value='1';
			}
			else {
				value++;
			}
		}
	}

	public char getValue() {
		return value;
	}

	public void setValue(char contentParam) {
		value = contentParam;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hideParam) {
		hide = hideParam;
	}

	public boolean isMarked() {
		return mark;
	}

	public void setMarked(boolean markParam) {
		mark = markParam;
	}
}



