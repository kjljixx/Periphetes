import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CircleIcon implements Icon {
    private int diameter;
    private Color color;

    public CircleIcon(int diameter, Color color) {
        this.diameter = diameter;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fillOval(x, y, diameter, diameter);
        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return diameter;
    }

    @Override
    public int getIconHeight() {
        return diameter;
    }
}

public class Main {

    public static void updateButton(Board board, JButton button, int row, int col){
        int sq = board.getBoard()[row][col];
        button.setIcon(new CircleIcon(50, sq == 1 ? Color.BLACK : sq == -1 ? Color.WHITE : Color.ORANGE));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Periphetes");
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridLayout(15, 15));

        Board board = new Board(15);
        AIPlayer aiPlayer = new AIPlayer();
        int[] firstMove = aiPlayer.getMove(board);
        board.makeMove(firstMove);

        for(int i=0; i<225; i++) {
            JButton button = new JButton();
            button.setBackground(Color.ORANGE);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            button.setFocusPainted(false);
            int col = i % 15;
            int row = i / 15;
            button.addActionListener((ActionEvent e) -> {
                if (board.getStm() == -1 && board.gameState() == 0) {
                    int[] move = new int[]{row, col};
                    if (!board.makeMove(move)) {
                        JOptionPane.showMessageDialog(null, "Invalid Move: Try Again", "Invalid Move", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    updateButton(board, (JButton)panel.getComponent(move[0]*15+move[1]), move[0], move[1]);

                    if(board.gameState() != 0){
                        int winner = board.gameState()*board.getStm();
                        if(winner == 1){
                            JOptionPane.showMessageDialog(null, "Game Over: Black Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else if(winner == -1){
                            JOptionPane.showMessageDialog(null, "Game Over: White Wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Game Over: Draw", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                        }
                        return;
                    }

                    move = aiPlayer.getMove(board);
                    board.makeMove(move);
                    updateButton(board, (JButton)panel.getComponent(move[0]*15+move[1]), move[0], move[1]);

                    if(board.gameState() != 0){
                        int winner = board.gameState()*board.getStm();
                        if(winner == 1){
                            JOptionPane.showMessageDialog(null, "Game Over: Black Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else if(winner == -1){
                            JOptionPane.showMessageDialog(null, "Game Over: White Wins", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Game Over: Draw", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                        }
                        return;
                    }
                }
            });
            panel.add(button);
        }
        updateButton(board, (JButton)panel.getComponent(firstMove[0]*15+firstMove[1]), firstMove[0], firstMove[1]);

        frame.add(panel);
        frame.setBounds(480, 0, 1000, 1000);
        frame.setVisible(true);
//        int aiPlayerState = 1; //0 = human vs human, 1 = ai vs player with ai going first, 2 = ai vs player with ai going second, 3 = ai vs ai
//        while(test.gameState() == 0){
//            Player currPlayer;
//            if(aiPlayerState == 0){
//                currPlayer = hPlayer;
//            }
//            else if(aiPlayerState == 3){
//                currPlayer = aiPlayer;
//            }
//            else if(aiPlayerState == 1){
//                currPlayer = test.getStm() == 1 ? aiPlayer : hPlayer;
//            }
//            else{
//                currPlayer = test.getStm() == 1 ? hPlayer : aiPlayer;
//            }
//            if(!test.makeMove(currPlayer.getMove(test))){
//                System.out.println("Invalid Move. Try Again.");
//            }
//        }
//        int winner = test.gameState()*test.getStm();
//        if(winner == 1){
//            System.out.println(test.toStringWithColoredWins());
//            System.out.println("Black Wins!");
//        }
//        else if(winner == -1){
//            System.out.println(test.toStringWithColoredWins());
//            System.out.println("White Wins!");
//        }
//        else{
//            System.out.println(test);
//            System.out.println("Draw!");
//        }
    }
}