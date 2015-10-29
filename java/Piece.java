/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Prateek
 */
public class Piece {
        private int uniqueID = 0;
        private char symbol;
	private char color = 'R';
	private Rank rank = null;
	private boolean shown = false;
	private static char pieceAtId[] = {'G','C','C','L','L','L','M','M','M','M','R','R','R','R','R','S','B','B','B','B','F','G','C','C','L','L','L','M','M','M','M','R','R','R','R','R','S','B','B','B','B','F'};

        public Piece()
        {
            uniqueID = -1;
            symbol = 'I';
            color  = (char)-1;
            rank = Rank.NIL;
            shown= false;
        }
        
	public Piece(int id, char symbol,char color, Rank rank) 
	{
		uniqueID = id;
                this.symbol = symbol;
		this.color = color;
		this.rank = rank;
	}

	public char getColor() 
	{
		return color;
	}

	public Rank getRank() 
	{
		return rank;
	}
	
	public int getID() 
	{
		return uniqueID;
	}
        
        public char getSymbol()
        {
            return symbol;
        }
	
	public boolean isShown()
	{
		return shown;
	}
	
	public void setShown(boolean b)
	{
		shown = b;
		
	}	
	

	public int compareTo(Piece p)
	{
		return uniqueID - p.uniqueID;
	}
	
	public boolean equals(Object p)
	{
		return (uniqueID == ((Piece)p).uniqueID);
	}
        
        /**
     *
     * @param id
     * @return
     */
    public static char getPieceById(int id)
        {
            return pieceAtId[id];
        }
}
