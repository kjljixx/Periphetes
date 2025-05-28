import java.util.Arrays;

public class AIPlayer extends Player{
    public double blockedType(Board board, int x, int y, int stm){
        if(x < 0){return 1;}
        if(x >= board.getSize()){return 1;}
        if(y < 0){return 1;}
        if(y >= board.getSize()){return 1;}
        int state = board.getBoard()[y][x];
        if(state == stm){
            return 0;
        }
        if(state == 0){
            return 0;
        }
        return 1;
    }
    public double simulate(Board board){
        boolean notFull = false;
        double eval = 0;
        for(int[] adjIncr : board.getAdjCoordinates()){
            for(int y=0; y<board.getSize(); y++){
                for(int x=0; x<board.getSize(); x++){
                    int currY = y;
                    int currX = x;
                    double otherEndBlockedType = blockedType(board, currX - adjIncr[1], currY - adjIncr[0], board.getBoard()[currY][currX]);
                    for(int i=0; i<6; i++){
                        int[][] stmVals =
                                {
                                        {0, 0, 100, 2000, 10000000, 200000000},
                                        {0, 0, 10, 200, 10000000, 200000000},
                                        {0, 0, 0, 0, 0, 200000000}
                                };
                        int[][] otherVals =
                                {
                                        {0, 0, 10, 200, 1000000, 200000000},
                                        {0, 0, 5, 50, 100, 2000, 200000000},
                                        {0, 0, 0, 0, 0, 200000000}
                                };
                        if(currY >= board.getSize()){
                            int blockedIdx = (int)(blockedType(board, currX, currY, board.getBoard()[y][x])+otherEndBlockedType);
                            eval += (board.getBoard()[currY-adjIncr[0]][currX-adjIncr[1]] == board.getStm() ? stmVals[blockedIdx][i] : -otherVals[blockedIdx][i]);
                            break;
                        }
                        if(currY < 0){
                            int blockedIdx = (int)(blockedType(board, currX, currY, board.getBoard()[y][x])+otherEndBlockedType);
                            eval += (board.getBoard()[currY-adjIncr[0]][currX-adjIncr[1]] == board.getStm() ? stmVals[blockedIdx][i] : -otherVals[blockedIdx][i]);
                            break;
                        }
                        if(currX >= board.getSize()){
                            int blockedIdx = (int)(blockedType(board, currX, currY, board.getBoard()[y][x])+otherEndBlockedType);
                            eval += (board.getBoard()[currY-adjIncr[0]][currX-adjIncr[1]] == board.getStm() ? stmVals[blockedIdx][i] : -otherVals[blockedIdx][i]);
                            break;
                        }
                        if(currX < 0){
                            int blockedIdx = (int)(blockedType(board, currX, currY, board.getBoard()[y][x])+otherEndBlockedType);
                            eval += (board.getBoard()[currY-adjIncr[0]][currX-adjIncr[1]] == board.getStm() ? stmVals[blockedIdx][i] : -otherVals[blockedIdx][i]);
                            break;
                        }

                        if(board.getBoard()[currY][currX] != 0 && i==0) {
                            eval += board.getBoard()[currY][currX] == board.getStm() ? board.getSize()/2.0-Math.abs(currY-board.getSize()/2.0)+board.getSize()/2.0-Math.abs(currX-board.getSize()/2.0) :
                                    -(board.getSize()/2.0-Math.abs(currY-board.getSize()/2.0)+board.getSize()/2.0-Math.abs(currX-board.getSize()/2.0));
                        }
                        if((i != 0 && board.getBoard()[currY-adjIncr[0]][currX-adjIncr[1]] != board.getBoard()[currY][currX]) || i == 5){
                            int blockedIdx = (int)(blockedType(board, currX, currY, board.getBoard()[y][x])+otherEndBlockedType);
                            eval += (board.getBoard()[currY-adjIncr[0]][currX-adjIncr[1]] == board.getStm() ? stmVals[blockedIdx][i] : -otherVals[blockedIdx][i]);
                            break;
                        }

                        if(board.getBoard()[currY][currX] == 0){
                            notFull = true;
                            break;
                        }

                        currY += adjIncr[0];
                        currX += adjIncr[1];
                    }
                }
            }
        }
        if(!notFull){
            return 0;
        }
        return eval;
    }
    @Override
    public int[] getMove(Board board){
        int[] bestMove = {0, 0};
        double bestScore = -1000000000;
        for(int i=0; i<board.getSize(); i++){
            for(int j=0; j<board.getSize(); j++){
                double currScore = 0;
                int[][] changedGameBoard = new int[64][64];
                for(int k=0; k<board.getSize(); k++){
                    changedGameBoard[k] = board.getBoard()[k].clone();
                }
                Board changedBoard = new Board(board.getSize(), changedGameBoard, board.getStm());
                if(!changedBoard.makeMove(new int[]{i, j})){
                    continue;
                }
                currScore = -simulate(changedBoard);
                if(currScore > bestScore){
                    bestScore = currScore;
                    bestMove = new int[]{i, j};
                }
            }
        }
        System.out.println(bestMove[0]+","+bestMove[1]+" "+bestScore+" "+board.getStm());
        return bestMove;
    }
}