import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;


public class InputSocket extends PlayerSocket {
    BufferedReader in;
    public InputSocket(int _port) {
		super(_port);
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
			in = new BufferedReader(inputStreamReader);
		} catch(IOException exception) {
			System.err.println("Error : " + exception.getMessage());
			exception.printStackTrace();
		}   	
    }
   
    String getNextMessage() throws IOException, Exception {
		try {
			Thread.sleep(2000);
			if(in.ready()) {
				return in.readLine();
			} else {
				throw new Exception("time limit exceeded exception");
			}
		} catch (InterruptedException exception) {
			System.err.println("Error : " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}
	}
    String getInitialConfig() throws IOException, Exception {
		try {
			Thread.sleep(2000);
			if(in.ready()) {
				return in.readLine();
			} else {
				throw new Exception("time limit exceeded exception");
			}
		} catch (InterruptedException exception) {
			System.err.println("Error : " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}
	}
}
