import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class LoadGameGUI {
    private int count;
    private int[] parameters = new int[] {4,4,1,1,1};
    private Game game;
    public LoadGameGUI(Game game) {
        this.game = game;
        
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
}