public class MinMax {

	private int player;
	private int MAX = 1000;
	private int MIN = -1000;
	private int score;

	public MinMax(int i) {
		this.player = i;
	}

	public double minimax(int node, int depth, boolean maximizingPlayer, double alpha, double beta) {
		if (depth == 0 || isTerminating(node))
			return score;
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

}
