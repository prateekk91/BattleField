#!/bin/bash
if (( $# != 2)); then
echo "<ERROR> Usage: bash run.sh path_of_player1 path_of_player2"
exit;
fi

java Main > Moves_out 2>>Moves_out &
./client "$1" 0.0.0.0  &
sleep 2;
./client "$2" 0.0.0.0  &
