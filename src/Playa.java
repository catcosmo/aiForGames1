import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lenz.htw.bogapr.net.NetworkClient;

public class Playa {
    public static void main(String[] args) {
        try {
            NetworkClient networkClient = new NetworkClient(null, "CLIENT 0", ImageIO.read(new File("meinLogo.png")));
            
            //networkClient.getExpectedNetworkLatencyInMilliseconds();
            //networkClient.getTimeLimitInSeconds();
            //networkClient.getMyPlayerNumber();
            
            for (;;) {
                Move receiveMove;
                while ((receiveMove = networkClient.receiveMove()) != null) {
                    //Zug in meine Brettrepräsentation einarbeiten
                }
                //berechne tollen Zug
                networkClient.sendMove(new Move(abc));
            }
        } catch (IOException e) {
            throw new RuntimeException("", e);
        }
    }
}
