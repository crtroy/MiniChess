import java.util.Scanner;

import java.util.Arrays;
import java.io.IOException;

/**
 * Created by Troy on 4/30/2017.
 */
public class MiniChess {

    public static void main(String args[]) throws IOException {

        Scanner S = new Scanner(System.in);


        //                     0   1   2   3   4   5   6   7   8   9  10  11  12  13  14
        //                     p   p   p   p   p   r   n   b   q  qp  qp  qp  qp  qp   k
        int[] blackPieces = {5, 6, 7, 8, 9, 4, 3, 2, 1, -1, -1, -1, -1, -1, 0};
        //                     P   P   P   P   P   R   N   B   Q  QP  QP  QP  QP  QP   K
        int[] whitePieces = {20, 21, 22, 23, 24, 25, 26, 27, 28, -1, -1, -1, -1, -1, 29};

        int[] board =
                {0, 0, 0, 0, 0
                        , 0, 0, 0, 0, 0
                        , 0, 0, 0, 0, 0
                        , 0, 0, 0, 0, 0
                        , 0, 0, 0, 0, 0
                        , 0, 0, 0, 0, 0};

        fillBoard(board, whitePieces, blackPieces);   //adds pieces to the board
        System.out.print("\n");

////////////////////////////////////////////////////////////////////////////////////////
//////////////////////      IMCS Server Menu      //////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////

        int gameConnected = 1;
        Client Cli = new Client("imcs.svcs.cs.pdx.edu", 3589);


        while (gameConnected != 0) {
            System.out.print("Please make a selection: \n");
            System.out.print("1. log in: \n");
            System.out.print("2. list games: \n");
            System.out.print("3. offer game: \n");
            System.out.print("4. accept game: \n");
            System.out.print("5. exit: \n");

            int selection = S.nextInt();


            if (selection == 1) {
                Cli.login("Fuddled_Army", "imcsTroy");
            } else if (selection == 2) {
                System.out.print(Cli.getGameList() + "\n");
            } else if (selection == 3) {
                System.out.print("\nThis has not yet been implemented\n");
            } else if (selection == 4) {
                System.out.print("\nPlease enter the game id: \n");
                String gameID = S.next();
                playGame(board, whitePieces, blackPieces, Cli, gameID);
                gameConnected = 0;
            } else if (selection == 5) {
                Cli.close();
                gameConnected = 0;
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////
//////////////////////          Game loop         //////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////

    static void playGame(int[] board, int[] whitePieces, int[] blackPieces, Client Cli, String gameID)throws IOException {
        int plyDepth            = 6,                    // How deep we search negamax
                move            = 0,                    // Current move count. Game ends once move 40 completes.
                inProgress      = 1,                    // Flag for while loop
                alpha           = -500000,              // Alpha for pruning
                beta            = 500000,               // Beta for pruning
                turn;                                   // Used for move ordering

        int[]   moveToMake      = {0, 0};               // Holds the positions of the move to be made

        char    myPlayer        = Cli.accept(gameID),   // Holds the color of the IMCS player
                IMCSplayer;

        String  fromOpponent;                           // Move received from your opponent
        String  toOpponent;                             // Move sent to opponent

        //Sets up what player moves first
        if(myPlayer == 'W') {
            turn = 0;
            IMCSplayer = 'B';
        }
        else {
            turn = 1;
            IMCSplayer = 'W';
        }

        while (inProgress != 0) {

            /////////////////////////////////////////////////////////////////////////
            ///// Opponents Move ////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////
            if(turn == 1) {                                                   ///////
                System.out.print("\n" + move + "  " + IMCSplayer + "\n");     ///////
                System.out.print("waiting for opponent\n");
                fromOpponent = Cli.getMove();                                 ///////
                System.out.print(fromOpponent);                               ///////
                coordinatesIn(fromOpponent, moveToMake);                      ///////
                Moves.makeMove(moveToMake, whitePieces, blackPieces, board);  ///////
                printBoard(board);                                            ///////
                System.out.print("\n");                                       ///////
                if (IMCSplayer == 'B'){                                       ///////
                    move++;                                                   ///////
                }                                                             ///////
                turn = 0;}                                                    ///////
            /////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////

            if(turn == 0) {

                    //limits search depth near end game
                if (move + plyDepth > 40)
                    plyDepth = plyDepth - ((move + plyDepth) % 40) + 1;

                int[] boardCopy = Arrays.copyOf(board, board.length);
                int[] whiteCopy = Arrays.copyOf(whitePieces, whitePieces.length);
                int[] blackCopy = Arrays.copyOf(blackPieces, blackPieces.length);

                checkMoves(plyDepth, myPlayer, whiteCopy, blackCopy, boardCopy, alpha, beta, moveToMake);

                toOpponent = coordinatesOut(moveToMake[0], moveToMake[1]);
                Moves.makeMove(moveToMake, whitePieces, blackPieces, board);

                System.out.print(move + "  " + myPlayer + "\n" + toOpponent);
                System.out.print("\nThis is my Move");
                printBoard(board);
                System.out.print("\n");

                /////////////////////////////////////////////////////////////////////
                /////////////////    END GAME CONDITIONS    /////////////////////////
                /////////////////////////////////////////////////////////////////////
                //If black king is dead, white wins                             /////
                if (blackPieces[14] == -1) {                                    /////
                    System.out.print("\nGame over!\nWhite wins\n");             /////
                    inProgress = 1;                                             /////
                    break;                                                      /////
                }                                                               /////
                //If white king is dead, black wins                             /////
                if (whitePieces[14] == -1) {                                    /////
                    System.out.print("\nGame over!\nBlack wins\n");  /////
                    inProgress = 1;                                             /////
                    break;                                                      /////
                }                                                               /////
                //Out of moves                                                  /////
                if (move > 40) {                                                /////
                    System.out.print("\nNo more moves!\nGame is a DRAW!\n");    /////
                    inProgress = 1;                                             /////
                    break;}                                                     /////
                /////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////

                //Swap turn to next player
                turn = 1;
                if(myPlayer == 'B'){
                    move++;
                }
                Cli.sendMove(toOpponent);
            }
        }
    }

    //
    static void checkMoves(int plyDepth, char player, int[] whitePieces, int[] blackPieces, int[] board, int alpha, int beta, int[] moveToMake) {

        int     value;

        int[]   pMoves,
                rMoves,
                nMoves,
                bMoves,
                qMoves,
                kMoves,
                moves   = new int[300];

//Need to add a new moves  array when adding pieces Last added king
//Do this for both white and black
        if (player == 'W') {
            pMoves = Moves.pawnMoves(whitePieces, board, player);
            rMoves = Moves.rookMoves(whitePieces, board, player);
            nMoves = Moves.knightMoves(whitePieces, board, player);
            bMoves = Moves.bishopMoves(whitePieces, board, player);
            qMoves = Moves.queenMoves(whitePieces, board, player);
            kMoves = Moves.kingMoves(whitePieces, board, player);

            //Add pawn moves to the moves list
            System.arraycopy(pMoves, 1, moves, moves[0] * 2 + 1, pMoves[0] * 2 + 1);
            moves[0] += pMoves[0];
            //Add rook moves to the moves list
            System.arraycopy(rMoves, 1, moves, moves[0] * 2 + 1, rMoves[0] * 2 + 1);
            moves[0] += rMoves[0];
            //Add knight moves to the moves list
            System.arraycopy(nMoves, 1, moves, moves[0] * 2 + 1, nMoves[0] * 2 + 1);
            moves[0] += nMoves[0];
            //Add bishop moves to the moves list
            System.arraycopy(bMoves, 1, moves, moves[0] * 2 + 1, bMoves[0] * 2 + 1);
            moves[0] += bMoves[0];
            //Add queen moves to the moves list
            System.arraycopy(qMoves, 1, moves, moves[0] * 2 + 1, qMoves[0] * 2 + 1);
            moves[0] += qMoves[0];
            //Add king moves to the moves list
            System.arraycopy(kMoves, 1, moves, moves[0] * 2 + 1, kMoves[0] * 2 + 1);
            moves[0] += kMoves[0];
        }
        else {
            pMoves = Moves.pawnMoves(blackPieces, board, player);
            rMoves = Moves.rookMoves(blackPieces, board, player);
            nMoves = Moves.knightMoves(blackPieces, board, player);
            bMoves = Moves.bishopMoves(blackPieces, board, player);
            qMoves = Moves.queenMoves(blackPieces, board, player);
            kMoves = Moves.kingMoves(blackPieces, board, player);

            //add pawn moves the the move list
            System.arraycopy(pMoves, 1, moves, moves[0] * 2 + 1, pMoves[0] * 2 + 1);
            moves[0] += pMoves[0];
            //add rook moves the the move list
            System.arraycopy(rMoves, 1, moves, moves[0] * 2 + 1, rMoves[0] * 2 + 1);
            moves[0] += rMoves[0];
            //add knight moves the the move list
            System.arraycopy(nMoves, 1, moves, moves[0] * 2 + 1, nMoves[0] * 2 + 1);
            moves[0] += nMoves[0];
            //add bishop moves the the move list
            System.arraycopy(bMoves, 1, moves, moves[0] * 2 + 1, bMoves[0] * 2 + 1);
            moves[0] += bMoves[0];
            //add queen moves the the move list
            System.arraycopy(qMoves, 1, moves, moves[0] * 2 + 1, qMoves[0] * 2 + 1);
            moves[0] += qMoves[0];
            //add king moves the the move list
            System.arraycopy(kMoves, 1, moves, moves[0] * 2 + 1, kMoves[0] * 2 + 1);
            moves[0] += kMoves[0];
        }

        for (int i = 0; i < moves[0]; i++) {
            //System.out.print("\n checkMove function");
            value = -Moves.simulStart(2*i + 1, moves, whitePieces, blackPieces, board, plyDepth, player,alpha, beta);
            System.out.print("\n" + value + "\n");
            if (alpha < value) {
                alpha = value;
                moveToMake[0] = moves[2 * i + 1];
                moveToMake[1] = moves[2 * i + 2];
            }
        }
    }

    //Prints the board to standard output
    static void printBoard(int[] board) {
        for (int a = 0; a < 30; a++) {
            if (a % 5 == 0)
                System.out.print("\n");
            if (board[a] == 0)
                System.out.print(". ");
            else if (board[a] == 1)
                System.out.print("P ");
            else if (board[a] == 2)
                System.out.print("p ");
            else if (board[a] == 11)
                System.out.print("R ");
            else if (board[a] == 12)
                System.out.print("r ");
            else if (board[a] == 21)
                System.out.print("N ");
            else if (board[a] == 22)
                System.out.print("n ");
            else if (board[a] == 31)
                System.out.print("B ");
            else if (board[a] == 32)
                System.out.print("b ");
            else if (board[a] == 41)
                System.out.print("Q ");
            else if (board[a] == 42)
                System.out.print("q ");
            else if (board[a] == 51)
                System.out.print("K ");
            else if (board[a] == 52)
                System.out.print("k ");

        }
    }

    //Coordinates of move being sent to opposing player
    static String coordinatesOut(int from, int to) {
        String fromLetter = "x",
                fromNumber = "x",
                toLetter = "x",
                toNumber = "x",
                sendCoordinates = "x";
        //Letter value of position moving from
        if (from % 5 == 0) {
            fromLetter = "a";
        } else if (from % 5 == 1) {
            fromLetter = "b";
        } else if (from % 5 == 2) {
            fromLetter = "c";
        } else if (from % 5 == 3) {
            fromLetter = "d";
        } else if (from % 5 == 4) {
            fromLetter = "e";
        }
        //Number value of position moving from
        if (to % 5 == 0) {
            toLetter = "a";
        } else if (to % 5 == 1) {
            toLetter = "b";
        } else if (to % 5 == 2) {
            toLetter = "c";
        } else if (to % 5 == 3) {
            toLetter = "d";
        } else if (to % 5 == 4) {
            toLetter = "e";
        }
        //Letter value of position moving to
        if (from / 5 == 0) {
            fromNumber = "6";
        } else if (from / 5 == 1) {
            fromNumber = "5";
        } else if (from / 5 == 2) {
            fromNumber = "4";
        } else if (from / 5 == 3) {
            fromNumber = "3";
        } else if (from / 5 == 4) {
            fromNumber = "2";
        } else if (from / 5 == 5) {
            fromNumber = "1";
        }
        //Number value of position moving to
        if (to / 5 == 0) {
            toNumber = "6";
        } else if (to / 5 == 1) {
            toNumber = "5";
        } else if (to / 5 == 2) {
            toNumber = "4";
        } else if (to / 5 == 3) {
            toNumber = "3";
        } else if (to / 5 == 4) {
            toNumber = "2";
        } else if (to / 5 == 5) {
            toNumber = "1";
        }

        sendCoordinates = fromLetter + fromNumber + "-" + toLetter + toNumber;

        return sendCoordinates;
    }

    //Coordinates of move received from opposing player
    static int[] coordinatesIn(String in, int[] movesToMake) {

        char[]  charArray1 = in.toCharArray();      //convert input into a char array;
        char[]  charArray2 = {'X', 'X'};
        System.arraycopy(charArray1, 0, charArray2, 0, charArray2.length);
        movesToMake[0] = moveConversion(charArray2);
        System.arraycopy(charArray1, 3, charArray2, 0, charArray2.length);
        movesToMake[1] = moveConversion(charArray2);

        return movesToMake;
    }

    //Adds pieces to the board
    static void fillBoard(int[] board, int[] whitePieces, int[] blackPieces){

        //fill in board with pieces
        for (int i = 0; i < 5; i++) {
            if (whitePieces[i] >= 0)
                board[whitePieces[i]] = 1;
            if (blackPieces[i] >= 0)
                board[blackPieces[i]] = 2;
        }
        //Add rooks
        if (whitePieces[5] >= 0)
            board[whitePieces[5]] = 11;
        if (blackPieces[5] >= 0)
            board[blackPieces[5]] = 12;
        //add knights
        if (whitePieces[6] >= 0)
            board[whitePieces[6]] = 21;
        if (blackPieces[6] >= 0)
            board[blackPieces[6]] = 22;
        //add bishops
        if (whitePieces[7] >= 0)
            board[whitePieces[7]] = 31;
        if (blackPieces[7] >= 0)
            board[blackPieces[7]] = 32;
        //add queens
        for (int i = 8; i < 14; i++) {
            if (whitePieces[i] >= 0)
                board[whitePieces[i]] = 41;
            if (blackPieces[i] >= 0)
                board[blackPieces[i]] = 42;
        }
        //add kings
        if (whitePieces[14] >= 0)
            board[whitePieces[14]] = 51;
        if (blackPieces[14] >= 0)
            board[blackPieces[14]] = 52;

        //return board;
    }

    //converts move(char array) to board position (int)
    static int moveConversion(char[] charArray){

        if(charArray[0] == 'a'){
            if(charArray[1] == '1')
                return 25;
            if(charArray[1] == '2')
                return 20;
            if(charArray[1] == '3')
                return 15;
            if(charArray[1] == '4')
                return 10;
            if(charArray[1] == '5')
                return 5;
            if(charArray[1] == '6')
                return 0;
        }
        else if(charArray[0] == 'b'){
            if(charArray[1] == '1')
                return 26;
            if(charArray[1] == '2')
                return 21;
            if(charArray[1] == '3')
                return 16;
            if(charArray[1] == '4')
                return 11;
            if(charArray[1] == '5')
                return 6;
            if(charArray[1] == '6')
                return 1;
        }
        else if(charArray[0] == 'c'){
            if(charArray[1] == '1')
                return 27;
            if(charArray[1] == '2')
                return 22;
            if(charArray[1] == '3')
                return 17;
            if(charArray[1] == '4')
                return 12;
            if(charArray[1] == '5')
                return 7;
            if(charArray[1] == '6')
                return 2;
        }
        else if(charArray[0] == 'd'){
            if(charArray[1] == '1')
                return 28;
            if(charArray[1] == '2')
                return 23;
            if(charArray[1] == '3')
                return 18;
            if(charArray[1] == '4')
                return 13;
            if(charArray[1] == '5')
                return 8;
            if(charArray[1] == '6')
                return 3;
        }
        else if(charArray[0] == 'e'){
            if(charArray[1] == '1')
                return 29;
            if(charArray[1] == '2')
                return 24;
            if(charArray[1] == '3')
                return 19;
            if(charArray[1] == '4')
                return 14;
            if(charArray[1] == '5')
                return 9;
            if(charArray[1] == '6')
                return 4;
        }
    return -1;
    }

}