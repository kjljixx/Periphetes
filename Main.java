public class Main {
    public static void main(String[] args) {
        Board test = new Board(15);
        HumanPlayer hPlayer = new HumanPlayer();
        AIPlayer aiPlayer = new AIPlayer();
        int aiPlayerState = 1; //0 = human vs human, 1 = ai vs player with ai going first, 2 = ai vs player with ai going second, 3 = ai vs ai
        while(test.gameState() == 0){
            Player currPlayer;
            if(aiPlayerState == 0){
                currPlayer = hPlayer;
            }
            else if(aiPlayerState == 3){
                currPlayer = aiPlayer;
            }
            else if(aiPlayerState == 1){
                currPlayer = test.getStm() == 1 ? aiPlayer : hPlayer;
            }
            else{
                currPlayer = test.getStm() == 1 ? hPlayer : aiPlayer;
            }
            if(!test.makeMove(currPlayer.getMove(test))){
                System.out.println("Invalid Move. Try Again.");
            }
        }
        int winner = test.gameState()*test.getStm();
        if(winner == 1){
            System.out.println(test.toStringWithColoredWins());
            System.out.println("Black Wins!");
        }
        else if(winner == -1){
            System.out.println(test.toStringWithColoredWins());
            System.out.println("White Wins!");
        }
        else{
            System.out.println(test);
            System.out.println("Draw!");
        }
    }
}