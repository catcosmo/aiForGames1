
public class GameField {

	// 0 = unten, 2 = oben bei [][x]
	/**
	 * Belegungen: 0 = Spieler 0 1 = Spieler 1 2 = Spieler 2 3 = frei 4 = zelle
	 * gibbet nicht
	 */
	private int[][] field = new int[49][3];

	public boolean isCellFree(short cellNumber) {
		if (field[cellNumber][0] == 3) {
			return true;
		} else
			return false;
	}

	public boolean isCellFull(short cellNumber) {
		if (field[cellNumber][2] != 3) {
			return false;
		} else
			return true;
	}

	public short getAmtChipsOnField(short cellNumber) {
		short amt = 0;
		int i = 0;
		while (field[cellNumber][i] < 3) {
			i++;
			amt++;
		}
		return amt;
	}

	public boolean isCellAttackable(short cellNumber, short player) {
		if (!isCellFree(cellNumber) && !isCellFull(cellNumber)
				&& field[cellNumber][getAmtChipsOnField(cellNumber)] != player) {
			return true;
		} else
			return false;

	}

}
