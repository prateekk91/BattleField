/*------------------------------------------Code Warrior (Avishkar 2k12)-----------------------------------------
-------------------------------------------------Game : Battlefield----------------------------------------------
------------------------------------------------Code Template----------------------------------------------------
Edit 'initConfig' and implement 'StrategicMove()' function
*/
#include<iostream>
#include<cstdio>
#include<cstring>
#include<string>

using namespace std;

#define RED 1
#define BLUE -1

#define fabs(x) ((x > 0) ? (x) : (-x))

#define G 360  // General
#define C 150  // Colonel
#define L 80   // Lieutenant
#define M 45   // Miner
#define R 30   // Rider
#define S 300  // Spy
#define B 100  // Bomb
#define F 5000 // Flag
#define U -10  // Unknown
#define N 0    // Nil
#define W 1    // Water

int myPieceColor;	// Will hold the color of your pieces.

int board[7][7] = { U, U, U, U, U, U, U,
                    U, U, U, U, U, U, U,
                    U, U, U, U, U, U, U,
                    N, W, N, N, N, W, N,
                    U, U, U, U, U, U, U,
                    U, U, U, U, U, U, U,
                    U, U, U, U, U, U, U };

                        // General  Colonel Lieutenent  Miner  Rider    spy     Bomb
int killedPieces[2][7] = {
				0,      0,	0,      0,	0,	0,	0,	//mine
				
				0,      0,	0,      0,	0,	0,	0	//opponents
                          };
class BattleField {
	public:
	void StrategicBoardIntialize(string initConfig, int turn);
	void UpdateBoard(string moveLine1, string moveLine2, int turn);
	int GetIndex(int piece);
	int GetPieceValue(char ch);                                
	bool CanKill(int myPiece, int oppPiece);
	string StrategicMove(int turn);
	void DisplayByPiece();
	void DisplayByPieceValue();
};

/*---This function will initialize your board and put your pieces on board at their
     appropriate places using string 'initConfig' (from main function)---*/
void BattleField :: StrategicBoardIntialize(string initConfig, int turn){
	int offst[3];
	if(turn == RED){
		offst[0] = 2;
		offst[1] = 1;
		offst[2] = 0;
	}
	else{
		offst[0] = 4;
		offst[1] = 5;
		offst[2] = 6;
	}
	
	int i, j, k = 0, piece;
	char ch;
	for(i = 0; i < 3; i++){
		for(j = 0; j < 7; j++){
			ch = initConfig[k];
			if(ch == 'G')
				piece = G;
			else if(ch == 'C')
				piece = C;
			else if(ch == 'L')
				piece = L;
			else if(ch == 'M')
				piece = M;
			else if(ch == 'R')
				piece = R;
			else if(ch == 'S')
				piece = S;
			else if(ch == 'B')
				piece = B;
			else if(ch == 'F')
				piece = F;
			
			board[offst[i]][j] = piece;
			k++;
		}
	}
}

/*---This function will update the board depending on the move you or your opponent played---*/
void BattleField :: UpdateBoard(string moveLine1, string moveLine2, int turn){
	int row1, col1, row2 ,col2;
	int piece1, piece2, myPiece, oppPiece;
	int index;
	
	row1 = moveLine1[0] - 'A';
	col1 = moveLine1[1] - '1';
     
	row2 = moveLine1[3] - 'A';
	col2 = moveLine1[4] - '1';
	
	if(moveLine2[0] == '*'){
		board[row2][col2] = board[row1][col1];
		board[row1][col1] = N;
		return ;
     }
     
	piece1 = GetPieceValue(moveLine2[0]);
	piece2 = GetPieceValue(moveLine2[2]);
     
	if(myPieceColor == RED){
		myPiece = piece2;
		oppPiece = piece1;
	}
	else{
		myPiece = piece1;
		oppPiece = piece2;
	}
     
	if(myPiece == oppPiece){
		board[row2][col2] = N;
		board[row1][col1] = N;
		index = GetIndex(myPiece);
		killedPieces[0][index]++;
		index = GetIndex(oppPiece);
		killedPieces[1][index]++;
	}
	else{
		if(CanKill(myPiece, oppPiece)){
			board[row1][col1] = N;
			board[row2][col2] = myPiece;
			index = GetIndex(oppPiece);
			killedPieces[1][index]++;
		}
		else{
			board[row1][col1] = N;
			board[row2][col2] = -oppPiece;
			index = GetIndex(myPiece);
			killedPieces[0][index]++;
		}
     }
}

