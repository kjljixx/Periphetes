public class Board {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private final int[][] adjCoordinates = {{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};
    private int size;
    private int[][] board; //0 for empty, 1 for black, -1 for white
    private int stm; //1 for black, -1 for white

    public Board(int size){
        this.size = size;
        this.board = new int[size][size];
        this.stm = 1;
    }
    public Board(int size, int[][] board, int stm){
        this.size = size;
        this.board = board;
        this.stm = stm;
    }
    public int getStm(){
        return stm;
    }
    public int getSize(){
        return size;
    }
    public int[][] getBoard(){
        return board;
    }
    public int[][] getAdjCoordinates(){
        return adjCoordinates;
    }
    public String toString(){
        String str = "   " + ANSI_GREEN;
        for(int x=0; x<size; x++){
            str += x;
            for(int i=0; i<(x == 0 ? 2 : 3-Math.floor(Math.log10(x))-1); i++){
                str += " ";
            }
        }
        str += ANSI_RESET + ANSI_PURPLE + "\n0  " + ANSI_RESET;
        for(int y=0; y<size; y++){
            for(int x=0; x<size; x++){
                str += (board[y][x] == 1 ? ANSI_RED : "") + (board[y][x] == -1 ? ANSI_BLUE : "") + (board[y][x] < 0 ? "" : " ") + board[y][x] + ANSI_RESET + " ";
            }
            if(y != size-1) {
                str += "\n" + ANSI_PURPLE + (y+1) + ANSI_RESET;
                for(int i=0; i<(y == 0 ? 2 : 3-Math.floor(Math.log10(y+1))-1); i++){
                    str += " ";
                }
            }
        }
        return str;
    }
    public boolean makeMove(int[] pos){
        if(pos[0] >= size){return false;}
        if(pos[0] < 0){return false;}
        if(pos[1] >= size){return false;}
        if(pos[1] < 0){return false;}
        if(board[pos[0]][pos[1]] != 0){return false;}

        board[pos[0]][pos[1]] = stm;
        stm = -stm;
        return true;
    }
    //Returns 0 for ongoing, 1 for stm win, -1 for not stm win
    public int gameState(){
        boolean notFull = false;
        for(int[] adjIncr : adjCoordinates){
            for(int y=0; y<size; y++){
                for(int x=0; x<size; x++){
                    int currY = y;
                    int currX = x;
                    for(int i=0; i<5; i++){
                        if(currY >= size){break;}
                        if(currY < 0){break;}
                        if(currX >= size){break;}
                        if(currX < 0){break;}

                        if(board[currY][currX] == 0){notFull = true; break;}

                        if(i != 0 && board[currY-adjIncr[0]][currX-adjIncr[1]] != board[currY][currX]){
                            break;
                        }

                        if(i == 4){
                            return board[currY][currX] == stm ? 1 : -1;
                        }

                        currY += adjIncr[0];
                        currX += adjIncr[1];
                    }
                }
            }
        }
        if(!notFull){
            return 2;
        }
        return 0;
    }

    public String toStringWithColoredWins(){
        int[][] changedBoard = new int[size][size];
        for(int k=0; k<size; k++){
            changedBoard[k] = board[k].clone();
        }

        for(int[] adjIncr : adjCoordinates){
            for(int y=0; y<size; y++){
                for(int x=0; x<size; x++){
                    int currY = y;
                    int currX = x;
                    for(int i=0; i<5; i++){
                        if(currY >= size){break;}
                        if(currY < 0){break;}
                        if(currX >= size){break;}
                        if(currX < 0){break;}

                        if(board[currY][currX] == 0){break;}

                        if(i != 0 && board[currY-adjIncr[0]][currX-adjIncr[1]] != board[currY][currX]){
                            break;
                        }

                        if(i == 4){
                            for(int a=0; a<5; a++){
                                int iterY = currY-a*adjIncr[0];
                                int iterX = currX-a*adjIncr[1];
                                changedBoard[iterY][iterX] = 2*board[iterY][iterX];
                            }
                        }

                        currY += adjIncr[0];
                        currX += adjIncr[1];
                    }
                }
            }
        }

        String str = "   " + ANSI_GREEN;
        for(int x=0; x<size; x++){
            str += x;
            for(int i=0; i<(x == 0 ? 2 : 3-Math.floor(Math.log10(x))-1); i++){
                str += " ";
            }
        }
        str += ANSI_RESET + ANSI_PURPLE + "\n0  " + ANSI_RESET;
        for(int y=0; y<size; y++){
            for(int x=0; x<size; x++){
                str += (changedBoard[y][x] == 1 ? ANSI_RED : (changedBoard[y][x] == 2 ? ANSI_YELLOW : "")) + (changedBoard[y][x] == -1 ? ANSI_BLUE : (changedBoard[y][x] == -2 ? ANSI_YELLOW : "")) + (changedBoard[y][x] < 0 ? "" : " ") + board[y][x] + ANSI_RESET + " ";
            }
            if(y != size-1) {
                str += "\n" + ANSI_PURPLE + (y+1) + ANSI_RESET;
                for(int i=0; i<(y == 0 ? 2 : 3-Math.floor(Math.log10(y+1))-1); i++){
                    str += " ";
                }
            }
        }
        return str;
    }
}
