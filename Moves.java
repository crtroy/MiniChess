import java.util.Arrays;

public class Moves {

    /**
     pawnMoves::
     Input:
     int[] pieces: This will either be whitePieces[] or blackPieces[]
     int[] board: This will be the board[]
     Output:
     int[]moves: Array to output all possible moves
     [0] = the number of total moves. Used for traversing array after return.
     [1-21] = moves available to the pawns
     Every 2 array position after will contain the
     "from" and the "to" positions that a pawn can
     legally move on the board.
     [1][3][5][7][9][11]...  = "from" positions
     [2][4][6][8][10][12]... = "to" positions
     Other:
     int j is used to cycle through the moves[] array
     int i is used to cycle through the five pawns
     **/
    static int[] pawnMoves(int[] pieces, int[] board, char player) {
        // moves[] holds all available moves for a turn
        // Size is determined by pieces
        // multiply possible moves by 2 since we need "from" and "to" values
        //      moveCount   = 1
        //      moves       = 26
        int[] moves = new int[28];
        int j = 1;
        if(player == 'W') {
            for (int i = 0; i < 5; i++) {
                //pawns
                if (i < 5) {
                    if (pieces[i] != -1 && pieces[i] > 4) {
                        // For pawns in these columns
                        // attacking right
                        //|  .  .  .  .  . |
                        //|  P  P  P  P  . |
                        //|  .  .  .  .  . |
                        if (pieces[i] % 5 != 4) {
                            // Check for attack to upper right
                            // x represents target destination
                            //|  .  .  .  .  . |
                            //|  .  x  x  x  x |
                            //|  P  P  P  P  . |
                            if ((board[pieces[i] - 4]) % 10 == 2) {
                                moves[j] = pieces[i];
                                moves[j + 1] = pieces[i] - 4;
                                j = j + 2;
                                moves[0]++;
                            }
                        }
                        // For pawns in these columns
                        // attacking left
                        //|  .  .  .  .  . |
                        //|  .  P  P  P  P |
                        //|  .  .  .  .  . |
                        if (pieces[i] % 5 != 0) {
                            // Check for attack to upper right
                            // x represents target destination
                            //|  .  .  .  .  . |
                            //|  x  x  x  x  . |
                            //|  .  P  P  P  P |
                            if ((board[pieces[i] - 6]) % 10 == 2) {
                                moves[j] = pieces[i];
                                moves[j + 1] = pieces[i] - 6;
                                j = j + 2;
                                moves[0]++;
                            }
                        }
                        // For pawns in these columns
                        // moving forward
                        //|  .  .  .  .  . |
                        //|  P  P  P  P  P |
                        //|  .  .  .  .  . |
                        if (board[pieces[i] - 5] == 0) {
                            moves[j] = pieces[i];
                            moves[j + 1] = pieces[i] - 5;
                            j = j + 2;
                            moves[0]++;
                        }
                    }
                }
            }
        }
        else{
            for (int i = 0; i < 5; i++) {
                if (pieces[i] != -1 && pieces[i] < 25) {
                    // For pawns in these columns
                    // attacking right
                    //|  .  .  .  .  . |
                    //|  p  p  p  p  . |
                    //|  .  .  .  .  . |
                    if (pieces[i] % 5 != 4) {
                        // Check for attack to lower right
                        // x represents target destination
                        //|  P  P  P  P  . |
                        //|  .  x  x  x  x |
                        //|  .  .  .  .  . |
                        if ((board[pieces[i] + 6]) % 10 == 1) {
                            moves[j] = pieces[i];
                            moves[j + 1] = pieces[i] + 6;
                            j = j + 2;
                            moves[0]++;
                        }
                    }
                    // For pawns in these columns
                    // attacking left
                    //|  .  .  .  .  . |
                    //|  .  p  p  p  p |
                    //|  .  .  .  .  . |
                    if (pieces[i] % 5 != 0) {
                        // Check for attack to upper right
                        // x represents target destination
                        //|  .  p  p  p  p |
                        //|  x  x  x  x  . |
                        //|  .  .  .  .  . |
                        if ((board[pieces[i] + 4]) % 10 == 1) {
                            moves[j] = pieces[i];
                            moves[j + 1] = pieces[i] + 4;
                            j = j + 2;
                            moves[0]++;
                        }
                    }
                    // For pawns in these columns
                    // moving forward
                    //|  .  .  .  .  . |
                    //|  p  p  p  p  p |
                    //|  .  .  .  .  . |
                    if (board[pieces[i] + 5] == 0) {
                        moves[j] = pieces[i];
                        moves[j + 1] = pieces[i] + 5;
                        j = j + 2;
                        moves[0]++;
                    }
                }
            }

        }
        return moves;
    }

    static int[] rookMoves(int[] pieces, int[] board, char player){
        // moves[] holds all available moves for a turn
        // Size is determined by pieces
        // multiply possible moves by 2 since we need "from" and "to" values
        //Most active position example:
        //|  .  .  D  .  . |
        //|  .  .  D  .  . |
        //|  D  D  R  D  D |
        //|  .  .  D  .  . |
        //|  .  .  D  .  . |
        //|  .  .  D  .  . |
        //      moveCount   = 1
        //      moves       = 18
        int[] moves = new int[20];
        int j = 1;
        int playerNumber;

        if (player == 'W')
            playerNumber = 1;
        else
            playerNumber = 2;

///////////////////////////////////////////////////////////////////
///////////////          Search Left          /////////////////////
///////////////////////////////////////////////////////////////////
        if(pieces[5] != -1) {
            for (int i = 1; i < 5; i++) {
                //Must be at the left side of the board or another of your pieces is to
                //your left.
                //No possible move to the left.
                // i = 5 to end this search
                //|  .  .  .  .  . |            |  .  .  .  .  . |
                //|  R  .  .  .  . |     OR     |  . RD RD RD  R |
                //|  .  .  .  .  . |            |  .  .  .  .  . |
                if (pieces[5] == 0 || (pieces[5] - i) % 5 == 4 || board[pieces[5] - i] % 10 % 10  == playerNumber) {
                    i = 5;
                }
                //last move to the left you can make
                //left side of board
                // i = 5 to end this search
                //|  .  .  .  .  . |
                //|  D  R  .  .  . |
                //|  .  .  .  .  . |
                else if ((pieces[5] - i) % 5 == 0) {
                    moves[j] = pieces[5];
                    moves[j + 1] = pieces[5] - i;
                    j = j + 2;
                    moves[0]++;
                    i = 5;
                }
                //normal move to the left
                //|  .  .  .  .  . |
                //|  D  D RD RD  R |
                //|  .  .  .  .  . |
                else if (board[pieces[5] - i] == 0) {
                    moves[j] = pieces[5];
                    moves[j + 1] = pieces[5] - i;
                    j = j + 2;
                    moves[0]++;
                }
                //only other option is opponent in left position
                //move to this position and set i to 5 to end move since it's
                //the last position you will be able to move.
                // i = 5 to end this search
                //|  .  .  .  .  . |
                //|  D RD RD RD  R |
                //|  .  .  .  .  . |
                else {
                    moves[j] = pieces[5];
                    moves[j + 1] = pieces[5] - i;
                    j = j + 2;
                    moves[0]++;
                    i = 5;
                }
            }

///////////////////////////////////////////////////////////////////
///////////////          Search Right          ////////////////////
///////////////////////////////////////////////////////////////////

            for (int i = 1; i < 5; i++) {
                //Must be at the right side of the board or another of your pieces is to
                //your right.
                //No possible move to the right.
                //i = 5 to end this search
                //|  .  .  .  .  . |            |  .  .  .  .  . |
                //|  .  .  .  .  R |     OR     |  R RD RD RD  . |
                //|  .  .  .  .  . |            |  .  .  .  .  . |
                if ((pieces[5] + i) % 5 == 0 || board[pieces[5] + i] % 10 % 10  == playerNumber) {
                    i = 5;
                }
                //last move to the right you can make
                //right side of board
                // i = 5 to end this search
                //|  .  .  .  .  . |
                //|  .  .  .  R  D |
                //|  .  .  .  .  . |
                else if ((pieces[5] + i) % 5 == 4) {
                    moves[j] = pieces[5];
                    moves[j + 1] = pieces[5] + i;
                    j = j + 2;
                    moves[0]++;
                    i = 5;
                }
                //normal move to the right
                //|  .  .  .  .  . |
                //|  R RD RD  D  D |
                //|  .  .  .  .  . |
                else if (board[pieces[5] + i] == 0) {
                    moves[j] = pieces[5];
                    moves[j + 1] = pieces[5] + i;
                    j = j + 2;
                    moves[0]++;
                }
                //only other option is opponent in right position
                //move to this position and set i to 5 to end move since it's
                //the last position you will be able to move.
                // i = 5 to end this search
                //|  .  .  .  .  . |
                //|  R RD RD RD  D |
                //|  .  .  .  .  . |
                else {
                    moves[j] = pieces[5];
                    moves[j + 1] = pieces[5] + i;
                    j = j + 2;
                    moves[0]++;
                    i = 5;
                }
            }

///////////////////////////////////////////////////////////////////
///////////////          Search Up          ///////////////////////
///////////////////////////////////////////////////////////////////

            for (int i = 1; i < 6; i++) {
                //Must be at the top of the board or another of your pieces is above you
                //No possible move up.
                //Set i to 6 to end move
                //|  .  .  R  .  . |            |  .  .  .  .  . |
                //|  .  .  .  .  . |     OR     |  .  . RD  .  . |
                //|  .  .  .  .  . |            |  .  . RD  .  . |
                //|  .  .  .  .  . |            |  .  . RD  .  . |
                //|  .  .  .  .  . |            |  .  . RD  .  . |
                //|  .  .  .  .  . |            |  .  . R   .  . |
                if ((pieces[5] - ((i - 1) * 5)) / 5 == 0 || board[pieces[5] - (i * 5)] % 10 % 10  == playerNumber) {
                    i = 6;
                }
                //normal move up
                //|  .  .  D  .  . |
                //|  .  . RD  .  . |
                //|  .  . RD  .  . |
                //|  .  . RD  .  . |
                //|  .  . RD  .  . |
                //|  .  . R   .  . |
                else if (board[pieces[5] - (i * 5)] == 0) {
                    moves[j] = pieces[5];
                    moves[j + 1] = pieces[5] - (i * 5);
                    j = j + 2;
                    moves[0]++;
                }
                //only other option is opponent directly above
                //move to this position and set i to 6 to end move
                //|  .  .  D  .  . |
                //|  .  . RD  .  . |
                //|  .  . RD  .  . |
                //|  .  . RD  .  . |
                //|  .  . RD  .  . |
                //|  .  . R   .  . |
                else {
                    moves[j] = pieces[5];
                    moves[j + 1] = pieces[5] - (i * 5);
                    j = j + 2;
                    moves[0]++;
                    i = 6;
                }
            }
///////////////////////////////////////////////////////////////////
///////////////          Search Down        ///////////////////////
///////////////////////////////////////////////////////////////////

            for (int i = 1; i < 6; i++) {
                //Must be at the bottom of the board or another of your pieces is below you
                //No possible move down.
                //Set i to 6 to end move
                //|  .  .  .  .  . |            |  .  .  R  .  . |
                //|  .  .  .  .  . |     OR     |  .  . RD  .  . |
                //|  .  .  .  .  . |            |  .  . RD  .  . |
                //|  .  .  .  .  . |            |  .  . RD  .  . |
                //|  .  .  .  .  . |            |  .  . RD  .  . |
                //|  .  .  R  .  . |            |  .  .  .  .  . |
                if ((pieces[5] + ((i - 1) * 5)) / 5 == 5 || board[pieces[5] + (i * 5)] % 10 % 10  == playerNumber) {
                    i = 6;
                }
                //normal move down
                //|  .  . R   .  . |
                //|  .  . RD  .  . |
                //|  .  . RD  .  . |
                //|  .  . RD  .  . |
                //|  .  . RD  .  . |
                //|  .  .  D  .  . |
                else if (board[pieces[5] + (i * 5)] == 0) {
                    moves[j] = pieces[5];
                    moves[j + 1] = pieces[5] + (i * 5);
                    j = j + 2;
                    moves[0]++;
                }
                //only other option is opponent directly below
                //move to this position and set i to 6 to end move
                //|  .  . R   .  . |
                //|  .  . RD  .  . |
                //|  .  . RD  .  . |
                //|  .  . RD  .  . |
                //|  .  . RD  .  . |
                //|  .  .  D  .  . |
                else {
                    moves[j] = pieces[5];
                    moves[j + 1] = pieces[5] + (i * 5);
                    j = j + 2;
                    moves[0]++;
                    i = 6;
                }
            }
        }
        //System.out.print("\n" + moves[0]);
        return moves;
    }

