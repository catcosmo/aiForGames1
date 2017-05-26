import java.util.ArrayList;
import java.util.Iterator;

import lenz.htw.bogapr.Move;

public class MinMax {

	private final int CHIPHIT = 1;
	private final int WALLHIT = 10;

	private int player;
	private int MAX = 1000;
	private int MIN = -1000;
	private int score;

	private Move tempMove;
	private Move finalMove;
	private ArrayList<Move> possibleMoveList = new ArrayList<Move>();
	private ArrayList<Integer> moveRatingList = new ArrayList<Integer>();

	public MinMax(int i) {
		this.player = i;
	}

	public double minimax(int node, int depth, boolean maximizingPlayer, double alpha, double beta) {
		if (depth == 0 || isTerminating(node))
			return estimate();
		if (maximizingPlayer) {
			double v = Double.NEGATIVE_INFINITY;
			for (int i = 0; i < 2; i++) {// for each child of node
				v = max(v, minimax(node * 2 + i, depth - 1, false, alpha, beta));
				alpha = max(alpha, v);
				if (beta <= alpha)
					break;
			}
			return v;
		} else {
			double v = Double.POSITIVE_INFINITY;
			for (int i = 0; i < 2; i++) {// for each child of node
				v = min(v, minimax(node * 2 + i, depth - 1, true, alpha, beta));
				beta = min(beta, v);
				if (beta <= alpha)
					break;
			}
			return v;
		}

	}

	// Einstiegspunkt
	public Move generateMoves() {
		//if (player != 0)
		//	GameField.turnField(player);
		if (player == 0)
			for (Iterator<Integer> it = GameField.getPlayer0Chips().iterator(); it.hasNext();) {
				int i = it.next();
				generateSingleMove(i);
			}
		else if (player == 1)
			for (Iterator<Integer> it = GameField.getPlayer1Chips().iterator(); it.hasNext();) {
				int i = it.next();
				generateSingleMove(i);
			}
		else
			for (Iterator<Integer> it = GameField.getPlayer2Chips().iterator(); it.hasNext();) {
				int i = it.next();
				generateSingleMove(i);
			}
		System.out.println(possibleMoveList.size());
		System.out.println(moveRatingList.size());
		//if (player != 0) {
		//	GameField.undoTurnField(player);
		//}
		return possibleMoveList.get(0);
	}

	private void generateSingleMove(int chipPosition) {
		if (GameField.getAmtChipsOnField(chipPosition) == 3) {
			calculateThreeChipMove(chipPosition);
		} else if (GameField.getAmtChipsOnField(chipPosition) == 2) {
			calculateTwoChipMove(chipPosition);
		} else
			calculateOneChipMove(chipPosition);
	}

	private void calculateOneChipMove(int chipPosition) {
		int[] xyPosition = GameField.zToXY(chipPosition);
		int x = xyPosition[0];
		int y = xyPosition[1];
		switch (player) {
		case 0:
			//x ungerade
			if (x % 2 != 0) {
				rateMove(chipPosition, chipPosition - 1);
				rateMove(chipPosition, chipPosition + 1);
				//x gerade
			} else {
				//TODO: Boundaries for 36 + 48
				rateMove(chipPosition, GameField.xyToPosition(x + 1, y + 1));
			}
			break;
		case 1:
			if (x % 2 == 0) {
				rateMove(chipPosition, chipPosition + 1);
			} else {
				rateMove(chipPosition, chipPosition + 1);
				rateMove(chipPosition, GameField.xyToPosition(x - 1, y - 1));
			}
			break;
		case 2:
			if (x % 2 == 0) {
				rateMove(chipPosition, chipPosition - 1);
			} else {
				rateMove(chipPosition, chipPosition - 1);
				rateMove(chipPosition, GameField.xyToPosition(x - 1, y - 1));
			}
			break;
		}

	}

