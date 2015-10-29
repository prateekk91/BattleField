
public class GameState {

    private Piece board[][];
    private char nextPlayer;
    private String previousMove;
    private String previousAttack;
    public GameState() {
        board = new Piece[7][7];
        for(int i=0;i<7;i++)
            for(int j=0;j<7;j++)
                board[i][j] = new Piece();
        nextPlayer = 'R';
        board[3][1] = new Piece(100, 'W', (char)-1, Rank.WATER);
        board[3][5] = new Piece(100, 'W', (char)-1, Rank.WATER);
        previousMove = "START";
        previousAttack = "* *";
        
        
    }

    public GameState(GameState gameState) {
        
        board = gameState.getBoard();
        nextPlayer = gameState.getNextPlayer();
       
    }

    public char getNextPlayer() {
        return nextPlayer;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void setPreviousMove(String move) {
    	previousMove = move;
    }
    
    public String getPreviousMove() {
    	return previousMove;
    }
    
    public void setPreviousAttack(Piece source, Piece destination)
    {   if(source.getRank() == Rank.NIL || destination.getRank() == Rank.NIL)
            previousAttack = "* *";
        else if(source.getColor() == 'B' )
            previousAttack = source.getSymbol() + " " + destination.getSymbol();
        else 
            previousAttack =  destination.getSymbol() + " " + source.getSymbol();
    }
    public String getAttack() {
        return previousAttack;
    }
    
    public void changeNextPlayer() {
        if(nextPlayer == 'B') {
            nextPlayer = 'R';
        } else if(nextPlayer == 'R') {
            nextPlayer = 'B';
        }
    }
    
    private void performMoveIfDestinationEmpty(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn){
        setPreviousAttack(board[sourceRow][sourceColumn],board[destinationRow][destinationColumn]);
        board[destinationRow][destinationColumn] = board[sourceRow][sourceColumn];
        board[sourceRow][sourceColumn] = new Piece();
    }
    private void performMoveIfDestinationFlag(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn){
        setPreviousAttack(board[sourceRow][sourceColumn],board[destinationRow][destinationColumn]);
        showPiece(board[destinationRow][destinationColumn]);
        showPiece(board[sourceRow][sourceColumn]);
        killPiece(board[destinationRow][destinationColumn]);
    }
    private void performMoveIfDestinationBomb(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn){
        setPreviousAttack(board[sourceRow][sourceColumn],board[destinationRow][destinationColumn]);
        if(board[sourceRow][sourceColumn].getSymbol() == 'M'){
            killPiece(board[destinationRow][destinationColumn]);
            board[destinationRow][destinationColumn] = board[sourceRow][sourceColumn];
        }
        else{
            killPiece(board[sourceRow][sourceColumn]);
        }
        showPiece(board[destinationRow][destinationColumn]);
        board[sourceRow][sourceColumn] = new Piece();
    }
    private void performMoveIfDestinationPerson(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn){
        setPreviousAttack(board[sourceRow][sourceColumn],board[destinationRow][destinationColumn]);
        if(board[sourceRow][sourceColumn].getSymbol() == board[destinationRow][destinationColumn].getSymbol()){
            killPiece(board[destinationRow][destinationColumn]);
            killPiece(board[sourceRow][sourceColumn]);
            board[destinationRow][destinationColumn] = new Piece();
        }
        else if(board[sourceRow][sourceColumn].getSymbol() == 'S' &&
                board[destinationRow][destinationColumn].getSymbol() == getHighestRankedPlayer(getNextPlayer())){
            killPiece(board[destinationRow][destinationColumn]);
            board[destinationRow][destinationColumn] = board[sourceRow][sourceColumn];
            showPiece(board[destinationRow][destinationColumn]);
        }
        else if (board[destinationRow][destinationColumn].getSymbol() == 'S' &&
                board[sourceRow][sourceColumn].getSymbol() == getHighestRankedPlayer((getNextPlayer()=='R') ? 'B' : 'R')){
            killPiece(board[sourceRow][sourceColumn]);
            showPiece(board[destinationRow][destinationColumn]);
        }
        else{

            if(board[destinationRow][destinationColumn].getRank().toInt() < board[sourceRow][sourceColumn].getRank().toInt()){
                killPiece(board[sourceRow][sourceColumn]);
                showPiece(board[destinationRow][destinationColumn]);
            }
            else{
                killPiece(board[destinationRow][destinationColumn]);
                board[destinationRow][destinationColumn] = board[sourceRow][sourceColumn];
                showPiece(board[destinationRow][destinationColumn]);
            }
        }
        board[sourceRow][sourceColumn] = new Piece();
    }
    
    private char getHighestRankedPlayer(char player){
                int i,id;
                for(i=0,id=(player =='R' ? 0 : 21); i<16; i++,id++){
                    if(UniqueID.isAlive[id] == true)
                        break;
                }
                return Piece.getPieceById(id);
    }
    private boolean isDistanceInvalid(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn){
        if((Math.abs(destinationColumn - sourceColumn) == 1 && destinationRow == sourceRow) || 
           (Math.abs(destinationRow - sourceRow) == 1 && destinationColumn == sourceColumn) )
                return false;
        return true;
    }
    
    private void killPiece (Piece p){
        UniqueID.isAlive[p.getID()] = false;
    }
    private void showPiece (Piece p){
        p.setShown(true);
    }
    private boolean canPerformMove(String move) throws InvalidMoveException{
        int sourceRow = move.charAt(0) - 'A';
        int sourceColumn = move.charAt(1) - '1';
        int destinationRow = move.charAt(3) - 'A';
        int destinationColumn = move.charAt(4) - '1'; 
        Piece sourcePiece = board[sourceRow][sourceColumn];
        Piece destinationPiece = board[destinationRow][destinationColumn];
        if(
            destinationPiece.getRank() == Rank.WATER ||
            destinationPiece.getColor() == sourcePiece.getColor() ||
            sourcePiece.getColor() == getNextPlayer() ||
            sourcePiece.getSymbol() == 'B' ||
            sourcePiece.getSymbol() == 'F' ||
            sourcePiece.getColor() != (getNextPlayer()=='R' ? 'B' : 'R') ||
            isDistanceInvalid(sourceRow, sourceColumn, destinationRow, destinationColumn)
            ){
                throw new InvalidMoveException("Can't perform move" + getNextPlayer());
        }
      //throw exception and return false
        //if destination is water
        //if destination is occupied by own team piece
        //if destination is same as source
        //if source is not occupied by own team piece
        //if source and destination are more than one place apart
        //if source is immobile
        //if source piece is not occupied by own team
        
        return true;
    }
   
    private void performMove (String move){
        int sourceRow = move.charAt(0) - 'A';
        int sourceColumn = move.charAt(1) - '1';
        int destinationRow = move.charAt(3) - 'A';
        int destinationColumn = move.charAt(4) - '1'; 
      
        Piece destinationPiece = board[destinationRow][destinationColumn];
        
        if(destinationPiece.getRank() == Rank.NIL)
            performMoveIfDestinationEmpty(sourceRow, sourceColumn, destinationRow, destinationColumn);
        else if (destinationPiece.getRank() == Rank.FLAG)
            performMoveIfDestinationFlag(sourceRow, sourceColumn, destinationRow, destinationColumn);
        else if (destinationPiece.getRank() == Rank.BOMB)
            performMoveIfDestinationBomb(sourceRow, sourceColumn, destinationRow, destinationColumn);
        else
            performMoveIfDestinationPerson(sourceRow, sourceColumn, destinationRow, destinationColumn);
       
    }

    private void printBoard()
    {
        
        for(int i=0;i<7;i++)
            {
            for(int j=0;j<7;j++)
                System.out.print(this.board[i][j].getSymbol()+" ");
            System.out.println();
        }
         
    }
    public GameState setMove(String move) throws InvalidMoveException {

    	if(move == null) {
        	throw new InvalidMoveException("Null object exception");
        }
        
        if(move.length() != 5) {
        	throw new InvalidMoveException("Wrong Move Format");
        }

        if(!move.equals("MERCY") && 
              ( move.charAt(0) < 'A' || move.charAt(0) > 'G' ||
       	        move.charAt(1) < '0' || move.charAt(1) > '7' ||
                move.charAt(2) != '-'||
                move.charAt(3) < 'A' || move.charAt(3) > 'G' ||
                move.charAt(4) < '0' || move.charAt(4) > '7' 
              )
          ) {
        	throw new InvalidMoveException("Wrong Move Format");
        }
        this.changeNextPlayer();
        GameState nextState = new GameState(this);
        
        if (!move.equals("MERCY") && nextState.canPerformMove(move)){
            nextState.performMove(move);
        }
        
        nextState.setPreviousMove(move);
        
        
        //printBoard();
        return nextState;
    }
    
    public void initBoard(String placementString,char color) throws InvalidMoveException
    {
        System.out.println(placementString);
        int g,c,l,m,r,s,b,f;
        g=c=l=m=r=s=b=f=0;
        if(placementString.length() != 21)
            throw new InvalidMoveException("Invalid initial configuration");
        for(int i=0;i<placementString.length();i++)
        {
            switch(placementString.charAt(i))
            {
                case 'G': g++; break;
                case 'C': c++;break;
                case 'L': l++; break;
                case 'M': m++; break;
                case 'R': r++; break;
                case 'S': s++; break;
                case 'B': b++; break;
                case 'F': f++; break;
            }
        }
        if((g!=1) || (c!=2) || (l!=3) || (m!=4) || (r!=5) || (s!=1) || (b!=4) || (f!=1))
        {
            throw new InvalidMoveException("Invalid initial configuration");
            
        }

        if(color=='B')
        {
            for(int i=4;i<7;i++)
            {
                for(int j=0;j<7;j++)
                {
                    Rank rank = Rank.NIL;
                    int id = 0;
                    Piece p;
                    switch(placementString.charAt((((i-4)*7)+j)))
                    {
                        case 'G' : rank = Rank.ONE; id = new UniqueID().getIdForGeneral(color);break;
                        case 'C' : rank = Rank.TWO; id = new UniqueID().getIdForColonel(color);break;
                        case 'L' : rank = Rank.THREE; id = new UniqueID().getIdForLeutenant(color);break;
                        case 'M' : rank = Rank.FOUR; id = new UniqueID().getIdForMiner(color); break;
                        case 'R' : rank = Rank.FIVE; id = new UniqueID().getIdForRider(color); break;
                        case 'S' : rank = Rank.SPY; id = new UniqueID().getIdForSpy(color); break;
                        case 'B' : rank = Rank.BOMB; id = new UniqueID().getIdForBomb(color); break;
                        case 'F' : rank = Rank.FLAG; id = new UniqueID().getIdForFlag(color); break;
                    }
                    UniqueID.isAlive[id] = true;

                    p = new Piece(id, placementString.charAt((((i-4)*7)+j)),color, rank);
                    p.setShown(false);
                    this.board[i][j] = p;
                    }
                }
          }

        if(color=='R')
        {
            for(int i=2;i>=0;i--)
            {
                for(int j=0;j<7;j++)
                {
                    Rank rank = Rank.NIL;
                    int id = 0;
                    Piece p;
                    switch(placementString.charAt(((i*7)+j)))
                    {
                        case 'G' : rank = Rank.ONE; id = new UniqueID().getIdForGeneral(color);break;
                        case 'C' : rank = Rank.TWO; id = new UniqueID().getIdForColonel(color);break;
                        case 'L' : rank = Rank.THREE; id = new UniqueID().getIdForLeutenant(color);break;
                        case 'M' : rank = Rank.FOUR; id = new UniqueID().getIdForMiner(color); break;
                        case 'R' : rank = Rank.FIVE; id = new UniqueID().getIdForRider(color); break;
                        case 'S' : rank = Rank.SPY; id = new UniqueID().getIdForSpy(color); break;
                        case 'B' : rank = Rank.BOMB; id = new UniqueID().getIdForBomb(color); break;
                        case 'F' : rank = Rank.FLAG; id = new UniqueID().getIdForFlag(color); break;
                    }
                    UniqueID.isAlive[id] = true;

                    p = new Piece(id, Piece.getPieceById(id),color, rank);
                    p.setShown(false);
                    this.board[2-i][j] = p;
                    }
                }
          }
            
     }
        
    
}
