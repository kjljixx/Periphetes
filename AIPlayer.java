import java.util.Arrays;

public class AIPlayer extends Player{
    public double blockedType(Board board, int x, int y, int stm){
        if(x < 0){return 0;}
        if(x >= board.getSize()){return 0;}
        if(y < 0){return 0;}
        if(y >= board.getSize()){return 0;}
        int state = board.getBoard()[y][x];
        if(state == stm){
            return 2;
        }
        if(state == 0){
            return 1;
        }
        return 0;
    }
    public double simulate(Board board){
        boolean notFull = false;
        double eval = 0;
        for(int[] adjIncr : board.getAdjCoordinates()){
            for(int y=0; y<board.getSize(); y++){
                for(int x=0; x<board.getSize(); x++){
                    int currY = y;
                    int currX = x;
                    for(int i=0; i<5; i++){
                        if(currY >= board.getSize()){
                            eval += (board.getBoard()[currY-adjIncr[0]][currX-adjIncr[1]] == board.getStm() ? i*i*i*i*i : -i*i*i)*(blockedType(board, currX, currY, board.getStm()));
                            break;
                        }
                        if(currY < 0){
                            eval += (board.getBoard()[currY-adjIncr[0]][currX-adjIncr[1]] == board.getStm() ? i*i*i*i*i : -i*i*i)*(blockedType(board, currX, currY, board.getStm()));
                            break;
                        }
                        if(currX >= board.getSize()){
                            eval += (board.getBoard()[currY-adjIncr[0]][currX-adjIncr[1]] == board.getStm() ? i*i*i*i*i : -i*i*i)*(blockedType(board, currX, currY, board.getStm()));
                            break;
                        }
                        if(currX < 0){
                            eval += (board.getBoard()[currY-adjIncr[0]][currX-adjIncr[1]] == board.getStm() ? i*i*i*i*i : -i*i*i)*(blockedType(board, currX, currY, board.getStm()));
                            break;
                        }

                        if(board.getBoard()[currY][currX] != 0) {
                            eval += board.getBoard()[currY][currX] == board.getStm() ? board.getSize()/2.0-Math.abs(currY-board.getSize()/2.0)+board.getSize()/2.0-Math.abs(currX-board.getSize()/2.0) :
                                    -(board.getSize()/2.0-Math.abs(currY-board.getSize()/2.0)+board.getSize()/2.0-Math.abs(currX-board.getSize()/2.0));
                        }
                        if(i != 0 && board.getBoard()[currY-adjIncr[0]][currX-adjIncr[1]] != board.getBoard()[currY][currX]){
                            eval += (board.getBoard()[currY-adjIncr[0]][currX-adjIncr[1]] == board.getStm() ? i*i*i*i*i : -i*i*i)*(blockedType(board, currX, currY, board.getStm()));
                            break;
                        }

                        if(board.getBoard()[currY][currX] == 0){notFull = true; break;}

                        if(i == 4){
                            return board.getBoard()[currY][currX] == board.getStm() ? 100000000 : -100000000;
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