int BattleField :: GetIndex(int piece){
	if(piece == 360)
		return 0;
	if(piece == 150)
		return 1;
	if(piece == 80)
		return 2;
	if(piece == 45)
		return 3;
	if(piece == 30)
		return 4;
	if(piece == 300)
		return 5;
	if(piece == 100)
		return 6;
}

int BattleField :: GetPieceValue(char ch){
	if(ch == 'G')
		return 360;
	if(ch == 'C')
		return 150;
	if(ch == 'L')
		return 80;
	if(ch == 'M')
		return 45;
	if(ch == 'R')
		return 30;
	if(ch == 'S')
		return 300;
	if(ch == 'B')
		return 100;
	if(ch == 'F')
		return 5000;
	if(ch == 'U')
		return -10;
	if(ch == 'N')
		return 0;
	if(ch == 'W')
		return 1;
	return -1;
}

/*---This function will check whether your piece can kill your opponent piece in case of an attack---*/
bool BattleField :: CanKill(int myPiece, int oppPiece){
	if(oppPiece == S){
		if(myPiece == G)
			return false;
		else if(myPiece == C && killedPieces[0][0] == 1)
			return false;
		else if(myPiece == L && killedPieces[0][0] == 1 && killedPieces[0][1] == 2)
			return false;
		else if(myPiece == M && killedPieces[0][0] == 1 && killedPieces[0][1] == 2 && killedPieces[0][2] == 3)
			return false;
		else if(myPiece == R && killedPieces[0][0] == 1 && killedPieces[0][1] == 2 && killedPieces[0][2] == 3 && killedPieces[0][3] == 4)
			return false;
		return true;
	}
	else if(myPiece == S){
		if(oppPiece == G)
			return true;
		else if(oppPiece == C && killedPieces[1][0] == 1)
			return true;
		else if(oppPiece == L && killedPieces[1][0] == 1 && killedPieces[1][1] == 2)
			return true;
		else if(oppPiece == M && killedPieces[1][0] == 1 && killedPieces[1][1] == 2 && killedPieces[1][2] == 3)
			return true;
		else if(oppPiece == R && killedPieces[1][0] == 1 && killedPieces[1][1] == 2 && killedPieces[1][2] == 3 && killedPieces[1][3] == 4)
			return true;
	}
	else if(myPiece == G && (oppPiece == C || oppPiece == L || oppPiece == M || oppPiece == R || oppPiece == S))
		return true;
	else if(myPiece == C && (oppPiece == L || oppPiece == M || oppPiece == R || oppPiece == S))
		return true;
	else if(myPiece == L && (oppPiece == M || oppPiece == R || oppPiece == S))
		return true;
	else if(myPiece == M && (oppPiece == R || oppPiece == S && oppPiece == B))
		return true;
	else if(myPiece == R && (oppPiece == S))
		return true;
	else if(myPiece == B && oppPiece != M)
		return true;
	return false;
}

/*-----This function will implement your strategy to play a move-----*/
string BattleField :: StrategicMove(int turn){
	/*IMPLEMENT YOUR STRATEGY HERE
	.
	.
	.
	*/
}

