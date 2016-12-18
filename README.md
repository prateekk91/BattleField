# BattleField

The name of the game is BattleField.
Battle Field Judge

Works fine on ubuntu and Redhat.

Requirements:
-JRE(Java Runtime Environment)
-gcc/g++


HOW TO RUN :
1. You need to write a C++ player for the game. A couple of samples are present in the directory.

2.Compile your code.

3.Run the shell script run.sh
bash run.sh './path_of_player1_objectfile' './path_of_player2_objectfile'

e.g.
$ cd battle_field
$ g++ sample_player.cpp -o p1
$ g++ sample_player_again.cpp -o p2
$ bash run.sh './p1' './p2'

After few seconds you will be able to see window of our Judge showing the game.
Finally you can check the moves played during the game in Moves_out file in the same folder.


In case their is some problem with the judge, try killing these processes then rerunning it.
java
client
<name_of_player1_objectfile>
<name_of_player2_objectfile>

If you want to play a match on LAN then :

Host the server using :
$ java Main
Now on each client's(player's/coder's) computer do :
$ ./client './name_of_player's_objectfile' <IP_address_of_server>

I have provided 3 sample player object files, namely 'player1', 'player2' , 'player3'.that you can test on the judge and see how they run. They all are generating random valid moves, they are just to give you a better visiblity of the judge and the moves.

In case you have any further doubts, mail your queries to 
prateekkhurana2006@gmail.com
