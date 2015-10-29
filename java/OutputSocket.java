import java.io.IOException;
import java.io.PrintWriter;

public class OutputSocket extends PlayerSocket {
    private PrintWriter out;
    public OutputSocket(int _port) {
        super(_port);
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch(IOException exception) {
            System.err.println("Error : " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    void putMessage(String message) {
        out.println(message);
    }
}
