import javax.swing.*;
import java.awt.Font;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JPanel implements ActionListener {
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

    public void createGUI(Board board) {

        this.board = board; 

        frame = new JFrame("GameOfLife");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);

        topPanel = new JPanel();

        startButton = new JButton("Play");
        startButton.setActionCommand("Play");
        startButton.addActionListener(this);

        pauseButton = new JButton("Pause");
        pauseButton.setActionCommand("Pause");
        pauseButton.addActionListener(this);
        
        saveButton = new JButton("Save");
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(this);

        stepButton = new JButton("Step");
        stepButton.setActionCommand("Step");
        stepButton.addActionListener(this);

        speedUpButton = new JButton("Speed up");
        speedUpButton.setActionCommand("Speed");
        speedUpButton.addActionListener(this);

        slowDownButton = new JButton("Slow down");
        slowDownButton.setActionCommand("Slow");
        slowDownButton.addActionListener(this);

        topPanel.add(startButton);
        topPanel.add(pauseButton);
        topPanel.add(saveButton);
        topPanel.add(stepButton);
    
        frame.add(BorderLayout.NORTH, topPanel);
        frame.setVisible(true);
    }


    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
    
        if (command.equals("Start")) {
  
        } else if (command.equals("Save")) {
            
        } else if (command.equals("New or load")) {

        } 
    } 


    public int setVariable(int variable, String type){
        String userVariable;
        while (variable < 1 || variable > 9){
            userVariable = JOptionPane.showInputDialog("Supply " + type + ", 1 <= " + type + " <= 9");
            boolean good = false;

            while (!good) {
                try{    
                    variable = Integer.parseInt(userVariable);
                    good = true;
                } catch(Exception e){
                    userVariable = JOptionPane.showInputDialog("Bad format, re-enter please");
                }
            }
        }
        return variable;
    }

    public int getBoardHeight(){
        String userHeight; 

		while (height <= 3 || height >= 51){
			userHeight = JOptionPane.showInputDialog("Supply board Height, 4 <= x <= 50");
			boolean good = false;
           
            while (!good) {
                try{
                    height = Integer.parseInt(userHeight);
                    good = true;
                } catch(Exception e){
                    userHeight = JOptionPane.showInputDialog("Bad format, re-enter please");
                }
            }
        }
        return height;
	}

    public int getBoardWidth(){
        String userWidth; 

		while (width <= 3 || width >= 51){
			userWidth = JOptionPane.showInputDialog("Supply board Width, 4 <= x <= 50");
			boolean good = false;

            while (!good) {
                try{
                    width = Integer.parseInt(userWidth);
                    good = true;
                } catch(Exception e){
                    userWidth = JOptionPane.showInputDialog("Bad format, re-enter please");
                }
            }  
		} 
        return width;
	}

    public String askNewOrLoad() {
        String newOrLoad;
        newOrLoad = JOptionPane.showInputDialog("Type 'new' for a new board or 'load' to load an existing board.");
        return newOrLoad;
    }

    public String getFileName() {
        String fileName;
        fileName = JOptionPane.showInputDialog("Supply file name for loading board.");
        return fileName;
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
                        //board.swapIndex(x, y);
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