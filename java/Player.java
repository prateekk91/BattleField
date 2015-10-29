

import java.io.IOException;


public class Player {
    private InputSocket inputSocket;
    private OutputSocket outputSocket;
    public Player(int inputPort, int outPort) {
    	outputSocket = new OutputSocket(outPort);
        inputSocket = new InputSocket(inputPort);
    }
    public void setInputSocket(int port) {
        inputSocket = new InputSocket(port);
    }
    public void setOutputSocket(int port) {
        outputSocket = new OutputSocket(port);
    }
    public String getNextMove() throws IOException, InvalidMoveException, Exception {
        return inputSocket.getNextMessage();
    }
    public void setNextMove(String move) {
        outputSocket.putMessage(move);
    }
    public void setAttackString(String attack)
    {
        outputSocket.putMessage(attack);
    }
    public String getInitialConfig() throws IOException, InvalidMoveException, Exception
    {
        return inputSocket.getNextMessage();
    }
}
