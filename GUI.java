import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class GUI implements ActionListener{
    private int count;
    private String gametype = "";
    private int[] parameters = new int[] {4,4,1,1,1};
    private Game game;
    public GUI(Game game) {
        this.game = game;
    }
    public void getGameType() {
        JFrame setup1Frame = new JFrame("GameOfLife");
        setup1Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setup1Frame.setSize(300,300);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(this);
        setup1Frame.getContentPane().add(newGameButton);
        newGameButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setup1Frame.setVisible(false);
                }
            }
        );

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(this);
        setup1Frame.getContentPane().add(loadGameButton);
        loadGameButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setup1Frame.setVisible(false);
                }
            }
        );

        setup1Frame.setLayout(new GridLayout(2,1)); 
         // Adds Button to content pane of frame
        setup1Frame.getContentPane().add(loadGameButton);
        setup1Frame.setVisible(true);
    }
    
    public void newGameGUI() {
        JFrame setup2Frame = new JFrame("Game of Life - Setup");
        SpringLayout layout = new SpringLayout();
        
        setup2Frame.setSize(300,300);
        Container contentPane = setup2Frame.getContentPane();
        contentPane.setLayout(layout);

        
        JLabel heightJLabel = new JLabel("Select game height:");
        JLabel widthJLabel = new JLabel("Select game width:");
        JLabel xLabel = new JLabel("Set x");
        JLabel yLabel = new JLabel("Set y");
        JLabel zLabel = new JLabel("Set z");
        String[] intChoices = new String[9];
        for(int i = 1; i <= 9; i++) {
            intChoices[i - 1] = Integer.toString(i);
        }
        String[] sizeChoices = new String[47];
        for(int i = 4; i <= 50; i++) {
            sizeChoices[i - 4] = Integer.toString(i);
        }
        final JComboBox<String> heightBox = new JComboBox<String>(sizeChoices);
        final JComboBox<String> widthBox = new JComboBox<String>(sizeChoices);
        final JComboBox<String> xBox = new JComboBox<String>(intChoices);
        final JComboBox<String> yBox = new JComboBox<String>(intChoices);
        final JComboBox<String> zBox = new JComboBox<String>(intChoices);
        heightBox.addItemListener( new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                parameters[0] = Integer.parseInt((String)heightBox.getSelectedItem());
            }
        });
        widthBox.addItemListener( new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                parameters[1] = Integer.parseInt((String)widthBox.getSelectedItem());
            }
        });
        xBox.addItemListener( new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                parameters[2] = Integer.parseInt((String)xBox.getSelectedItem());
            }
        });
        yBox.addItemListener( new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                parameters[3] = Integer.parseInt((String)yBox.getSelectedItem());
            }
        });
        zBox.addItemListener( new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                parameters[4] = Integer.parseInt((String)zBox.getSelectedItem());
            }
        });



        contentPane.add(heightJLabel);
        contentPane.add(heightBox);
        contentPane.add(widthJLabel);
        contentPane.add(widthBox);
        contentPane.add(xLabel);
        contentPane.add(xBox);
        contentPane.add(yLabel);
        contentPane.add(yBox);
        contentPane.add(zLabel);
        contentPane.add(zBox);

        layout.putConstraint(SpringLayout.WEST, heightJLabel,5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, heightJLabel,10,SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, heightBox, 6, SpringLayout.EAST, heightJLabel);
        layout.putConstraint(SpringLayout.NORTH, heightBox,6,SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, widthJLabel,5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, widthJLabel,20,SpringLayout.SOUTH, heightJLabel);

        layout.putConstraint(SpringLayout.WEST, widthBox, 6, SpringLayout.EAST, widthJLabel);
        layout.putConstraint(SpringLayout.NORTH, widthBox, -4, SpringLayout.NORTH, widthJLabel);

        layout.putConstraint(SpringLayout.WEST, xLabel, 6, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, xLabel, 20, SpringLayout.SOUTH, widthJLabel);
        
        layout.putConstraint(SpringLayout.WEST, xBox, 6, SpringLayout.EAST, xLabel);
        layout.putConstraint(SpringLayout.NORTH, xBox, -4, SpringLayout.NORTH, xLabel);

        layout.putConstraint(SpringLayout.WEST, yLabel, 6, SpringLayout.EAST, xBox);
        layout.putConstraint(SpringLayout.NORTH, yLabel, 20, SpringLayout.SOUTH, widthJLabel);

        layout.putConstraint(SpringLayout.WEST, yBox, 6, SpringLayout.EAST, yLabel);
        layout.putConstraint(SpringLayout.NORTH, yBox, -4, SpringLayout.NORTH, yLabel);

        layout.putConstraint(SpringLayout.WEST, zLabel, 6, SpringLayout.EAST, yBox);
        layout.putConstraint(SpringLayout.NORTH, zLabel, 20, SpringLayout.SOUTH, widthJLabel);

        layout.putConstraint(SpringLayout.WEST, zBox, 6, SpringLayout.EAST, zLabel);
        layout.putConstraint(SpringLayout.NORTH, zBox, -4, SpringLayout.NORTH, zLabel);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(this);
        setup2Frame.getContentPane().add(confirmButton);
        confirmButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setup2Frame.setVisible(false);
                }
            }
        );

        layout.putConstraint(SpringLayout.NORTH, confirmButton, 20, SpringLayout.SOUTH, xBox);
        layout.putConstraint(SpringLayout.WEST, confirmButton, 40, SpringLayout.SOUTH, xBox);

        setup2Frame.setVisible(true);
    }

    public void loadGameGUI() {
        File folder = new File(".");
        ArrayList<String> saves = new ArrayList<String>();
        Pattern pattern = Pattern.compile("(.*\\.class|.*\\.java|.*\\.git)");
        for(String file : folder.list()) {
            Matcher matcher = pattern.matcher(file);
            if(!matcher.matches()) {
                saves.add(file);
            }
        String[] savesArray = new String[saves.size()];
        saves.toArray(savesArray);

        JLabel savesLabel = new JLabel("Select a game save:");
        final JComboBox<String> savesBox = new JComboBox<String>(savesArray);
        JButton savesButton = new JButton("Load Save");

        JFrame loadGameFrame = new JFrame("Game of Life - Load save");
        SpringLayout layout = new SpringLayout();
        loadGameFrame.setSize(300,300);

        Container contentPane = loadGameFrame.getContentPane();
        contentPane.setLayout(layout);
        contentPane.add(savesLabel);
        contentPane.add(savesBox);
        contentPane.add(savesButton);
        
        layout.putConstraint(SpringLayout.WEST, savesLabel, 5, SpringLayout.WEST,contentPane);
        layout.putConstraint(SpringLayout.NORTH, savesLabel, 10, SpringLayout.NORTH, contentPane);
        
        layout.putConstraint(SpringLayout.WEST, savesBox, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, savesBox, 10, SpringLayout.SOUTH, savesLabel);

        layout.putConstraint(SpringLayout.WEST, savesButton, 6, SpringLayout.EAST, savesBox);
        layout.putConstraint(SpringLayout.NORTH, savesButton, 0, SpringLayout.NORTH, savesBox);

        loadGameFrame.setVisible(true);
        }    
    }

    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        if(event.equals("New Game")) {
            try{
                this.newGameGUI();
            }
            catch (Exception exception) {
                System.out.println("Something went wrong");
            }
            
        }
        if(event.equals("Load Game")) {
            try{
                this.loadGameGUI();
            }
            catch (Exception exception) {
                System.out.println("Something went wrong.");
            }
        }
        if(event.equals("Confirm")) {
            try {
                this.game.newGame();
            }
            catch (Exception exception) {
                System.out.println("Something went wrong.");
            }
        }
    }

    public int[] getParameters() {
        return this.parameters;
    }
}
