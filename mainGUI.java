import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainGUI extends JPanel {
    JButton startButton; 
    JButton pauseButton; 
    JButton saveButton;
    JButton stepButton;
    JButton speedUpButton;
    JButton slowDownButton;
    JButton[][] gridButtons;
    JFrame frame;
    GridLayout gridLayout; 
    BorderLayout borderLayout; 
    JPanel gridPanel; 
    JPanel topPanel; 
    String newOrLoadInput; 
    int height; 
    int width;    
    Board board;

    public void createMainGUI(Board board) {

        this.board = board; 

        frame = new JFrame("GameOfLife");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);

        topPanel = new JPanel();

        startButton = new JButton("Play");
        //startButton.addActionListener(this);

        pauseButton = new JButton("Pause");
        //pauseButton.addActionListener(this);
        
        saveButton = new JButton("Save");
        //saveButton.addActionListener(this); 
     
        stepButton = new JButton("Step");
        //stepButton.addActionListener(this);

        speedUpButton = new JButton("Speed up");
        //speedUpButton.addActionListener(this); 

        slowDownButton = new JButton("Slow down");
        //slowDownButton.addActionListener(this);

        topPanel.add(startButton);
        topPanel.add(pauseButton);
        topPanel.add(saveButton);
        topPanel.add(stepButton);
    
        frame.add(BorderLayout.NORTH, topPanel);

        frame.setVisible(true);
    }

    
    public void createGrid() {
        gridPanel = new JPanel(); 
        gridButtons = new JButton[width][height];
        gridLayout = new GridLayout(width, height);
        gridPanel.setLayout(gridLayout);
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                final int x = i;
                final int y = j;
                gridButtons[i][j] = new JButton();
                gridButtons[i][j].setBackground(Color.BLACK);
                gridButtons[i][j].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        swapBoxColour(x, y);
                        board.swapIndex(x, y);
                    }
                });
                gridPanel.add(gridButtons[i][j]);
            }
        }

        frame.add(gridPanel);
        frame.setVisible(true);
    }

    public void swapBoxColour(int y, int x){
        boolean on = this.board.getIndex(y, x);
        if (on){
            this.gridButtons[y][x].setBackground(Color.BLACK);
        } else {
            this.gridButtons[y][x].setBackground(Color.WHITE);
        }
    }
    
}