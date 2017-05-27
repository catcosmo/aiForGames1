import java.util.HashSet;
import java.util.Set;

import lenz.htw.bogapr.Move;

public class GameField {

	public final int DOWN = 0;
	public final int MID = 1;
	public final int UP = 2;
	public final int FREE = 3;

	private static Set<Integer> player0Chips = new HashSet<Integer>();
	private static Set<Integer> player1Chips = new HashSet<Integer>();
	private static Set<Integer> player2Chips = new HashSet<Integer>();

	// 0 = unten, 2 = oben bei [][x]
	/**
	 * Belegungen: 0 = Spieler 0, 1 = Spieler 1, 2 = Spieler 2, 3 = frei, 4 = zelle
	 * gibbet nicht
	 */
	private static int[][] field = new int[49][3];

	public static boolean isCellFree(int destination) {
		if (field[destination][0] == 3) {
			return true;
		} else
			return false;
	}

	public static boolean isCellOwnChip(int destination, int player) {
		int topChip = getAmtChipsOnField(destination);
		if (field[destination][topChip] == player)
			return true;
		return false;
	}

	public static boolean isCellFull(int cellNumber) {
		if (field[cellNumber][2] == 3) {
			return false;
		} else
			return true;
	}

	public static int getAmtChipsOnField(int chipPosition) {
		int amt = 0;
		int i = 0;
		while (field[chipPosition][i] < 3) {
			i++;
			amt++;
			if (i == 3)
				break;
		}
		return amt;
	}

	public static boolean isCellWall(int chipPosition) {
		if (chipPosition > 35)
			return true;
		return false;
	}

	//Zelle ist von Gegner besetzt & man kann auf die Zelle
	public static boolean isCellAttackable(int destination, int player) {
		if (!isCellFree(destination) && !isCellFull(destination)
				&& field[destination][getAmtChipsOnField(destination)] != player) {
			return true;
		} else
			return false;

	}

	public static void initializeBoard() {

		// You need to just change the order of Columns and rows , Yours is printing columns X rows and the solution is printing them rows X columns 
		for (int rows = 0; rows < field.length; rows++) {
			for (int columns = 0; columns < field[rows].length; columns++) {
				field[rows][columns] = 3;
			}
		}
		field[36][0] = field[36][1] = field[36][2] = field[48][0] = field[48][1] = field[48][2] = field[0][1] = field[0][2] = field[0][0] = 4;
		field[1][0] = field[1][1] = field[1][2] = field[2][0] = field[2][1] = field[2][2] = field[3][0] = field[3][1] = field[3][2] = 0;
		field[25][0] = field[25][1] = field[25][2] = field[37][0] = field[37][1] = field[37][2] = field[38][0] = field[38][1] = field[38][2] = 1;
		field[35][0] = field[35][1] = field[35][2] = field[47][0] = field[47][1] = field[47][2] = field[46][0] = field[46][1] = field[46][2] = 2;

		player0Chips.add(1);
		player0Chips.add(2);
		player0Chips.add(3);

		player1Chips.add(25);
		player1Chips.add(37);
		player1Chips.add(38);

		player2Chips.add(35);
		player2Chips.add(47);
		player2Chips.add(46);

		// You need to just change the order of Columns and rows , Yours is printing columns X rows and the solution is printing them rows X columns 
		for (int rows = 0; rows < field.length; rows++) {
			for (int columns = 0; columns < field[rows].length; columns++) {
				System.out.print(field[rows][columns] + "\t");
			}
			System.out.println();
		}
	}

	public static void makeMove(Move move) {
		int moveFrom = xyToPosition(move.fromX, move.fromY);
		int moveTo = xyToPosition(move.toX, move.toY);
		int player;
		if (field[moveFrom][2] != 3) {
			player = field[moveFrom][2];
			field[moveFrom][2] = 3;
			if (field[moveFrom][1] != player)
				removePlayerChips(player, moveFrom);
			moveTo(moveTo, player);
		} else if (field[moveFrom][1] != 3) {
			player = field[moveFrom][1];
			field[moveFrom][1] = 3;
			if (field[moveFrom][0] != player)
				removePlayerChips(player, moveFrom);
			moveTo(moveTo, player);
		} else {
			player = field[moveFrom][0];
			field[moveFrom][0] = 3;
			removePlayerChips(player, moveFrom);
			moveTo(moveTo, player);
		}
	}

	private static void moveTo(int moveTo, int player) {
		if (field[moveTo][2] != 3) {
			field[moveTo][2] = player;
		} else if (field[moveTo][1] != 3) {
			field[moveTo][1] = player;
		} else {
			field[moveTo][0] = player;
		}
		addPlayerChip(player, moveTo);
	}

	public static int xyToPosition(int x, int y) {
		return y * y + x;
	}

	public static int[] zToXY(int z) {
		int x = 0;
		int y = 0;
		if (z > 35) {
			y = 6;
			x = z - 36;
		} else if (z > 24) {
			y = 5;
			x = z - 25;
		} else if (z > 15) {
			y = 4;
			x = z - 16;
		} else if (z > 8) {
			y = 3;
			x = z - 9;
		} else if (z > 3) {
			y = 2;
			x = z - 4;
		} else if (z > 0) {
			y = 1;
			x = z - 1;
		}
		int[] xy = new int[2];
		xy[0] = x;
		xy[1] = y;
		return xy;

	}

	private static void removePlayerChips(int player, int position) {
		switch (player) {
		case 0:
			player0Chips.remove(position);
			break;
		case 1:
			player1Chips.remove(position);
			break;
		case 2:
			player2Chips.remove(position);
			break;
		}
	}

	private static void addPlayerChip(int player, int position) {
		switch (player) {
		case 0:
			player0Chips.add(position);
			break;
		case 1:
			player1Chips.add(position);
			break;
		case 2:
			player2Chips.add(position);
			break;
		}
	}

	public static Set<Integer> getPlayer0Chips() {
		return player0Chips;
	}

	public static void setPlayer0Chips(HashSet<Integer> player0Chips) {
		GameField.player0Chips = player0Chips;
	}

	public static Set<Integer> getPlayer1Chips() {
		return player1Chips;
	}

	public static void setPlayer1Chips(HashSet<Integer> player1Chips) {
		GameField.player1Chips = player1Chips;
	}

	public static Set<Integer> getPlayer2Chips() {
		return player2Chips;
	}

	public static void setPlayer2Chips(HashSet<Integer> player2Chips) {
		GameField.player2Chips = player2Chips;
	}

	//rotate for player 1 (left up) and player 2(right up)
	/*public static void turnField(int player) {
		int[][] newArray = new int[49][3];
		if (player == 1) {
			for (int rows = 0; rows < newArray.length; rows++) {
				for (int columns = 0; columns < newArray[rows].length; columns++) {
					int[] xy = zToXY(rows);
					int x = xy[0];
					int y = xy[1];
					//int y2 = zToXY(z)
					newArray[rows][columns] = field[rows - (y * y) - (2 * y + x)][columns];
				}
			}
	
			for (int rows = 0; rows < newArray.length; rows++) {
				for (int columns = 0; columns < newArray[rows].length; columns++) {
					System.out.print(newArray[rows][columns] + "\t");
				}
				System.out.println();
			}
		}
	}
	
	//undo rotation of board
	public static void undoTurnField(int player) {
		// TODO Auto-generated method stub
	
	}*/

}