/*---This function will display board with pieces using their symbol---*/
void BattleField :: DisplayByPiece(){
	int i, j;
	char ch, row, col, chs;
	printf("\n");
	row = 'A';
	col = '1';
	for(i = 0; i < 7; i++)
		printf("\t%c", col++);
	printf("\n  ");
	for(i = 0; i < 56; i++)
		printf("-");
	printf("\nA |");
	for(i = 0; i < 7; i++){
		for(j = 0; j < 7; j++){
			if(fabs(board[i][j]) == G)
				ch = 'G';
			else if(fabs(board[i][j]) == C)
				ch = 'C';
			else if(fabs(board[i][j]) == L)
				ch = 'L';
			else if(fabs(board[i][j]) == M)
				ch = 'M';
			else if(fabs(board[i][j]) == R)
				ch = 'R';
			else if(fabs(board[i][j]) == S)
				ch = 'S';
			else if(fabs(board[i][j]) == B)
				ch = 'B';
			else if(fabs(board[i][j]) == F)
				ch = 'F';
			else if(fabs(board[i][j]) == W)
				ch = 'W';
			else if(board[i][j] == U)
				ch = 'U';
			else if(board[i][j] == N)
				ch = '0';
			
			chs = '+';
			if(board[i][j] == -G || board[i][j] == -C || board[i][j] == -L || board[i][j] == -M || board[i][j] == -R || board[i][j] == -S)
				chs = '-';
			printf("\t");
			if(chs == '-')
			printf("-");
			printf("%c", ch);
		}
		row++;
		if(i != 6)
			printf("\n  |\n%c |", row);
		else
			printf("\n\n");
	}
	printf("Killed Pieces : \tG\tC\tL\tM\tR\tS\tB\n");
	printf("         Mine : ");
	for(i = 0; i < 7; i++)
	printf("\t%d", killedPieces[0][i]);
	printf("\n    Opponents : ");
	for(i = 0; i < 7; i++)
	printf("\t%d", killedPieces[1][i]);
	
	printf("\n\n");
}

/* This function will display board with value of each piece instead of it's symbol */
void BattleField :: DisplayByPieceValue(){
	int i, j;
	char row, col;
	printf("\n           ");
	row = 'A';
	col = '1';
	for(i = 0; i < 7; i++)
		printf("%c       ", col++);
	printf("\n  ");
	for(i = 0; i < 58; i++)
		printf("-");
	printf("\nA |");
	
	for(i = 0; i < 7; i++){
		for(j = 0; j < 7; j++)
			printf("\t%4d", board[i][j]);
		row++;
		if(i != 6)
			printf("\n  |\n%c |", row);
		else
			printf("\n\n");
	}
	printf("Killed Pieces : \tG\tC\tL\tM\tR\tS\tB\n");
	printf("         Mine : ");
	for(i = 0; i < 7; i++)
	printf("\t%d", killedPieces[0][i]);
	printf("\n    Opponents : ");
	for(i = 0; i < 7; i++)
	printf("\t%d", killedPieces[1][i]);
	
	printf("\n\n");
}

int main(){
	string myMove;
	string opponentMoveLine1, opponentMoveLine2;
	string initConfig = "";		//---Your Initial Pieces Position String
	int turn = RED;
	BattleField myGame;
	
	/* Send your initial peices configuration string to judge */ 
	cout << initConfig << endl;
	fflush(stdout);
	
	getline(cin, opponentMoveLine1);		//----First line of input ex: C4-D4 (or) START
	getline(cin, opponentMoveLine2);		//---Second line of input ex: M L (or) * *
	if(opponentMoveLine1 == "START"){ 
		/* START signal send by judge. You are RED */
		myPieceColor = RED;
		/* Set your pieces */
		myGame.StrategicBoardIntialize(initConfig, turn);
		
		/* Play your first move */
		myMove = myGame.StrategicMove(turn);
		cout << myMove << endl;
		fflush(stdout);
		
		/* Judge reply for your move */
		getline(cin, opponentMoveLine1);
		getline(cin, opponentMoveLine2);
		myGame.UpdateBoard(opponentMoveLine1, opponentMoveLine2, turn);
		
		/* Judge reply for opponent move */
		getline(cin, opponentMoveLine1);
		getline(cin, opponentMoveLine2);
		turn= -turn;
	}
	else{
		/* You are BLUE */
		myPieceColor = BLUE;
		/* Set your pieces */
		myGame.StrategicBoardIntialize(initConfig, -turn);
	}
    
	while(true) {
		/* first update the board with opponent move*/
		myGame.UpdateBoard(opponentMoveLine1, opponentMoveLine2, turn);
		
		turn = -turn;
		/* Play your  move */
		myMove = myGame.StrategicMove(turn);
		
		cout << myMove << endl;
		fflush(stdout);
		
		/* Judge reply for your move */
		getline(cin, opponentMoveLine1);
		getline(cin, opponentMoveLine2);
		myGame.UpdateBoard(opponentMoveLine1, opponentMoveLine2, turn);
		
		/* Judge reply for opponent move */
		getline(cin, opponentMoveLine1);
		getline(cin, opponentMoveLine2);
		turn = -turn;
	}
    return 0;
}