	private void calculateTwoChipMove(int chipPosition) {
		int[] xyPosition = GameField.zToXY(chipPosition);
		int x = xyPosition[0];
		int y = xyPosition[1];
		switch (player) {
		case 0:
			rateMove(chipPosition, GameField.xyToPosition(x, y + 1));
			rateMove(chipPosition, GameField.xyToPosition(x + 2, y + 1));
			break;
		case 1:
			rateMove(chipPosition, GameField.xyToPosition(x, y - 1));
			rateMove(chipPosition, GameField.xyToPosition(x + 2, y));
			break;
		case 2:
			rateMove(chipPosition, GameField.xyToPosition(x - 2, y - 1));
			rateMove(chipPosition, GameField.xyToPosition(x - 2, y));
			break;
		}

	}

	private void calculateThreeChipMove(int chipPosition) {
		int[] xyPosition = GameField.zToXY(chipPosition);
		int x = xyPosition[0];
		int y = xyPosition[1];
		switch (player) {
		case 0:
			if (x % 2 == 0) {
				rateMove(chipPosition, GameField.xyToPosition(x + 1, y + 2));
				rateMove(chipPosition, GameField.xyToPosition(x + 3, y + 2));
			} else {
				rateMove(chipPosition, GameField.xyToPosition(x - 1, y + 1));
				rateMove(chipPosition, GameField.xyToPosition(x + 1, y + 1));
				rateMove(chipPosition, GameField.xyToPosition(x + 4, y + 1));
			}
			break;
		case 1:
			if (x % 2 == 0) {
				rateMove(chipPosition, GameField.xyToPosition(x + 1, y - 1));
				rateMove(chipPosition, GameField.xyToPosition(x + 3, y));
			} else {
				rateMove(chipPosition, GameField.xyToPosition(x + 3, y));
				rateMove(chipPosition, GameField.xyToPosition(x + 1, y - 1));
				rateMove(chipPosition, GameField.xyToPosition(x - 1, y - 2));
			}
			break;
		case 2:
			if (x % 2 == 0) {
				rateMove(chipPosition, GameField.xyToPosition(x - 3, y - 1));
				rateMove(chipPosition, GameField.xyToPosition(x - 3, y));
			} else {
				rateMove(chipPosition, GameField.xyToPosition(x - 3, y));
				rateMove(chipPosition, GameField.xyToPosition(x - 3, y - 1));
				rateMove(chipPosition, GameField.xyToPosition(x - 3, y - 2));
			}
			break;
		}
	}

	private void rateMove(int chipPosition, int destination) {
		int rating = 0;
		int[] fromxy = GameField.zToXY(chipPosition);
		int[] toxy = GameField.zToXY(destination);

		Move m = new Move(fromxy[0], fromxy[1], toxy[0], toxy[1]);

		if (GameField.isCellWall(destination)) {
			rating = WALLHIT;
		} else if (GameField.isCellAttackable(destination, player)) {
			rating = CHIPHIT;
		}
		if (!GameField.isCellFull(destination) && !GameField.isCellOwnChip(destination, player)) {
			possibleMoveList.add(m);
			moveRatingList.add(rating);
		}
	}

	private double estimate() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*int max(int spieler, int tiefe) {
		    if (tiefe == 0 or keineZuegeMehr(spieler))
		       return bewerten();
		    int maxWert = -unendlich;
		    generiereMoeglicheZuege(spieler);
		    while (noch Zug da) {
		       fuehreNaechstenZugAus();
		       int wert = min(-spieler, tiefe-1);
		       macheZugRueckgaengig();
		       if (wert > maxWert) {
		          maxWert = wert;
		          if (tiefe == gewuenschteTiefe)
		             gespeicherterZug = Zug;
		       }
		    }
		    return maxWert;
		 }*/

	private boolean isTerminating(int node) {
		// TODO Auto-generated method stub
		return false;
	}

	private double max(double alpha, double v) {
		// TODO Auto-generated method stub
		return 0;
	}

	private double min(double beta, double v) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void resetMovelist() {
		possibleMoveList.clear();
		moveRatingList.clear();
	}

}
