import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import lenz.htw.bogapr.Move;

public class MinMax {

	private final int CHIPHIT = 1;
	private final int WALLHIT = 10;
	private final long timeLimit;
	private final long tStart;

	private int player;
	private int fixedPlayer;
	private int MAX = 1000;
	private int MIN = -1000;
	private int score;

	private int count = 0;

	private Move tempMove;
	private Move finalMove;
	private ArrayList<Move> possibleMoveList = new ArrayList<Move>();
	private ArrayList<Integer> moveRatingList = new ArrayList<Integer>();

	private ArrayList<ArrayList<Move>> allMoves = new ArrayList<ArrayList<Move>>();
	private ArrayList<ArrayList<Integer>> allRatings = new ArrayList<ArrayList<Integer>>();

	public MinMax(int i, long theTimeLimit) {
		this.player = i;
		fixedPlayer = i;
		tStart = System.currentTimeMillis();
		timeLimit = theTimeLimit;
	}

	/*public Move startMinMax(int node, int depth, boolean maximizingPlayer, double alpha, double beta, long timeLimit) {
		long tStart = System.currentTimeMillis();
	
		while (System.currentTimeMillis() - tStart < 50) {
			if (player == 0)
				for (Iterator<Integer> it = GameField.getPlayer0Chips().iterator(); it.hasNext();) {
					int i = it.next();
					minimax(i, depth, maximizingPlayer, alpha, beta);
				}
			else if (player == 1)
				for (Iterator<Integer> it = GameField.getPlayer1Chips().iterator(); it.hasNext();) {
					int i = it.next();
					minimax(i, depth, maximizingPlayer, alpha, beta);
				}
			else
				for (Iterator<Integer> it = GameField.getPlayer2Chips().iterator(); it.hasNext();) {
					int i = it.next();
					minimax(i, depth, maximizingPlayer, alpha, beta);
				}
		}
		return null;
	
	}*/

	public double minimax(int depth, boolean maximizingPlayer) {
		int estimate = 0;
		System.out.println(System.currentTimeMillis() - tStart);
		System.out.println(timeLimit);

		if (depth == 0) {// || noMovesLeft 
			//finalMove = allMoves.get(allMoves.size() - 1).get(getBestMoveIndex());
			player = fixedPlayer;
			return (allRatings.get(allRatings.size() - 1).get(getBestMoveIndex()));
		}
		if (maximizingPlayer) {
			double maxValue = Double.NEGATIVE_INFINITY;
			generateMoves();
			ArrayList<Move> saveMoveList = new ArrayList<Move>(possibleMoveList);
			ArrayList<Integer> saveRatingList = new ArrayList<Integer>(moveRatingList);

			allMoves.add(saveMoveList);
			allRatings.add(saveRatingList);
			int index = allMoves.size() - 1;
			for (int i = 0; i < saveMoveList.size(); i++) {
				GameField.makeMove(saveMoveList.get(i));
				resetMovelist();
				player = (player + 1) % 3;
				count++;
				boolean isMax = false;
				if (count % 3 == 0)
					isMax = true;
				double value = minimax(depth - 1, isMax);
				GameField.makeMove(undo(allMoves.get(index).get(i)));
				if (value > maxValue) {
					maxValue = value;
					//if (System.currentTimeMillis() - tStart < timeLimit - 50)
					finalMove = allMoves.get(0).get(i);
				}
			}
			return maxValue;
		} else {
			double minValue = Double.POSITIVE_INFINITY;
			generateMoves();
			ArrayList<Move> saveMoveList = new ArrayList<Move>(possibleMoveList);
			ArrayList<Integer> saveRatingList = new ArrayList<Integer>(moveRatingList);

			allMoves.add(saveMoveList);
			allRatings.add(saveRatingList);

			int index = allMoves.size() - 1;
			for (int i = 0; i < saveMoveList.size(); i++) {
				GameField.makeMove(saveMoveList.get(i));
				resetMovelist();
				player = (player + 1) % 3;
				count++;
				boolean isMax = false;
				if (count % 3 == 0)
					isMax = true;
				double value = minimax(depth - 1, isMax);
				GameField.makeMove(undo(allMoves.get(index).get(i)));
				if (value < minValue) {
					minValue = value;
				}
			}
			return minValue;
		}

	}

	private Move undo(Move move) {
		return new Move(move.toX, move.toY, move.fromX, move.fromY);
	}

	/*public double minimax(int node, int depth, boolean maximizingPlayer, double alpha, double beta) {
		double estimate = 0;
		if (depth == 0) //|| isTerminating(node))
			estimate = estimate();
		if (maximizingPlayer) {
			double v = Double.NEGATIVE_INFINITY;
			for (int i = 0; i < possibleMoveList.size(); i++) {
				v = max(v, minimax(node * 2 + i, depth - 1, false, alpha, beta));
				alpha = max(alpha, v);
				if (beta <= alpha)
					break;
			}
			estimate = v;
		} else {
			double v = Double.POSITIVE_INFINITY;
			for (int i = 0; i < 2; i++) {
				v = min(v, minimax(node * 2 + i, depth - 1, true, alpha, beta));
				beta = min(beta, v);
				if (beta <= alpha)
					break;
			}
			estimate = v;
		}
		return estimate;
	
	}*/

	/*private double generateMove() {
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
		return getBestMove();
	}*/

	// Einstiegspunkt
	public int generateMoves() {
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
		return getBestMoveIndex();

	}

	private int getBestMoveIndex() {
		int rating = -1;
		//Move m = null;
		int moveIndex = 0;
		for (int i = 0; i < possibleMoveList.size(); i++) {
			if (moveRatingList.get(i) > rating) {
				rating = moveRatingList.get(i);
				//m = possibleMoveList.get(i);
				score += rating;
				moveIndex = i;
			}
		}
		if (rating == 0) {
			Random rn = new Random();

			int randomNum = rn.nextInt((possibleMoveList.size() - 1) - 0 + 1) + 0;
			//m = possibleMoveList.get(randomNum);
			score += rating;
			moveIndex = randomNum;
		}
		return moveIndex;
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

	public void resetMovelist() {
		possibleMoveList.clear();
		moveRatingList.clear();
	}

	public void resetAllLists() {
		allMoves.clear();
		allRatings.clear();
	}

	public Move getMove() {
		return finalMove;
	}

}
