import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lenz.htw.bogapr.Move;
import lenz.htw.bogapr.net.NetworkClient;

public class Playa {
	// get image
	private static BufferedImage getImage() {
		BufferedImage pic = null;
		try {
			pic = ImageIO.read(new File("cat.png"));
		} catch (IOException ex) {
			System.out.println("Missing Image!");
			System.exit(1);
		}
		;
		return pic;
	}

	// connect to server
	static NetworkClient network = new NetworkClient("localhost", "wumms", getImage());

	// networkClient.getExpectedNetworkLatencyInMilliseconds();
	// networkClient.getTimeLimitInSeconds();
	// networkClient.getMyPlayerNumber();

	// create playing field

	public static void main(String[] args) {
		GameField.initializeBoard();
		int player = network.getMyPlayerNumber();
		MinMax minMax = new MinMax(player);
		for (;;) {
			Move receiveMove;
			Move move = null;
			//int desiredDepth = 4;
			//double score = minMax.minimax(0, 0, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			while ((receiveMove = network.receiveMove()) != null) {
				GameField.makeMove(receiveMove, player);
				// Zug in meine Brettrepräsentation einarbeiten
			}
			minMax.generateMoves();
			// berechne tollen Zug
			// networkClient.sendMove(new Move(abc));
		}
	}
}