    static int[] knightMoves(int[] pieces, int[] board, char player) {
        // moves[] holds all available moves for a turn
        // Size is determined by pieces
        // multiply possible moves by 2 since we need "from" and "to" values
        // multiply possible moves by 2 since we need "from" and "to" values
        //Most active position example:
        //|  .  D  .  D  . |
        //|  D  .  .  .  D |
        //|  .  .  K  .  . |
        //|  D  .  .  .  D |
        //|  .  D  .  D  . |
        //|  .  .  .  .  . |
        //      moveCount   = 1
        //      moves       = 16
        int[] moves = new int[18];
        int j = 1;
        int playerNumber;

        if (player == 'W')
            playerNumber = 1;
        else
            playerNumber = 2;

        if (pieces[6] == 0) {
            if ((board[7]) % 10 != playerNumber) {
                moves[j] = 0;
                moves[j + 1] = 7;
                j = j + 2;
                moves[0]++;
            }
            if ((board[11]) % 10 != playerNumber) {
                moves[j] = 0;
                moves[j + 1] = 11;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 1) {
            if ((board[8]) % 10 != playerNumber) {
                moves[j] = 1;
                moves[j + 1] = 8;
                j = j + 2;
                moves[0]++;
            }
            if ((board[10]) % 10 != playerNumber) {
                moves[j] = 1;
                moves[j + 1] = 10;
                j = j + 2;
                moves[0]++;
            }
            if ((board[12]) % 10 != playerNumber) {
                moves[j] = 1;
                moves[j + 1] = 12;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 2) {
            if ((board[5]) % 10 != playerNumber) {
                moves[j] = 2;
                moves[j + 1] = 5;
                j = j + 2;
                moves[0]++;
            }
            if ((board[9]) % 10 != playerNumber) {
                moves[j] = 2;
                moves[j + 1] = 9;
                j = j + 2;
                moves[0]++;
            }
            if ((board[11]) % 10 != playerNumber) {
                moves[j] = 2;
                moves[j + 1] = 11;
                j = j + 2;
                moves[0]++;
            }
            if ((board[13]) % 10 != playerNumber) {
                moves[j] = 2;
                moves[j + 1] = 13;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 3) {
            if ((board[6]) % 10 != playerNumber) {
                moves[j] = 3;
                moves[j + 1] = 6;
                j = j + 2;
                moves[0]++;
            }
            if ((board[12]) % 10 != playerNumber) {
                moves[j] = 3;
                moves[j + 1] = 12;
                j = j + 2;
                moves[0]++;
            }
            if ((board[14]) % 10 != playerNumber) {
                moves[j] = 3;
                moves[j + 1] = 14;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 4) {
            if ((board[7]) % 10 != playerNumber) {
                moves[j] = 4;
                moves[j + 1] = 7;
                j = j + 2;
                moves[0]++;
            }
            if ((board[13]) % 10 != playerNumber) {
                moves[j] = 4;
                moves[j + 1] = 13;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 5) {
            if ((board[2]) % 10 != playerNumber) {
                moves[j] = 5;
                moves[j + 1] = 2;
                j = j + 2;
                moves[0]++;
            }
            if ((board[12]) % 10 != playerNumber) {
                moves[j] = 5;
                moves[j + 1] = 12;
                j = j + 2;
                moves[0]++;
            }
            if ((board[16]) % 10 != playerNumber) {
                moves[j] = 5;
                moves[j + 1] = 16;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 6) {
            if ((board[3]) % 10 != playerNumber) {
                moves[j] = 6;
                moves[j + 1] = 3;
                j = j + 2;
                moves[0]++;
            }
            if ((board[13]) % 10 != playerNumber) {
                moves[j] = 6;
                moves[j + 1] = 13;
                j = j + 2;
                moves[0]++;
            }
            if ((board[15]) % 10 != playerNumber) {
                moves[j] = 6;
                moves[j + 1] = 15;
                j = j + 2;
                moves[0]++;
            }
            if ((board[17]) % 10 != playerNumber) {
                moves[j] = 6;
                moves[j + 1] = 17;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 7) {
            if ((board[0]) % 10 != playerNumber) {
                moves[j] = 7;
                moves[j + 1] = 0;
                j = j + 2;
                moves[0]++;
            }
            if ((board[4]) % 10 != playerNumber) {
                moves[j] = 7;
                moves[j + 1] = 4;
                j = j + 2;
                moves[0]++;
            }
            if ((board[10]) % 10 != playerNumber) {
                moves[j] = 7;
                moves[j + 1] = 10;
                j = j + 2;
                moves[0]++;
            }
            if ((board[14]) % 10 != playerNumber) {
                moves[j] = 7;
                moves[j + 1] = 14;
                j = j + 2;
                moves[0]++;
            }
            if ((board[16]) % 10 != playerNumber) {
                moves[j] = 7;
                moves[j + 1] = 16;
                j = j + 2;
                moves[0]++;
            }
            if ((board[18]) % 10 != playerNumber) {
                moves[j] = 7;
                moves[j + 1] = 18;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 8) {
            if ((board[1]) % 10 != playerNumber) {
                moves[j] = 8;
                moves[j + 1] = 1;
                j = j + 2;
                moves[0]++;
            }
            if ((board[11]) % 10 != playerNumber) {
                moves[j] = 8;
                moves[j + 1] = 11;
                j = j + 2;
                moves[0]++;
            }
            if ((board[17]) % 10 != playerNumber) {
                moves[j] = 8;
                moves[j + 1] = 17;
                j = j + 2;
                moves[0]++;
            }
            if ((board[19]) % 10 != playerNumber) {
                moves[j] = 8;
                moves[j + 1] = 19;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 9) {
            if ((board[2]) % 10 != playerNumber) {
                moves[j] = 9;
                moves[j + 1] = 2;
                j = j + 2;
                moves[0]++;
            }
            if ((board[12]) % 10 != playerNumber) {
                moves[j] = 9;
                moves[j + 1] = 12;
                j = j + 2;
                moves[0]++;
            }
            if ((board[18]) % 10 != playerNumber) {
                moves[j] = 9;
                moves[j + 1] = 18;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 10) {
            if ((board[1]) % 10 != playerNumber) {
                moves[j] = 10;
                moves[j + 1] = 1;
                j = j + 2;
                moves[0]++;
            }
            if ((board[7]) % 10 != playerNumber) {
                moves[j] = 10;
                moves[j + 1] = 7;
                j = j + 2;
                moves[0]++;
            }
            if ((board[17]) % 10 != playerNumber) {
                moves[j] = 10;
                moves[j + 1] = 17;
                j = j + 2;
                moves[0]++;
            }
            if ((board[21]) % 10 != playerNumber) {
                moves[j] = 10;
                moves[j + 1] = 21;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 11) {
            if ((board[0]) % 10 != playerNumber) {
                moves[j] = 11;
                moves[j + 1] = 0;
                j = j + 2;
                moves[0]++;
            }
            if ((board[2]) % 10 != playerNumber) {
                moves[j] = 11;
                moves[j + 1] = 2;
                j = j + 2;
                moves[0]++;
            }
            if ((board[8]) % 10 != playerNumber) {
                moves[j] = 11;
                moves[j + 1] = 8;
                j = j + 2;
                moves[0]++;
            }
            if ((board[18]) % 10 != playerNumber) {
                moves[j] = 11;
                moves[j + 1] = 18;
                j = j + 2;
                moves[0]++;
            }
            if ((board[20]) % 10 != playerNumber) {
                moves[j] = 11;
                moves[j + 1] = 20;
                j = j + 2;
                moves[0]++;
            }
            if ((board[22]) % 10 != playerNumber) {
                moves[j] = 11;
                moves[j + 1] = 22;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 12) {
            if ((board[1]) % 10 != playerNumber) {
                moves[j] = 12;
                moves[j + 1] = 1;
                j = j + 2;
                moves[0]++;
            }
            if ((board[3]) % 10 != playerNumber) {
                moves[j] = 12;
                moves[j + 1] = 3;
                j = j + 2;
                moves[0]++;
            }
            if ((board[5]) % 10 != playerNumber) {
                moves[j] = 12;
                moves[j + 1] = 5;
                j = j + 2;
                moves[0]++;
            }
            if ((board[9]) % 10 != playerNumber) {
                moves[j] = 12;
                moves[j + 1] = 9;
                j = j + 2;
                moves[0]++;
            }
            if ((board[15]) % 10 != playerNumber) {
                moves[j] = 12;
                moves[j + 1] = 15;
                j = j + 2;
                moves[0]++;
            }
            if ((board[19]) % 10 != playerNumber) {
                moves[j] = 12;
                moves[j + 1] = 19;
                j = j + 2;
                moves[0]++;
            }
            if ((board[21]) % 10 != playerNumber) {
                moves[j] = 12;
                moves[j + 1] = 21;
                j = j + 2;
                moves[0]++;
            }
            if ((board[23]) % 10 != playerNumber) {
                moves[j] = 12;
                moves[j + 1] = 23;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 13) {
            if ((board[2]) % 10 != playerNumber) {
                moves[j] = 13;
                moves[j + 1] = 2;
                j = j + 2;
                moves[0]++;
            }
            if ((board[4]) % 10 != playerNumber) {
                moves[j] = 13;
                moves[j + 1] = 4;
                j = j + 2;
                moves[0]++;
            }
            if ((board[6]) % 10 != playerNumber) {
                moves[j] = 13;
                moves[j + 1] = 6;
                j = j + 2;
                moves[0]++;
            }
            if ((board[16]) % 10 != playerNumber) {
                moves[j] = 13;
                moves[j + 1] = 16;
                j = j + 2;
                moves[0]++;
            }
            if ((board[22]) % 10 != playerNumber) {
                moves[j] = 13;
                moves[j + 1] = 22;
                j = j + 2;
                moves[0]++;
            }
            if ((board[24]) % 10 != playerNumber) {
                moves[j] = 13;
                moves[j + 1] = 24;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 14) {
            if ((board[3]) % 10 != playerNumber) {
                moves[j] = 14;
                moves[j + 1] = 3;
                j = j + 2;
                moves[0]++;
            }
            if ((board[7]) % 10 != playerNumber) {
                moves[j] = 14;
                moves[j + 1] = 7;
                j = j + 2;
                moves[0]++;
            }
            if ((board[17]) % 10 != playerNumber) {
                moves[j] = 14;
                moves[j + 1] = 17;
                j = j + 2;
                moves[0]++;
            }
            if ((board[23]) % 10 != playerNumber) {
                moves[j] = 14;
                moves[j + 1] = 23;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 15) {
            if ((board[6]) % 10 != playerNumber) {
                moves[j] = 15;
                moves[j + 1] = 6;
                j = j + 2;
                moves[0]++;
            }
            if ((board[12]) % 10 != playerNumber) {
                moves[j] = 15;
                moves[j + 1] = 12;
                j = j + 2;
                moves[0]++;
            }
            if ((board[22]) % 10 != playerNumber) {
                moves[j] = 15;
                moves[j + 1] = 22;
                j = j + 2;
                moves[0]++;
            }
            if ((board[26]) % 10 != playerNumber) {
                moves[j] = 15;
                moves[j + 1] = 26;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 16) {
            if ((board[5]) % 10 != playerNumber) {
                moves[j] = 16;
                moves[j + 1] = 5;
                j = j + 2;
                moves[0]++;
            }
            if ((board[7]) % 10 != playerNumber) {
                moves[j] = 16;
                moves[j + 1] = 7;
                j = j + 2;
                moves[0]++;
            }
            if ((board[13]) % 10 != playerNumber) {
                moves[j] = 16;
                moves[j + 1] = 13;
                j = j + 2;
                moves[0]++;
            }
            if ((board[23]) % 10 != playerNumber) {
                moves[j] = 16;
                moves[j + 1] = 23;
                j = j + 2;
                moves[0]++;
            }
            if ((board[25]) % 10 != playerNumber) {
                moves[j] = 16;
                moves[j + 1] = 25;
                j = j + 2;
                moves[0]++;
            }
            if ((board[27]) % 10 != playerNumber) {
                moves[j] = 16;
                moves[j + 1] = 27;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 17) {
            if ((board[6]) % 10 != playerNumber) {
                moves[j] = 17;
                moves[j + 1] = 6;
                j = j + 2;
                moves[0]++;
            }
            if ((board[8]) % 10 != playerNumber) {
                moves[j] = 17;
                moves[j + 1] = 8;
                j = j + 2;
                moves[0]++;
            }
            if ((board[10]) % 10 != playerNumber) {
                moves[j] = 17;
                moves[j + 1] = 10;
                j = j + 2;
                moves[0]++;
            }
            if ((board[14]) % 10 != playerNumber) {
                moves[j] = 17;
                moves[j + 1] = 14;
                j = j + 2;
                moves[0]++;
            }
            if ((board[20]) % 10 != playerNumber) {
                moves[j] = 17;
                moves[j + 1] = 20;
                j = j + 2;
                moves[0]++;
            }
            if ((board[24]) % 10 != playerNumber) {
                moves[j] = 17;
                moves[j + 1] = 24;
                j = j + 2;
                moves[0]++;
            }
            if ((board[26]) % 10 != playerNumber) {
                moves[j] = 17;
                moves[j + 1] = 26;
                j = j + 2;
                moves[0]++;
            }
            if ((board[28]) % 10 != playerNumber) {
                moves[j] = 17;
                moves[j + 1] = 28;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 18) {
            if ((board[7]) % 10 != playerNumber) {
                moves[j] = 18;
                moves[j + 1] = 7;
                j = j + 2;
                moves[0]++;
            }
            if ((board[9]) % 10 != playerNumber) {
                moves[j] = 18;
                moves[j + 1] = 9;
                j = j + 2;
                moves[0]++;
            }
            if ((board[11]) % 10 != playerNumber) {
                moves[j] = 18;
                moves[j + 1] = 11;
                j = j + 2;
                moves[0]++;
            }
            if ((board[21]) % 10 != playerNumber) {
                moves[j] = 18;
                moves[j + 1] = 21;
                j = j + 2;
                moves[0]++;
            }
            if ((board[27]) % 10 != playerNumber) {
                moves[j] = 18;
                moves[j + 1] = 27;
                j = j + 2;
                moves[0]++;
            }
            if ((board[29]) % 10 != playerNumber) {
                moves[j] = 18;
                moves[j + 1] = 29;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 19) {
            if ((board[8]) % 10 != playerNumber) {
                moves[j] = 19;
                moves[j + 1] = 8;
                j = j + 2;
                moves[0]++;
            }
            if ((board[12]) % 10 != playerNumber) {
                moves[j] = 19;
                moves[j + 1] = 12;
                j = j + 2;
                moves[0]++;
            }
            if ((board[22]) % 10 != playerNumber) {
                moves[j] = 19;
                moves[j + 1] = 22;
                j = j + 2;
                moves[0]++;
            }
            if ((board[28]) % 10 != playerNumber) {
                moves[j] = 19;
                moves[j + 1] = 28;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 20) {
            if ((board[11]) % 10 != playerNumber) {
                moves[j] = 20;
                moves[j + 1] = 11;
                j = j + 2;
                moves[0]++;
            }
            if ((board[17]) % 10 != playerNumber) {
                moves[j] = 20;
                moves[j + 1] = 17;
                j = j + 2;
                moves[0]++;
            }
            if ((board[27]) % 10 != playerNumber) {
                moves[j] = 20;
                moves[j + 1] = 27;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 21) {
            if ((board[10]) % 10 != playerNumber) {
                moves[j] = 21;
                moves[j + 1] = 10;
                j = j + 2;
                moves[0]++;
            }
            if ((board[12]) % 10 != playerNumber) {
                moves[j] = 21;
                moves[j + 1] = 12;
                j = j + 2;
                moves[0]++;
            }
            if ((board[18]) % 10 != playerNumber) {
                moves[j] = 21;
                moves[j + 1] = 18;
                j = j + 2;
                moves[0]++;
            }
            if ((board[28]) % 10 != playerNumber) {
                moves[j] = 21;
                moves[j + 1] = 28;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 22) {
            if ((board[11]) % 10 != playerNumber) {
                moves[j] = 22;
                moves[j + 1] = 11;
                j = j + 2;
                moves[0]++;
            }
            if ((board[13]) % 10 != playerNumber) {
                moves[j] = 22;
                moves[j + 1] = 13;
                j = j + 2;
                moves[0]++;
            }
            if ((board[15]) % 10 != playerNumber) {
                moves[j] = 22;
                moves[j + 1] = 15;
                j = j + 2;
                moves[0]++;
            }
            if ((board[19]) % 10 != playerNumber) {
                moves[j] = 22;
                moves[j + 1] = 19;
                j = j + 2;
                moves[0]++;
            }
            if ((board[25]) % 10 != playerNumber) {
                moves[j] = 22;
                moves[j + 1] = 25;
                j = j + 2;
                moves[0]++;
            }
            if ((board[29]) % 10 != playerNumber) {
                moves[j] = 22;
                moves[j + 1] = 29;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 23) {
            if ((board[12]) % 10 != playerNumber) {
                moves[j] = 23;
                moves[j + 1] = 12;
                j = j + 2;
                moves[0]++;
            }
            if ((board[14]) % 10 != playerNumber) {
                moves[j] = 23;
                moves[j + 1] = 14;
                j = j + 2;
                moves[0]++;
            }
            if ((board[16]) % 10 != playerNumber) {
                moves[j] = 23;
                moves[j + 1] = 16;
                j = j + 2;
                moves[0]++;
            }
            if ((board[26]) % 10 != playerNumber) {
                moves[j] = 23;
                moves[j + 1] = 26;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 24) {
            if ((board[13]) % 10 != playerNumber) {
                moves[j] = 24;
                moves[j + 1] = 13;
                j = j + 2;
                moves[0]++;
            }
            if ((board[17]) % 10 != playerNumber) {
                moves[j] = 24;
                moves[j + 1] = 17;
                j = j + 2;
                moves[0]++;
            }
            if ((board[27]) % 10 != playerNumber) {
                moves[j] = 24;
                moves[j + 1] = 27;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 25) {
            if ((board[16]) % 10 != playerNumber) {
                moves[j] = 25;
                moves[j + 1] = 16;
                j = j + 2;
                moves[0]++;
            }
            if ((board[22]) % 10 != playerNumber) {
                moves[j] = 25;
                moves[j + 1] = 22;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 26) {
            if ((board[15]) % 10 != playerNumber) {
                moves[j] = 26;
                moves[j + 1] = 15;
                j = j + 2;
                moves[0]++;
            }
            if ((board[17]) % 10 != playerNumber) {
                moves[j] = 26;
                moves[j + 1] = 17;
                j = j + 2;
                moves[0]++;
            }
            if ((board[23]) % 10 != playerNumber) {
                moves[j] = 26;
                moves[j + 1] = 23;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 27) {
            if ((board[16]) % 10 != playerNumber) {
                moves[j] = 27;
                moves[j + 1] = 16;
                j = j + 2;
                moves[0]++;
            }
            if ((board[18]) % 10 != playerNumber) {
                moves[j] = 27;
                moves[j + 1] = 18;
                j = j + 2;
                moves[0]++;
            }
            if ((board[20]) % 10 != playerNumber) {
                moves[j] = 27;
                moves[j + 1] = 20;
                j = j + 2;
                moves[0]++;
            }
            if ((board[24]) % 10 != playerNumber) {
                moves[j] = 27;
                moves[j + 1] = 24;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 28) {
            if ((board[17]) % 10 != playerNumber) {
                moves[j] = 28;
                moves[j + 1] = 17;
                j = j + 2;
                moves[0]++;
            }
            if ((board[19]) % 10 != playerNumber) {
                moves[j] = 28;
                moves[j + 1] = 19;
                j = j + 2;
                moves[0]++;
            }
            if ((board[21]) % 10 != playerNumber) {
                moves[j] = 28;
                moves[j + 1] = 21;
                moves[0]++;
            }
            return moves;
        }
        if (pieces[6] == 29) {
            if ((board[18]) % 10 != playerNumber) {
                moves[j] = 29;
                moves[j + 1] = 18;
                j = j + 2;
                moves[0]++;
            }
            if ((board[22]) % 10 != playerNumber) {
                moves[j] = 29;
                moves[j + 1] = 22;
                moves[0]++;
            }
            return moves;
        }
        return moves;
    }

    static int[] bishopMoves(int[] pieces, int[] board, char player){
        // moves[] holds all available moves for a turn
        // Size is determined by pieces
        // multiply possible moves by 2 since we need "from" and "to" values
        //Most active position example:
        //|  D  .  .  .  D |
        //|  .  D  D  D  . |
        //|  .  D  B  D  . |
        //|  .  D  D  D  . |
        //|  D  .  .  .  D |
        //|  .  .  .  .  . |
        //      moveCount   = 1
        //      moves       = 24

        int[] moves = new int[26];
        int j = 1;
        int playerNumber;

        if (player == 'W')
            playerNumber = 1;
        else
            playerNumber = 2;

        //check to see if piece is still alive
        if(pieces[7] != -1){
/////////////////////////////////////////////////////////////////////////////////
/////////////////////////////   Pacifist Moves   ////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////
            //move up
            //|  .  .  .  .  . |
            //|  .  .  D  .  . |
            //|  .  .  B  .  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            if(pieces[7] > 4) {
                if (board[pieces[7] - 5] == 0) {
                    moves[j] = pieces[7];
                    moves[j + 1] = pieces[7] - 5;
                    j = j + 2;
                    moves[0]++;
                }
            }

            //move down
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            //|  .  .  B  .  . |
            //|  .  .  D  .  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            if(pieces[7] < 25) {
                if (board[pieces[7] + 5] == 0) {
                    moves[j] = pieces[7];
                    moves[j + 1] = pieces[7] + 5;
                    j = j + 2;
                    moves[0]++;
                }
            }

            //move left
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            //|  .  D  B  .  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            if(pieces[7] % 5 != 0) {
                if (board[pieces[7] - 1] == 0) {
                    moves[j] = pieces[7];
                    moves[j + 1] = pieces[7] - 1;
                    j = j + 2;
                    moves[0]++;
                }
            }

            //move right
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            //|  .  .  B  D  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            if(pieces[7] % 5 != 4) {
                if (board[pieces[7] + 1] == 0) {
                    moves[j] = pieces[7];
                    moves[j + 1] = pieces[7] + 1;
                    j = j + 2;
                    moves[0]++;
                }
            }

/////////////////////////////////////////////////////////////////////////////////
/////////////////////////////   Aggressive Moves   //////////////////////////////
/////////////////////////////////////////////////////////////////////////////////

// 0
            if(pieces[7] == 0) {
                //|  B  .  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  D  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  D |
                //|  .  .  .  .  . |
                for(int i = 1; i < 5; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
            }
// 1
            else if(pieces[7] == 1) {
                //|  .  B  .  .  . |
                //|  D  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  B  .  .  . |
                //|  .  .  D  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  D |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
            }
// 2
            else if(pieces[7] == 2) {
                //|  .  .  B  .  . |
                //|  .  D  .  .  . |
                //|  D  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  B  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  D |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
            }
// 3
            else if(pieces[7] == 3) {
                //|  .  .  .  B  . |
                //|  .  .  D  .  . |
                //|  .  D  .  .  . |
                //|  D  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
                //|  .  .  .  B  . |
                //|  .  .  .  .  D |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
            }
// 4
            else if(pieces[7] == 4) {
                //|  .  .  .  .  B |
                //|  .  .  .  D  . |
                //|  .  .  D  .  . |
                //|  .  D  .  .  . |
                //|  D  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 5; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
            }
// 5
            else if(pieces[7] == 5) {
                //|  .  D  .  .  . |
                //|  B  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  B  .  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  D  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  D |
                for(int i = 1; i < 5; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
            }
// 6
            else if(pieces[7] == 6) {
                //|  D  .  .  .  . |
                //|  .  B  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  D  .  . |
                //|  .  B  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  B  .  .  . |
                //|  D  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  B  .  .  . |
                //|  .  .  D  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  D |
                //|  .  .  .  .  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
            }
// 7
            else if(pieces[7] == 7) {
                //|  .  D  .  .  . |
                //|  .  .  B  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  D  . |
                //|  .  .  B  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  B  .  . |
                //|  .  D  .  .  . |
                //|  D  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  B  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  D |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
            }
// 8
            else if(pieces[7] == 8) {
                //|  .  .  D  .  . |
                //|  .  .  .  B  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  D |
                //|  .  .  .  B  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  B  . |
                //|  .  .  D  .  . |
                //|  .  D  .  .  . |
                //|  D  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  B  . |
                //|  .  .  .  .  D |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
            }
// 9
            else if(pieces[7] == 9) {
                //|  .  .  .  D  . |
                //|  .  .  .  .  B |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  B |
                //|  .  .  .  D  . |
                //|  .  .  D  .  . |
                //|  .  D  .  .  . |
                //|  D  .  .  .  . |
                for(int i = 1; i < 5; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
            }
// 10
            else if(pieces[7] == 10) {
                //|  .  .  D  .  . |
                //|  .  D  .  .  . |
                //|  B  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  B  .  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  D  .  . |
                //|  .  .  .  D  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
            }
// 11
            else if(pieces[7] == 11) {
                //|  .  .  .  .  . |
                //|  D  .  .  .  . |
                //|  .  B  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  D  . |
                //|  .  .  D  .  . |
                //|  .  B  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  B  .  .  . |
                //|  D  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  B  .  .  . |
                //|  .  .  D  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  D |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
            }
// 12
            else if(pieces[7] == 12) {
                //|  D  .  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  B  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  D |
                //|  .  .  .  D  . |
                //|  .  .  B  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  B  .  . |
                //|  .  D  .  .  . |
                //|  D  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  B  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  D |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
            }
// 13
            else if(pieces[7] == 13) {
                //|  .  D  .  .  . |
                //|  .  .  D  .  . |
                //|  .  .  .  B  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  D |
                //|  .  .  .  B  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  B  . |
                //|  .  .  D  .  . |
                //|  .  D  .  .  . |
                //|  D  .  .  .  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  B  . |
                //|  .  .  .  .  D |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
            }
// 14
            else if(pieces[7] == 14) {
                //|  .  .  D  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  B |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  B |
                //|  .  .  .  D  . |
                //|  .  .  D  .  . |
                //|  .  D  .  .  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
            }
// 15
            else if(pieces[7] == 15) {
                //|  .  .  .  D  . |
                //|  .  .  D  .  . |
                //|  .  D  .  .  . |
                //|  B  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  B  .  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  D  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
            }
// 16
            else if(pieces[7] == 16) {
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  D  .  .  .  . |
                //|  .  B  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  D |
                //|  .  .  .  D  . |
                //|  .  .  D  .  . |
                //|  .  B  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  B  .  .  . |
                //|  D  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  B  .  .  .
                //|  .  .  D  .  . |
                //|  .  .  .  D  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
            }
// 17
            else if(pieces[7] == 17) {
                //|  .  .  .  .  . |
                //|  D  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  B  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  D |
                //|  .  .  .  D  . |
                //|  .  .  B  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  B  .  . |
                //|  .  D  .  .  . |
                //|  D  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  B  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  D |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
            }
// 18
            else if(pieces[7] == 18) {
                //|  D  .  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  D  .  . |
                //|  .  .  .  B  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  D |
                //|  .  .  .  B  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  B  . |
                //|  .  .  D  .  . |
                //|  .  D  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  B  . |
                //|  .  .  .  .  D |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
            }
// 19
            else if(pieces[7] == 19) {
                //|  .  D  .  .  . |
                //|  .  .  D  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  B |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  B |
                //|  .  .  .  D  . |
                //|  .  .  D  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
            }
// 20
            else if(pieces[7] == 20) {
                //|  .  .  .  .  D |
                //|  .  .  .  D  . |
                //|  .  .  D  .  . |
                //|  .  D  .  .  . |
                //|  B  .  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 5; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  B  .  .  .  . |
                //|  .  D  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
            }
// 21
            else if(pieces[7] == 21) {
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  D  .  .  .  . |
                //|  .  B  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  D |
                //|  .  .  .  D  . |
                //|  .  .  D  .  . |
                //|  .  B  .  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  B  .  .  . |
                //|  D  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  B  .  .  . |
                //|  .  .  D  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
            }
// 22
            else if(pieces[7] == 22) {
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  D  .  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  B  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  D |
                //|  .  .  .  D  . |
                //|  .  .  B  .  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 3;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 3;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  B  .  . |
                //|  .  D  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  B  .  . |
                //|  .  .  .  D  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
            }
// 23
            else if(pieces[7] == 23) {
                //|  .  .  .  .  . |
                //|  D  .  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  D  .  . |
                //|  .  .  .  B  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 4;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 4;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  D |
                //|  .  .  .  B  . |
                //|  .  .  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  B  . |
                //|  .  .  D  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  B  . |
                //|  .  .  .  .  D |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 6)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
            }
// 24
            else if(pieces[7] == 24) {
                //|  D  .  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  D  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  B |
                //|  .  .  .  .  . |
                for(int i = 1; i < 5; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  B |
                //|  .  .  .  D  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] + (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] + (i * 4)] % 10  == playerNumber) {
                        i = 2;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] + (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 2;
                    }
                }
            }
// 25
            else if(pieces[7] == 25) {
                //|  .  .  .  .  . |
                //|  .  .  .  .  D |
                //|  .  .  .  D  . |
                //|  .  .  D  .  . |
                //|  .  D  .  .  . |
                //|  B  .  .  .  . |
                for(int i = 1; i < 5; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
            }
// 26
            else if(pieces[7] == 26) {
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  D  .  .  .  . |
                //|  .  B  .  .  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  D |
                //|  .  .  .  D  . |
                //|  .  .  D  .  . |
                //|  .  B  .  .  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
            }
// 27
            else if(pieces[7] == 27) {
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  D  .  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  B  .  . |

                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  D |
                //|  .  .  .  D  . |
                //|  .  .  B  .  . |
                for(int i = 1; i < 3; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
            }
// 28
            else if(pieces[7] == 28) {
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  D  .  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  D  .  . |
                //|  .  .  .  B  . |
                for(int i = 1; i < 4; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  D |
                //|  .  .  .  B  . |
                for(int i = 1; i < 2; i++) {
                    if (board[pieces[7] - (i * 4)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 4)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 4);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
            }
// 29
            else if(pieces[7] == 29) {
                //|  .  .  .  .  . |
                //|  D  .  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  D  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  B |
                for(int i = 1; i < 5; i++) {
                    if (board[pieces[7] - (i * 6)] == 0) {
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                    }
                    else if(board[pieces[7] - (i * 6)] % 10  == playerNumber) {
                        i = 5;
                    }
                    else{
                        moves[j] = pieces[7];
                        moves[j + 1] = pieces[7] - (i * 6);
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }
            }
        }
        return moves;
    }

    static int[] queenMoves(int[] pieces, int[] board, char player){
        // moves[] holds all available moves for a turn
        // Size is determined by pieces
        // multiply possible moves by 2 since we need "from" and "to" values
        //Most active position example:
        //|  D  .  D  .  D |
        //|  .  D  D  D  . |
        //|  D  D  Q  D  D |
        //|  .  D  D  D  . |
        //|  D  .  D  .  D |
        //|  .  .  D  .  . |
        //      moveCount   = 1
        //      moves       = 34
        int[] moves = new int[180];
        int j = 1;
        int playerNumber;

        if (player == 'W')
            playerNumber = 1;
        else
            playerNumber = 2;


        for(int q = 8; q < 14; q++) {
///////////////////////////////////////////////////////////////////
///////////////          Search Left          /////////////////////
///////////////////////////////////////////////////////////////////
            if (pieces[q] != -1) {
                for (int i = 1; i < 5; i++) {
                    //Must be at the left side of the board or another of your pieces is to
                    //your left.
                    //No possible move to the left.
                    // i = 5 to end this search
                    //|  .  .  .  .  . |            |  .  .  .  .  . |
                    //|  Q  .  .  .  . |     OR     |  . QD QD QD  Q |
                    //|  .  .  .  .  . |            |  .  .  .  .  . |
                    if (pieces[q] == 0 || (pieces[q] - i) % 5 == 4 || board[pieces[q] - i] % 10 % 10 == playerNumber) {
                        i = 5;
                    }
                    //last move to the left you can make
                    //left side of board
                    // i = 5 to end this search
                    //|  .  .  .  .  . |
                    //|  D  Q  .  .  . |
                    //|  .  .  .  .  . |
                    else if ((pieces[q] - i) % 5 == 0) {
                        moves[j] = pieces[q];
                        moves[j + 1] = pieces[q] - i;
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                    //normal move to the left
                    //|  .  .  .  .  . |
                    //|  D  D QD QD  Q |
                    //|  .  .  .  .  . |
                    else if (board[pieces[q] - i] == 0) {
                        moves[j] = pieces[q];
                        moves[j + 1] = pieces[q] - i;
                        j = j + 2;
                        moves[0]++;
                    }
                    //only other option is opponent in left position
                    //move to this position and set i to 5 to end move since it's
                    //the last position you will be able to move.
                    // i = 5 to end this search
                    //|  .  .  .  .  . |
                    //|  D QD QD QD  Q |
                    //|  .  .  .  .  . |
                    else {
                        moves[j] = pieces[q];
                        moves[j + 1] = pieces[q] - i;
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }

///////////////////////////////////////////////////////////////////
///////////////          Search Right          ////////////////////
///////////////////////////////////////////////////////////////////

                for (int i = 1; i < 5; i++) {
                    //Must be at the right side of the board or another of your pieces is to
                    //your right.
                    //No possible move to the right.
                    //i = 5 to end this search
                    //|  .  .  .  .  . |            |  .  .  .  .  . |
                    //|  .  .  .  .  Q |     OR     |  Q QD QD QD  . |
                    //|  .  .  .  .  . |            |  .  .  .  .  . |
                    if ((pieces[q] + i) % 5 == 0 || board[pieces[q] + i] % 10 % 10 == playerNumber) {
                        i = 5;
                    }
                    //last move to the right you can make
                    //right side of board
                    // i = 5 to end this search
                    //|  .  .  .  .  . |
                    //|  .  .  .  Q  D |
                    //|  .  .  .  .  . |
                    else if ((pieces[q] + i) % 5 == 4) {
                        moves[j] = pieces[q];
                        moves[j + 1] = pieces[q] + i;
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                    //normal move to the right
                    //|  .  .  .  .  . |
                    //|  Q QD QD  D  D |
                    //|  .  .  .  .  . |
                    else if (board[pieces[q] + i] == 0) {
                        moves[j] = pieces[q];
                        moves[j + 1] = pieces[q] + i;
                        j = j + 2;
                        moves[0]++;
                    }
                    //only other option is opponent in right position
                    //move to this position and set i to 5 to end move since it's
                    //the last position you will be able to move.
                    // i = 5 to end this search
                    //|  .  .  .  .  . |
                    //|  Q QD QD QD  D |
                    //|  .  .  .  .  . |
                    else {
                        moves[j] = pieces[q];
                        moves[j + 1] = pieces[q] + i;
                        j = j + 2;
                        moves[0]++;
                        i = 5;
                    }
                }

///////////////////////////////////////////////////////////////////
///////////////          Search Up          ///////////////////////
///////////////////////////////////////////////////////////////////

                for (int i = 1; i < 6; i++) {
                    //Must be at the top of the board or another of your pieces is above you
                    //No possible move up.
                    //Set i to 6 to end move
                    //|  .  .  Q  .  . |            |  .  .  .  .  . |
                    //|  .  .  .  .  . |     OR     |  .  . QD  .  . |
                    //|  .  .  .  .  . |            |  .  . QD  .  . |
                    //|  .  .  .  .  . |            |  .  . QD  .  . |
                    //|  .  .  .  .  . |            |  .  . QD  .  . |
                    //|  .  .  .  .  . |            |  .  . Q   .  . |
                    if ((pieces[q] - ((i - 1) * 5)) / 5 == 0 || board[pieces[q] - (i * 5)] % 10 % 10 == playerNumber) {
                        i = 6;
                    }
                    //normal move up
                    //|  .  .  D  .  . |
                    //|  .  . QD  .  . |
                    //|  .  . QD  .  . |
                    //|  .  . QD  .  . |
                    //|  .  . QD  .  . |
                    //|  .  . Q   .  . |
                    else if (board[pieces[q] - (i * 5)] == 0) {
                        moves[j] = pieces[q];
                        moves[j + 1] = pieces[q] - (i * 5);
                        j = j + 2;
                        moves[0]++;
                    }
                    //only other option is opponent directly above
                    //move to this position and set i to 6 to end move
                    //|  .  .  D  .  . |
                    //|  .  . QD  .  . |
                    //|  .  . QD  .  . |
                    //|  .  . QD  .  . |
                    //|  .  . QD  .  . |
                    //|  .  . Q   .  . |
                    else {
                        moves[j] = pieces[q];
                        moves[j + 1] = pieces[q] - (i * 5);
                        j = j + 2;
                        moves[0]++;
                        i = 6;
                    }
                }
///////////////////////////////////////////////////////////////////
///////////////          Search Down        ///////////////////////
///////////////////////////////////////////////////////////////////

                for (int i = 1; i < 6; i++) {
                    //Must be at the bottom of the board or another of your pieces is below you
                    //No possible move down.
                    //Set i to 6 to end move
                    //|  .  .  .  .  . |            |  .  .  Q  .  . |
                    //|  .  .  .  .  . |     OR     |  .  . QD  .  . |
                    //|  .  .  .  .  . |            |  .  . QD  .  . |
                    //|  .  .  .  .  . |            |  .  . QD  .  . |
                    //|  .  .  .  .  . |            |  .  . QD  .  . |
                    //|  .  .  Q  .  . |            |  .  .  .  .  . |
                    if ((pieces[q] + ((i - 1) * 5)) / 5 == 5 || board[pieces[q] + (i * 5)] % 10 % 10 == playerNumber) {
                        i = 6;
                    }
                    //normal move down
                    //|  .  . Q   .  . |
                    //|  .  . QD  .  . |
                    //|  .  . QD  .  . |
                    //|  .  . QD  .  . |
                    //|  .  . QD  .  . |
                    //|  .  .  D  .  . |
                    else if (board[pieces[q] + (i * 5)] == 0) {
                        moves[j] = pieces[q];
                        moves[j + 1] = pieces[q] + (i * 5);
                        j = j + 2;
                        moves[0]++;
                    }
                    //only other option is opponent directly below
                    //move to this position and set i to 6 to end move
                    //|  .  . Q   .  . |
                    //|  .  . QD  .  . |
                    //|  .  . QD  .  . |
                    //|  .  . QD  .  . |
                    //|  .  . QD  .  . |
                    //|  .  .  D  .  . |
                    else {
                        moves[j] = pieces[q];
                        moves[j + 1] = pieces[q] + (i * 5);
                        j = j + 2;
                        moves[0]++;
                        i = 6;
                    }
                }

// 0
                if(pieces[q] == 0) {
                    //|  Q  .  .  .  . |
                    //|  .  D  .  .  . |
                    //|  .  .  D  .  . |
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 5; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                }
// 1
                else if(pieces[q] == 1) {
                    //|  .  Q  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  Q  .  .  . |
                    //|  .  .  D  .  . |
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                }
// 2
                else if(pieces[q] == 2) {
                    //|  .  .  Q  .  . |
                    //|  .  D  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  Q  .  . |
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                }
// 3
                else if(pieces[q] == 3) {
                    //|  .  .  .  Q  . |
                    //|  .  .  D  .  . |
                    //|  .  D  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                    //|  .  .  .  Q  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                }
// 4
                else if(pieces[q] == 4) {
                    //|  .  .  .  .  Q |
                    //|  .  .  .  D  . |
                    //|  .  .  D  .  . |
                    //|  .  D  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 5; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                }
// 5
                else if(pieces[q] == 5) {
                    //|  .  D  .  .  . |
                    //|  Q  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  Q  .  .  .  . |
                    //|  .  D  .  .  . |
                    //|  .  .  D  .  . |
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  D |
                    for(int i = 1; i < 5; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                }
// 6
                else if(pieces[q] == 6) {
                    //|  D  .  .  .  . |
                    //|  .  Q  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  D  .  . |
                    //|  .  Q  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  Q  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  Q  .  .  . |
                    //|  .  .  D  .  . |
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                }
// 7
                else if(pieces[q] == 7) {
                    //|  .  D  .  .  . |
                    //|  .  .  Q  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  D  . |
                    //|  .  .  Q  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  Q  .  . |
                    //|  .  D  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  Q  .  . |
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                }
// 8
                else if(pieces[q] == 8) {
                    //|  .  .  D  .  . |
                    //|  .  .  .  Q  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  D |
                    //|  .  .  .  Q  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  Q  . |
                    //|  .  .  D  .  . |
                    //|  .  D  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  Q  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                }
// 9
                else if(pieces[q] == 9) {
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  Q |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  Q |
                    //|  .  .  .  D  . |
                    //|  .  .  D  .  . |
                    //|  .  D  .  .  . |
                    //|  D  .  .  .  . |
                    for(int i = 1; i < 5; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                }
// 10
                else if(pieces[q] == 10) {
                    //|  .  .  D  .  . |
                    //|  .  D  .  .  . |
                    //|  Q  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  Q  .  .  .  . |
                    //|  .  D  .  .  . |
                    //|  .  .  D  .  . |
                    //|  .  .  .  D  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                }
// 11
                else if(pieces[q] == 11) {
                    //|  .  .  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  Q  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  D  . |
                    //|  .  .  D  .  . |
                    //|  .  Q  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  Q  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  Q  .  .  . |
                    //|  .  .  D  .  . |
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  D |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                }
// 12
                else if(pieces[q] == 12) {
                    //|  D  .  .  .  . |
                    //|  .  D  .  .  . |
                    //|  .  .  Q  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  D |
                    //|  .  .  .  D  . |
                    //|  .  .  Q  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  Q  .  . |
                    //|  .  D  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  Q  .  . |
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                }
// 13
                else if(pieces[q] == 13) {
                    //|  .  D  .  .  . |
                    //|  .  .  D  .  . |
                    //|  .  .  .  Q  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  Q  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  Q  . |
                    //|  .  .  D  .  . |
                    //|  .  D  .  .  . |
                    //|  D  .  .  .  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  Q  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                }
// 14
                else if(pieces[q] == 14) {
                    //|  .  .  D  .  . |
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  Q |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  Q |
                    //|  .  .  .  D  . |
                    //|  .  .  D  .  . |
                    //|  .  D  .  .  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                }
// 15
                else if(pieces[q] == 15) {
                    //|  .  .  .  D  . |
                    //|  .  .  D  .  . |
                    //|  .  D  .  .  . |
                    //|  Q  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  Q  .  .  .  . |
                    //|  .  D  .  .  . |
                    //|  .  .  D  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                }
// 16
                else if(pieces[q] == 16) {
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  Q  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  D |
                    //|  .  .  .  D  . |
                    //|  .  .  D  .  . |
                    //|  .  Q  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  Q  .  .  . |
                    //|  D  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  Q  .  .  .
                    //|  .  .  D  .  . |
                    //|  .  .  .  D  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                }
// 17
                else if(pieces[q] == 17) {
                    //|  .  .  .  .  . |
                    //|  D  .  .  . |
                    //|  .  D  .  .  . |
                    //|  .  .  Q  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  D  . |
                    //|  .  .  Q  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  Q  .  . |
                    //|  .  D  .  .  . |
                    //|  D  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  Q  .  . |
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  D |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                }
// 18
                else if(pieces[q] == 18) {
                    //|  D  .  .  .  . |
                    //|  .  D  .  .  . |
                    //|  .  .  D  .  . |
                    //|  .  .  .  Q  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  Q  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  Q  . |
                    //|  .  .  D  .  . |
                    //|  .  D  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  Q  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                }
// 19
                else if(pieces[q] == 19) {
                    //|  .  D  .  .  . |
                    //|  .  .  D  .  . |
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  Q |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  Q |
                    //|  .  .  .  D  . |
                    //|  .  .  D  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                }
// 20
                else if(pieces[q] == 20) {
                    //|  .  .  .  .  D |
                    //|  .  .  .  D  . |
                    //|  .  .  D  .  . |
                    //|  .  D  .  .  . |
                    //|  Q  .  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 5; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  Q  .  .  .  . |
                    //|  .  D  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                }
// 21
                else if(pieces[q] == 21) {
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  Q  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  D  . |
                    //|  .  .  D  .  . |
                    //|  .  Q  .  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  Q  .  .  . |
                    //|  D  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  Q  .  .  . |
                    //|  .  .  D  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                }
// 22
                else if(pieces[q] == 22) {
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  D  .  .  . |
                    //|  .  .  Q  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  D  . |
                    //|  .  .  Q  .  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 3;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 3;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  Q  .  . |
                    //|  .  D  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  Q  .  . |
                    //|  .  .  .  D  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                }
// 23
                else if(pieces[q] == 23) {
                    //|  .  .  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  D  .  .  . |
                    //|  .  .  D  .  . |
                    //|  .  .  .  Q  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 4;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 4;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  Q  . |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  Q  . |
                    //|  .  .  D  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  Q  . |
                    //|  .  .  .  .  D |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 6)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                }
// 24
                else if(pieces[q] == 24) {
                    //|  D  .  .  .  . |
                    //|  .  D  .  .  . |
                    //|  .  .  D  .  . |
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  Q |
                    //|  .  .  .  .  . |
                    for(int i = 1; i < 5; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  Q |
                    //|  .  .  .  D  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] + (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] + (i * 4)] % 10  == playerNumber) {
                            i = 2;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] + (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 2;
                        }
                    }
                }
// 25
                else if(pieces[q] == 25) {
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  D  . |
                    //|  .  .  D  .  . |
                    //|  .  D  .  .  . |
                    //|  Q  .  .  .  . |
                    for(int i = 1; i < 5; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                }
// 26
                else if(pieces[q] == 26) {
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  Q  .  .  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  D  . |
                    //|  .  .  D  .  . |
                    //|  .  Q  .  .  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                }
// 27
                else if(pieces[q] == 27) {
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  D  .  .  . |
                    //|  .  .  Q  .  . |

                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  D  . |
                    //|  .  .  Q  .  . |
                    for(int i = 1; i < 3; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                }
// 28
                else if(pieces[q] == 28) {
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  D  .  .  . |
                    //|  .  .  D  .  . |
                    //|  .  .  .  Q  . |
                    for(int i = 1; i < 4; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  . |
                    //|  .  .  .  .  D |
                    //|  .  .  .  Q  . |
                    for(int i = 1; i < 2; i++) {
                        if (board[pieces[q] - (i * 4)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 4)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 4);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                }
// 29
                else if(pieces[q] == 29) {
                    //|  .  .  .  .  . |
                    //|  D  .  .  .  . |
                    //|  .  D  .  .  . |
                    //|  .  .  D  .  . |
                    //|  .  .  .  D  . |
                    //|  .  .  .  .  Q |
                    for(int i = 1; i < 5; i++) {
                        if (board[pieces[q] - (i * 6)] == 0) {
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                        }
                        else if(board[pieces[q] - (i * 6)] % 10  == playerNumber) {
                            i = 5;
                        }
                        else{
                            moves[j] = pieces[q];
                            moves[j + 1] = pieces[q] - (i * 6);
                            j = j + 2;
                            moves[0]++;
                            i = 5;
                        }
                    }
                }
            }
        }
        return moves;
    }

    static int[] kingMoves(int[] pieces, int[] board, char player){
        // moves[] holds all available moves for a turn
        // Size is determined by pieces
        // multiply possible moves by 2 since we need "from" and "to" values
        //Most active position example:
        //|  .  .  .  .  . |
        //|  .  D  D  D  . |
        //|  .  D  K  D  . |
        //|  .  D  D  D  . |
        //|  .  .  .  .  . |
        //|  .  .  .  .  . |
        //      moveCount   = 1
        //      moves       = 16

        int[] moves = new int[18];
        int j = 1;
        int playerNumber;

        if (player == 'W')
            playerNumber = 1;
        else
            playerNumber = 2;


        if(pieces[14] != -1) {
            //move up
            //|  .  .  .  .  . |
            //|  .  .  D  .  . |
            //|  .  .  K  .  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            if (pieces[14] > 4) {
                if (board[pieces[14] - 5] % 10 != playerNumber) {
                    moves[j] = pieces[14];
                    moves[j + 1] = pieces[14] - 5;
                    j = j + 2;
                    moves[0]++;
                }
                //move up-right
                //|  .  .  .  .  . |
                //|  .  .  .  D  . |
                //|  .  .  K  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                if (pieces[14] % 5 != 4 && board[pieces[14] - 4] % 10 != playerNumber) {
                    moves[j] = pieces[14];
                    moves[j + 1] = pieces[14] - 4;
                    j = j + 2;
                    moves[0]++;
                }
                //move up-left
                //|  .  .  .  .  . |
                //|  .  D  .  .  . |
                //|  .  .  K  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                if (pieces[14] % 5 != 0 && board[pieces[14] - 6] % 10 != playerNumber) {
                    moves[j] = pieces[14];
                    moves[j + 1] = pieces[14] - 6;
                    j = j + 2;
                    moves[0]++;
                }
            }

            //move down
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            //|  .  .  K  .  . |
            //|  .  .  D  .  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            if (pieces[14] < 25) {
                if (board[pieces[14] + 5] % 10 != playerNumber) {
                    moves[j] = pieces[14];
                    moves[j + 1] = pieces[14] + 5;
                    j = j + 2;
                    moves[0]++;
                }
                //move down-left
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  K  .  . |
                //|  .  D  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                if (pieces[14] % 5 != 0 && board[pieces[14] + 4] % 10 != playerNumber) {
                    moves[j] = pieces[14];
                    moves[j + 1] = pieces[14] + 4;
                    j = j + 2;
                    moves[0]++;
                }
                //move down-right
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                //|  .  .  K  .  . |
                //|  .  .  .  D  . |
                //|  .  .  .  .  . |
                //|  .  .  .  .  . |
                if (pieces[14] % 5 != 4 && board[pieces[14] + 6] % 10 != playerNumber) {
                    moves[j] = pieces[14];
                    moves[j + 1] = pieces[14] + 6;
                    j = j + 2;
                    moves[0]++;
                }
            }

            //move left
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            //|  .  D  K  .  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            if (pieces[14] % 5 != 0) {
                if (board[pieces[14] - 1] % 10 != playerNumber) {
                    moves[j] = pieces[14];
                    moves[j + 1] = pieces[14] - 1;
                    j = j + 2;
                    moves[0]++;
                }
            }

            //move right
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            //|  .  .  K  D  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            //|  .  .  .  .  . |
            if (pieces[14] % 5 != 4) {
                if (board[pieces[14] + 1] % 10 != playerNumber) {
                    moves[j] = pieces[14];
                    moves[j + 1] = pieces[14] + 1;
                    j = j + 2;
                    moves[0]++;
                }
            }
        }
        return moves;
    }

    static int simulStart(int k, int[] originalMoves, int[] whitePieces, int[] blackPieces, int[] board, int ply, char player, int alpha, int beta){

        int[]   moves       = new int[300],
                firstMove   = {originalMoves[k], originalMoves[k + 1]},
                boardCopy   = Arrays.copyOf(board, board.length),
                whiteCopy   = Arrays.copyOf(whitePieces, whitePieces.length),
                blackCopy   = Arrays.copyOf(blackPieces, blackPieces.length),
                pMoves,
                nMoves,
                rMoves,
                bMoves,
                qMoves,
                kMoves;

        int     thisValue   = 0;

        //Make move
        makeMove(firstMove, whiteCopy, blackCopy, boardCopy);

        if (player == 'W') {
            player = 'B';
        }
        else{
            player = 'W';
        }

        //If the player is white and the ply = 1 we have reached our minimum depth for this search and return
        if (player == 'W') {
            if(ply == 1) {
                thisValue = pieceValue(whiteCopy, blackCopy, 'W');
                return thisValue;
            }

            pMoves = Moves.pawnMoves(whiteCopy, boardCopy, player);
            rMoves = Moves.rookMoves(whiteCopy, boardCopy, player);
            nMoves = Moves.knightMoves(whiteCopy, boardCopy, player);
            bMoves = Moves.bishopMoves(whiteCopy, boardCopy, player);
            qMoves = Moves.queenMoves(whiteCopy, boardCopy, player);
            kMoves = Moves.kingMoves(whiteCopy, boardCopy, player);

            //Pawn moves added to moves list
            System.arraycopy(pMoves, 1, moves, moves[0] * 2 + 1, pMoves[0] * 2 + 1);
            moves[0] += pMoves[0];
            //Rook moves added to moves list
            System.arraycopy(rMoves, 1, moves, moves[0] * 2 + 1, rMoves[0] * 2 + 1);
            moves[0] += rMoves[0];
            //knight moves added to moves list
            System.arraycopy(nMoves, 1, moves, moves[0] * 2 + 1, nMoves[0] * 2 + 1);
            moves[0] += nMoves[0];
            //bishop moves added to moves list
            System.arraycopy(bMoves, 1, moves, moves[0] * 2 + 1, bMoves[0] * 2 + 1);
            moves[0] += bMoves[0];
            //queen moves added to moves list
            System.arraycopy(qMoves, 1, moves, moves[0] * 2 + 1, qMoves[0] * 2 + 1);
            moves[0] += qMoves[0];
            //king moves added to moves list
            System.arraycopy(kMoves, 1, moves, moves[0] * 2 + 1, kMoves[0] * 2 + 1);
            moves[0] += kMoves[0];

            for (int i = 0; i < moves[0]; i++) {
                //checks to see if piece is on board
                //if (whiteCopy[i] >= 0 && whiteCopy[i] < 30) {
                    int value = -simulStart(2*i + 1, moves, whiteCopy, blackCopy, boardCopy, ply - 1, player, -beta, -alpha);

                    //alpha = max(alpha, value);
                    if(alpha < value){
                        alpha = value;
                    }
                    thisValue = alpha;

                    if (alpha >= beta) {
                        //System.out.print("Pruning\n");
                        //System.out.println("returning value: " + value + " \n");
                        return beta;
                    }
                }
            }
        //}
        else{
            if(ply == 1) {
                thisValue = pieceValue(whiteCopy, blackCopy, 'B');
                return thisValue;
            }
            pMoves = Moves.pawnMoves(blackCopy, boardCopy, player);
            rMoves = Moves.rookMoves(blackCopy, boardCopy, player);
            nMoves = Moves.knightMoves(blackCopy, boardCopy, player);
            bMoves = Moves.bishopMoves(blackCopy, boardCopy, player);
            qMoves = Moves.queenMoves(blackCopy, boardCopy, player);
            kMoves = Moves.kingMoves(blackCopy, boardCopy, player);

            //pawn moves added to move list
            System.arraycopy(pMoves, 1, moves, moves[0] * 2 + 1, pMoves[0] * 2 + 1);
            moves[0] += pMoves[0];
            //rook moves added to move list
            System.arraycopy(rMoves, 1, moves, moves[0] * 2 + 1, rMoves[0] * 2 + 1);
            moves[0] += rMoves[0];
            //knight moves added to moves list
            System.arraycopy(nMoves, 1, moves, moves[0] * 2 + 1, nMoves[0] * 2 + 1);
            moves[0] += nMoves[0];
            //bishop moves added to move list
            System.arraycopy(bMoves, 1, moves, moves[0] * 2 + 1, bMoves[0] * 2 + 1);
            moves[0] += bMoves[0];
            //queen moves added to move list
            System.arraycopy(qMoves, 1, moves, moves[0] * 2 + 1, qMoves[0] * 2 + 1);
            moves[0] += qMoves[0];
            //king moves added to move list
            System.arraycopy(kMoves, 1, moves, moves[0] * 2 + 1, kMoves[0] * 2 + 1);
            moves[0] += kMoves[0];

            for (int i = 0; i < moves[0]; i++) {
                int value = -simulStart(2*i + 1, moves, whiteCopy, blackCopy, boardCopy, ply - 1, player, -beta, -alpha);

                //alpha = max(alpha, value);
                if(alpha < value){
                    alpha = value;
                }
                thisValue = alpha;
                if (alpha >= beta) {
                    //System.out.print("Pruning\n");
                    //System.out.println("returning value: " + value + " \n");
                    return beta;
                }
            }
        }

        return thisValue;
    }

    /**
     makeMove::
     Input:
     int[] moveToBeMade:
        [0] board position moving from
        [1] board position moving to
     int[] whitePieces: white piece list
     int[] blackPieces: black piece list
     int[] board: board array
     Output:
     N/A
     Other:
     N/A
     **/
    static void makeMove(int[] moveToBeMade, int[] whitePieces, int[] blackPieces, int[] board){
        //Piece will be removed from board
        if(board[moveToBeMade[1]] != 0) {
            //Black piece was in destination. Piece sets and board will be updated.
            if (board[moveToBeMade[1]] % 10 == 2) {
                //removes black piece from black piece set
                for (int i = 0; i < 15; i++) {
                    if (blackPieces[i] == moveToBeMade[1]) {
                        blackPieces[i] = -1;
                        i = 16;
                    }
                }
                //updates white piece location in white piece set
                for (int i = 0; i < 15; i++) {
                    if (whitePieces[i] == moveToBeMade[0]) {
                        whitePieces[i] = moveToBeMade[1];
                        board[moveToBeMade[0]] = 0;
                        if (i < 5) {
                            board[moveToBeMade[1]] = 1;
                            /////////////////////////////////////////////////////////////
                            //Pawn promotion to queen////////////////////////////////////
                            /////////////////////////////////////////////////////////////
                            if(moveToBeMade[1] < 5 ) {                      /////////////
                                board[moveToBeMade[1]] = 41;                /////////////
                                whitePieces[i] = -1;                        /////////////
                                for(int q = 8; q < 14; q++){                /////////////
                                    if(whitePieces[q] == -1){               /////////////
                                        whitePieces[q] = moveToBeMade[1];   /////////////
                                        q = 14;}}}                          /////////////
                            /////////////////////////////////////////////////////////////
                            /////////////////////////////////////////////////////////////
                            /////////////////////////////////////////////////////////////
                        }
                        else if (i == 5)
                            board[moveToBeMade[1]] = 11;
                        else if (i == 6)
                            board[moveToBeMade[1]] = 21;
                        else if (i == 7)
                            board[moveToBeMade[1]] = 31;
                        else if (i > 7 && i < 14)
                            board[moveToBeMade[1]] = 41;
                        else if (i == 14)
                            board[moveToBeMade[1]] = 51;
                        i = 16;
                    }
                }
            }
            //white piece was in destination. Piece sets and board will be updated.
            else if (board[moveToBeMade[1]] % 10 == 1) {
                //removes white piece from white set
                for (int i = 0; i < 15; i++) {
                    if (whitePieces[i] == moveToBeMade[1]) {
                        whitePieces[i] = -1;
                        i = 16;
                    }
                }
                //updates black piece location in black piece set
                for (int i = 0; i < 15; i++) {
                    if (blackPieces[i] == moveToBeMade[0]) {
                        blackPieces[i] = moveToBeMade[1];
                        board[moveToBeMade[0]] = 0;
                        if (i < 5) {
                            board[moveToBeMade[1]] = 2;

                            /////////////////////////////////////////////////////////////
                            //Pawn promotion to queen////////////////////////////////////
                            /////////////////////////////////////////////////////////////
                            if(moveToBeMade[1] > 24 ) {                     /////////////
                                board[moveToBeMade[1]] = 42;                /////////////
                                blackPieces[i] = -1;                        /////////////
                                for(int q = 8; q < 14; q++){                /////////////
                                    if(blackPieces[q] == -1){               /////////////
                                        blackPieces[q] = moveToBeMade[1];   /////////////
                                        q = 14;}}}                          /////////////
                            /////////////////////////////////////////////////////////////
                            /////////////////////////////////////////////////////////////
                            /////////////////////////////////////////////////////////////
                        }
                        else if (i == 5)
                            board[moveToBeMade[1]] = 12;
                        else if (i == 6)
                            board[moveToBeMade[1]] = 22;
                        else if (i == 7)
                            board[moveToBeMade[1]] = 32;
                        else if (i > 7 && i < 14)
                            board[moveToBeMade[1]] = 42;
                        else if (i == 14)
                            board[moveToBeMade[1]] = 52;
                        i = 16;
                    }
                }
            }
        }
        //Piece will not be removed from board
        else {
            //whitePiece being moved
            if (board[moveToBeMade[0]] % 10 == 1) {
                for (int i = 0; i < 15; i++) {
                    if (whitePieces[i] == moveToBeMade[0]) {
                        whitePieces[i] = moveToBeMade[1];
                        board[moveToBeMade[0]] = 0;
                        if (i < 5) {
                            board[moveToBeMade[1]] = 1;

                            /////////////////////////////////////////////////////////////
                            //Pawn promotion to queen////////////////////////////////////
                            /////////////////////////////////////////////////////////////
                            if(moveToBeMade[1] < 5 ) {                      /////////////
                                board[moveToBeMade[1]] = 41;                /////////////
                                whitePieces[i] = -1;                        /////////////
                                for(int q = 8; q < 14; q++){                /////////////
                                    if(whitePieces[q] == -1){               /////////////
                                        whitePieces[q] = moveToBeMade[1];   /////////////
                                        q = 14;}}}                          /////////////
                            /////////////////////////////////////////////////////////////
                            /////////////////////////////////////////////////////////////
                            /////////////////////////////////////////////////////////////
                        }
                        else if (i == 5)
                            board[moveToBeMade[1]] = 11;
                        else if (i == 6)
                            board[moveToBeMade[1]] = 21;
                        else if (i == 7)
                            board[moveToBeMade[1]] = 31;
                        else if (i > 7 && i < 14)
                            board[moveToBeMade[1]] = 41;
                        else if (i == 14)
                            board[moveToBeMade[1]] = 51;
                        i = 16;
                    }
                }
            }
            //blackPiece being moved
            else if (board[moveToBeMade[0]] % 10 == 2) {
                for (int i = 0; i < 15; i++) {
                    if (blackPieces[i] == moveToBeMade[0]) {
                        blackPieces[i] = moveToBeMade[1];
                        board[moveToBeMade[0]] = 0;
                        if (i < 5){
                            board[moveToBeMade[1]] = 2;
                            /////////////////////////////////////////////////////////////
                            //Pawn promotion to queen////////////////////////////////////
                            /////////////////////////////////////////////////////////////
                            if(moveToBeMade[1] > 24 ) {                     /////////////
                                board[moveToBeMade[1]] = 42;                /////////////
                                blackPieces[i] = -1;                        /////////////
                                for(int q = 8; q < 14; q++){                /////////////
                                    if(blackPieces[q] == -1){               /////////////
                                        blackPieces[q] = moveToBeMade[1];   /////////////
                                        q = 14;}}}                          /////////////
                            /////////////////////////////////////////////////////////////
                            /////////////////////////////////////////////////////////////
                            /////////////////////////////////////////////////////////////
                        }
                        else if (i == 5)
                            board[moveToBeMade[1]] = 12;
                        else if (i == 6)
                            board[moveToBeMade[1]] = 22;
                        else if (i == 7)
                            board[moveToBeMade[1]] = 32;
                        else if (i > 7 && i < 14)
                            board[moveToBeMade[1]] = 42;
                        else if (i == 14)
                            board[moveToBeMade[1]] = 52;
                        i = 16;
                    }
                }
            }
        }
    }

    public static int pieceValue(int[] whitePieces, int[]blackPieces, char player){
        int total = 0;
        int whiteValue = 0;
        int blackValue = 0;

//Add more values for pieces as they come available
        //white moves up
        //score increases as pawns move up
        for(int i = 0; i < 5; i++){
            if(whitePieces[i] != -1) {
                if(whitePieces[i] / 5 == 0) {
                    whiteValue += 1000 + 20000;
                }
                if(whitePieces[i] / 5 == 1){
                    whiteValue += 1000 + 900;
                }
                if(whitePieces[i] / 5 == 2){
                    whiteValue += 1000 + 200;
                }
                if(whitePieces[i] / 5 == 3){
                    whiteValue += 1000 + 50;
                }
                if(whitePieces[i] / 5 == 4){
                    whiteValue += 1000;
                }
            }
        }
        //value for rook
        if(whitePieces[5] != -1) {
            whiteValue += 2500;
        }
        //value for knight
        if(whitePieces[6] != -1) {
            whiteValue += 3000;
        }
        //value for bishop
        if(whitePieces[7] != -1) {
            whiteValue += 3000;
        }
        //value for queen
        for(int i = 8; i < 14; i++){
            if(whitePieces[i] != -1)
                whiteValue += 10000;
        }

        //Death of a king
        if(whitePieces[14] == -1)
            whiteValue -= 150000;
        //System.out.print("White value: " + whiteValue);
        //System.out.print("\n");

        //black moves down
        //score increases as pawns move down
        for(int i = 0; i < 5; i++) {
            if (blackPieces[i] != -1) {
                if(blackPieces[i] / 5 == 1){
                    blackValue += 1000;
                }
                if(blackPieces[i] / 5 == 2){
                    blackValue += 1000 + 50;
                }
                if(blackPieces[i] / 5 == 3){
                    blackValue += 1000 + 200;
                }
                if(blackPieces[i] / 5 == 4){
                    blackValue += 1000 + 900;
                }
                if(blackPieces[i] / 5 == 5){
                    blackValue += 1000 + 20000;
                }
            }
        }
        //value for rook
        if(blackPieces[5] != -1) {
            blackValue += 2500;
        }
        //value for knight
        if(blackPieces[6] != -1) {
            blackValue += 3000;
        }
        //value for bishop
        if(blackPieces[7] != -1) {
            blackValue += 3000;
        }
        //value for queen
        for(int i = 8; i < 14; i++){
            if(blackPieces[i] != -1)
                blackValue += 10000;
        }
        //Death of a king
        if(blackPieces[14] == -1) {
            blackValue -= 150000;
        }
        //System.out.print("Black value: " + blackValue);
        //System.out.print("\n");

        if(player == 'W') {
            return whiteValue;
            //total = blackValue - whiteValue;
        }
        else {
            return blackValue;
            //total = whiteValue - blackValue;
        }
        //return total;
    }

}