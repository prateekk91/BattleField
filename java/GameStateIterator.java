import java.util.LinkedList;
import java.util.ListIterator;

public class GameStateIterator {
    private LinkedList<GameState> gameList;
    private ListIterator<GameState> gameListIterator;
    private boolean gameFinishedFlag;
    private char winner;
    public GameStateIterator() {
         gameList = new LinkedList<GameState>();
         //gameList.add(new GameState());
         gameList.add(Main.nextState);
         gameListIterator = gameList.listIterator();
         gameFinishedFlag = false;
         winner = 0;
    }

    public GameState getNextGameState() {
        if(gameListIterator.hasNext()) {
            return gameListIterator.next();
        } else {
            GameState gameState = gameListIterator.previous();
            gameListIterator.next();
            return gameState;
        }
    }
    public GameState getPreviousGameState() {
        if(gameListIterator.hasPrevious()) {
            return gameListIterator.previous();
        } else {
	    GameState gameState = gameListIterator.next();
            gameListIterator.previous();
            return gameState;
        }
    }

	public void setWinner(char winner) {
		this.winner = winner;
	}
	
	public char getWinner() {
		return winner;
	}
	
	public void setGameFinished() {
		this.gameFinishedFlag = true;
	}

	public boolean isGameFinished() {
		return gameFinishedFlag;
	}
	private int count(char color)
        {
            int count=0;
            for(int i=0;i<7;i++)
                for(int j=0;j<7;j++)
                    if(getNextGameState().getBoard()[i][j].getColor()==color)
                        count++;
            return count;
        }
    public GameState getNextGameState(String move) throws InvalidMoveException, Exception {
        GameState presentGameState = getPreviousGameState();
        gameListIterator.next();
        GameState nextGameState = presentGameState.setMove(move);
        gameListIterator.add(nextGameState);

        if(gameList.size()==200)
        {
            setGameFinished();
            if(count('R') > count('B'))
                winner = 'R';
            else if(count('B')>count('R'))
                winner = 'B';
            else
                if(count('R')==count('B'))
                    winner = 'D';

        }
    if(nextGameState.getPreviousMove().equals("MERCY") || !UniqueID.isAlive[20] || !UniqueID.isAlive[41]){
            gameFinishedFlag = true;
            if(!UniqueID.isAlive[20]) {
        		winner = 'B';
            } else if(!UniqueID.isAlive[41]) {
	        	winner = 'R';
            } else {
                winner = presentGameState.getNextPlayer();
            }
            
            setGameFinished();
        }       
        return nextGameState;
    }
}

