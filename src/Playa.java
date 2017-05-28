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
	static NetworkClient network = new NetworkClient("localhost", "#AllesAllen", getImage());

	// networkClient.getExpectedNetworkLatencyInMilliseconds();
	// networkClient.getTimeLimitInSeconds();
	// networkClient.getMyPlayerNumber();

	// create playing field

	public static void main(String[] args) {
		GameField.initializeBoard();
		int player = network.getMyPlayerNumber();
		MinMax minMax = new MinMax(player, network.getExpectedNetworkLatencyInMilliseconds());
		for (;;) {
			Move receiveMove;

			while ((receiveMove = network.receiveMove()) != null) {
				System.out.println("Received move: " + receiveMove);
				// Zug in meine Brettrepräsentation einarbeiten
				GameField.makeMove(receiveMove);

			}
			System.out.println(player);
			minMax.minimax(3, true);
			Move m = minMax.getMove();
			System.out.println(player + ": " + m);
			minMax.resetAllLists();
			//GameField.makeMove(m, player, -1);
			// berechne tollen Zug
			network.sendMove(m);
		}
	}
}
