import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class TheoGUI{
    private JFrame frame;
    private GridLayout layout;
    private JPanel panel;
    private Board board;
    private JButton[][] buttons;
    public TheoGUI(int width, int height, Board board){
        this.board = board;
        panel = new JPanel();
        frame = new JFrame();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setTitle("Game of Life");
        Dimension dimensions = new Dimension(screen.width, screen.height - frame.getInsets().top);
        frame.setSize(dimensions);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        this.buttons = new JButton[width][height];
        frame.setResizable(false);
        layout = new GridLayout(width, height);
        panel.setLayout(layout);
        frame.add(panel);
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                final int x = i;
                final int y = j;
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.BLACK);
                buttons[i][j].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        swapBoxColour(x, y);
                        board.swapIndex(x, y);
                    }
                });
                panel.add(buttons[i][j]);
            }
        }
        //frame.add(panel);
        panel.setVisible(true);
        frame.setVisible(true);
    }

    public void swapBoxColour(int y, int x){
        System.out.println("changed" + x + " " + y);
        boolean on = this.board.getIndex(y, x);
        if (on){
            this.buttons[y][x].setBackground(Color.BLACK);
        } else {
            this.buttons[y][x].setBackground(Color.WHITE);
        }
    }
}
