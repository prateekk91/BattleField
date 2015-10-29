

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

abstract class PlayerSocket {
    protected Socket socket;
    private ServerSocket serverSocket;
    
    public PlayerSocket() {
    }
  
    public PlayerSocket(int _port) {

        try {
            serverSocket = new ServerSocket(_port);

            socket = serverSocket.accept();
            serverSocket.close();
            System.out.println("Connection Accepted");
        } catch(IOException exception) {
            System.err.println("Error : " + exception.getMessage());
            exception.printStackTrace();
            System.exit(1);
        }
    }
    
}
