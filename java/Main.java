import java.io.IOException;

public class Main {
    static GameView view;
    static Player player1;
    static Player player2;
    static GameState nextState;
    static GameStateIterator gameIterator;
    private static String playPlayer(Player player, String move) throws IOException, Exception {
        try {
            player1.setNextMove(move);
            player1.setAttackString(gameIterator.getNextGameState().getAttack());
            if(!move.equalsIgnoreCase("START")){
                player2.setNextMove(move);
                player2.setAttackString(gameIterator.getNextGameState().getAttack());
            }

            move = player.getNextMove();
            if(player.equals(player1)) {
                    System.out.println("Player 1 move -: " + move);
            } else {
                    System.out.println("Player 2 move -: " + move);
            }
            System.out.println(gameIterator.getNextGameState().getAttack());
            nextState = gameIterator.getNextGameState(move);
            
            if(gameIterator.isGameFinished()) {
                        view.update(nextState);
		        if(gameIterator.getWinner() == 'R') {
                            view.display("Player 1 is Winner");
		        } if(gameIterator.getWinner() == 'B') {
		            view.display("Player 2 is Winner");
		        } else if(gameIterator.getWinner() == 'D') {
		        	view.display("Game Drawn");
		        }
            }
        } catch(InvalidMoveException exception) {
            System.out.println("Wrong Move : " + exception.getMessage());
        	if(player.equals(player1)) {
                view.display("Player 2 is Winner");
            } else {
                view.display("Player 1 is Winner");
            }
        }
            catch(Exception exception) {
            System.out.println("Wrong Move : " + exception.getMessage());
        	if(player.equals(player1)) {
                view.display("Player 2 is Winner");
            } else {
                view.display("Player 1 is Winner");
            }
        }
        view.update(nextState);
            
        return move;
    }
    public static void main(String[] args) throws IOException, Exception {
        player1 = new Player(8001, 8000);
        player2 = new Player(9001, 9000);
        
        view = new GameView();
        
        
        String placementRed="", placementBlue="";
        try {
            placementRed = player1.getInitialConfig();
        }
        catch(InvalidMoveException e)
        {
            view.display("Player 2 is winner "+ e.getMessage());
        }
        try {
            
            placementBlue = player2.getInitialConfig();
        }
        catch(InvalidMoveException e)
        {
            view.display("Player 1 is winner " + e.getMessage());
        }

        nextState = new GameState();
	try
        {
            nextState.initBoard(placementRed,'R');
        }
        catch(InvalidMoveException e)
        {
            view.display("Player 2 is winner");
        }
        try
        {
            nextState.initBoard(placementBlue, 'B');
        }
        catch(InvalidMoveException e)
        {
            view.display("Player 1 is winner");
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
		        view.setVisible(true);
            }
        });
        view.update(nextState);
        gameIterator = new GameStateIterator();
        
        String move = new String("START");
        
        while(true) {
                move = playPlayer(player1, move);
                move = playPlayer(player2, move);
        }
    }
}
