import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lenz.htw.bogapr.Move;
import lenz.htw.bogapr.net.NetworkClient;

public class Playa {
	//get image
	private static BufferedImage getImage(){
		BufferedImage pic = null;
		try{ pic = ImageIO.read(new File("cat.png"));
		} catch (IOException ex){
		  System.out.println("Missing Image!");
		  System.exit(1);
		};	
		return pic;
	}
	
	//connect to server
	static NetworkClient network = new NetworkClient("localhost", "wumms", getImage());
	
	//networkClient.getExpectedNetworkLatencyInMilliseconds();
    //networkClient.getTimeLimitInSeconds();
    //networkClient.getMyPlayerNumber();
	
	//create playing field
	static byte[] field = new byte[64];
	
	public static void main(String[] args) {
		for (;;) {
			Move receiveMove;
			while ((receiveMove = network.receiveMove()) != null) {
				//Zug in meine Brettrepräsentation einarbeiten
			}
			//berechne tollen Zug
			//networkClient.sendMove(new Move(abc));
		}
	}
}
