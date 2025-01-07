import java.util.Scanner;

public class HumanPlayer extends Player{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    Scanner s;

    public HumanPlayer(){
        s = new Scanner(System.in);
    }

    @Override
    public int[] getMove(Board board){
        System.out.print(board);
        System.out.println("\nInput your move (" + ANSI_PURPLE + "row" + ANSI_RESET + ", " + ANSI_GREEN + "column" + ANSI_RESET + "):");
        int y = s.nextInt();
        int x = s.nextInt();
        return new int[]{y, x};
    }
}
