package testing;

import java.io.IOException;
import Game.tigerzoneClientProtocol;
import Game.tigerzoneServerProtocol;

public class protocolTest {
	public static void main(String[] args) throws IOException {
		boolean stop = false;
		
		String sOut = " ";
		String aOut = " ";
		
		//Create server and two clients
		tigerzoneServerProtocol serverP = new tigerzoneServerProtocol();
		tigerzoneClientProtocol playerA = new tigerzoneClientProtocol();
		
		while (!stop) {
			if (sOut != null) {
				aOut = playerA.processMessage(sOut);
				System.out.println("PlayerA: " + aOut + "\n");
			}
			sOut = serverP.processInput(aOut);
			System.out.println("ServerA: " + sOut);
			try {
				if (aOut.equals("Bye.")) {
					break;
				}
			} catch (Exception ex){}
		}
	}
}