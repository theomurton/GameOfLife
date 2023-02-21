import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.*;

import javax.swing.JButton;

import java.awt.event.*;

import java.awt.event.ActionListener;

/*
class GameTypeButton implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		String choice = e.getActionCommand();
		if (choice.equals("New Game")) {
			System.out.println("new");
		}
		if (choice.equals("Load Game")) {
			System.out.println("load");
		}
	}
}
*/

public class Game {
	private int x;
	private int y;
	private int z;
	private int delay;
	private boolean isQuit;
	private boolean gameStart = false;
	private GUI gui;
	private TheoGUI theoGUI;
	private Board board;
	public static void main (String[] args) throws Exception{
		Game game = new Game();
		game.playGame();
	}
	public void playGame () throws Exception{
		this.gui = new GUI(this);
		gui.getGameType();
		
	}
	public void newGame() throws Exception {
		
		this.board = new Board();
		int[] parameters = this.gui.getParameters();
		this.board.setHeight(parameters[0]);
		this.board.setWidth(parameters[1]);
        this.board.setBoardSize(this.board.getHeight(), this.board.getWidth());
            	this.board.printBoard();
            	this.x = parameters[2];
            	this.y = parameters[3];
				this.z = parameters[4];
				//this.setX();
				//this.setY();
            	//this.setZ();
		this.board.swapIndex(0,0);
		this.board.swapIndex(0,2);
		this.board.swapIndex(1,1);
		this.board.swapIndex(1,2);
		this.board.swapIndex(2,1);
		this.theoGUI = new TheoGUI(board.getHeight(), board.getWidth(), board);
		this.gameLoop();
		
	}
	public void loadGame() throws Exception{
		System.out.println("Supply file name for loading board.");
        File givenFile = new File(this.getInput());
		board = this.loadBoard(this.decodeLoad(givenFile));
		board.printBoard();
		this.gameLoop();
	}
	public void gameLoop() throws Exception{
		while (this.isQuit == false){
			this.updateBoard(board);
			System.out.println("");
			System.out.println("");
			board.printBoard();
			Thread.sleep(100);
		}
	}
	public void setDelay(int delay){
		this.delay = delay;
	}
	public int getDelay(){
		return this.delay;
	}
	public void saveGameBoard(Board board)throws Exception{
		String name = this.getInput();
		File file = new File(name);
		BufferedWriter writer = new BufferedWriter(new FileWriter(name));
		writer.write(board.getHeight() + "," + board.getWidth());
		writer.newLine();
		String line = "";
		for (int i = 0; i < board.getHeight(); i++){
			for (int j = 0; j < board.getWidth(); j++){
				if (board.getIndex(i,j)){
					line+= "1";
				} else {
					line+= "0";
				}
			}
		}
		boolean full;
		String character = String.valueOf(line.charAt(0));
		if (character.equals("0")){
			writer.append("e");
			full = false;
		} else{
			writer.append("f");
			full = true;
		}
		int counter = 1;
		for (int i = 1; i < line.length(); i++){
			String specificCharacter = String.valueOf(line.charAt(i));
			boolean tempBool = full;
			if (specificCharacter.equals("0")){
				full = false;
				counter ++;
			} else {
				full = true;
				counter ++;
			}
			if (tempBool != full && i != 0){
				String value = Integer.toString(counter - 1);
				writer.write(value);
				if (full){
					writer.append("f");
				} else {
					writer.append("e");
				}
				counter= 1;
			}
			if (i == line.length() - 1){
				String finalValue = Integer.toString(counter);
				writer.write(finalValue);
			}
		}
		writer.close();
	}
	//will return a list of coordinates to swap
	public List<Integer> decodeLoad(File file) throws Exception{
		List<Integer> decoded = new ArrayList<>();
		Scanner scan = new Scanner(file);
		List<String> contents = new ArrayList<>();
		while (scan.hasNextLine()){
			contents.add(scan.nextLine());
		}
		String[] dimensions = contents.get(0).split(",");
		int height = Integer.valueOf(dimensions[0]);
		int width = Integer.valueOf(dimensions[1]);
		String code = contents.get(1);
		boolean full = false;
		String number = "";
		int currentRow = 0;
		int currentColumn = 0;
		int total = 0;
		decoded.add(height);
		decoded.add(width);
		for (int i = 0; i < code.length(); i++){
			char charac = code.charAt(i);
			String character = Character.toString(charac);
			if (Character.isLetter(charac)){
				if (i!=0){
					int value = Integer.valueOf(number);
					number  = "";
					if (full){
						for (int j = 0; j < value; j++){
							decoded.add(currentRow);
							decoded.add(currentColumn);
							currentColumn++;
							if (currentColumn == width){
								currentColumn = 0;
								currentRow++;
							}
						}
						total+= value;
					} else {
						total += value;
						currentRow = total/width;
						currentColumn = total%width;
					}
				}
				if (character.equals("f")){
					full = true;
				} else {
					full = false;
				}
			} else {
				number+= character;
			}
			if (i == code.length() - 1){
				int finalValue = Integer.valueOf(number);
				if (full){
					for (int j = 0; j < finalValue; j++){
						decoded.add(currentRow);
						decoded.add(currentColumn);
						if (currentColumn == width + 1){
                                                                currentColumn = 0;
                                                                currentRow++;
                                                        }
					}
				}
			}
		}
		return decoded;
	}
	public void saveGOL(Board board) throws Exception{
                String name = (this.getInput() + ".gol");
                File file = new File(name);
                BufferedWriter writer = new BufferedWriter(new FileWriter(name));
                writer.write(board.getHeight() + "," + board.getWidth());
                writer.newLine();
                for (int i = 0; i < board.getHeight(); i++){
                        String line = "";
                        for (int j = 0; j < board.getWidth(); j++){
                                if (board.getIndex(i,j)){
                                        line+= "1";
                                } else {
                                        line+= "0";
                                }
                        }
                        writer.write(line);
                        writer.newLine();
                }
                writer.close();
        }
	public List<Integer> decodeGOL(File file) throws Exception{
                List<Integer> decoded = new ArrayList<>();
                Scanner scan = new Scanner(file);
                List<String> contents = new ArrayList<>();
                while (scan.hasNextLine()){
                        contents.add(scan.nextLine());
                }
                String[] dimensions = contents.get(0).split(",");
                int height = Integer.valueOf(dimensions[0]);
                int width = Integer.valueOf(dimensions[1]);
                contents.remove(0);
                decoded.add(height);
                decoded.add(width);
                for (int i = 0; i < contents.size(); i++){
                        for (int j = 0; j < contents.get(i).length(); j++){
                                String character = String.valueOf(contents.get(i).charAt(j));
                                if (character.equals("1")){
                                        decoded.add(i);
                                        decoded.add(j);
                                }
                        }
                }
                return decoded;
        }

	public Board loadBoard(List<Integer> input){
		List<Integer> decoded = new ArrayList<>(input);
		Board loadingBoard = new Board();
		loadingBoard.setHeight(decoded.get(0));
		decoded.remove(0);
		loadingBoard.setWidth(decoded.get(0));
		decoded.remove(0);
		System.out.println(decoded);
		loadingBoard.setBoardSize(loadingBoard.getHeight(), loadingBoard.getWidth());
		loadingBoard.printBoard();
		for (int i = 0; i < decoded.size(); i = i+2){
			System.out.println("done " + i);
			loadingBoard.swapIndex(decoded.get(i), decoded.get(i+1));
		}
		return loadingBoard;
	}
	public void updateBoard(Board board) throws Exception{
		int[][] filled = new int[board.getHeight()][board.getWidth()];
		for (int i = 0; i < board.getHeight(); i++){
			for (int j = 0; j < board.getWidth(); j++){
				int numberFilled = this.scanBoard(board, i, j);
				filled[i][j] = numberFilled;
			}
		}
		for (int i = 0; i < board.getHeight(); i++){
			for (int j = 0; j < board.getWidth(); j++){
				if (board.getIndex(i,j) == true && filled[i][j] < this.x){
					this.theoGUI.swapBoxColour(i, j);
					board.swapIndex(i, j);
				} else if (board.getIndex(i,j) == true && filled[i][j] >= this.x && filled[i][j] <= this.y){
					//nothing happens
				} else if (board.getIndex(i,j) == true && filled[i][j] > this.y){
					this.theoGUI.swapBoxColour(i, j);
					board.swapIndex(i,j);
				} else if (board.getIndex(i,j) == false && filled[i][j] == this.z){
					this.theoGUI.swapBoxColour(i, j);
					board.swapIndex(i,j);
				}
			}
		}
		//this will be removed, just showing how many are touching each square.
		/*for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board.length; j++){
				System.out.print(filled[i][j]);
			}
			System.out.println("");
		}*/
	}
	public int scanBoard(Board board, int y, int x){
		int number = 0;
		for (int i = -1; i <=1; i++){
			for (int j = -1; j <=1; j++){
				//don't want to check the centre square
				if (i==0 && j==0){
					number = number;
				} else {
					int yCo = y + i;
					int xCo = x + j;
					//toroidality
					if (xCo == -1){
						xCo = board.getWidth() - 1;
					}
					if (yCo == -1){
						yCo = board.getHeight() - 1;
					}
					if (xCo == board.getWidth()){
						xCo = 0;
					}
					if (yCo == board.getHeight()){
						yCo = 0;
					}
					if (board.getIndex(yCo, xCo)){
						number++;
					}
				}
			}
		}
		return number;
	}
	public int setVariable(int variable, String type){
		while (variable < 1 || variable > 9){
			System.out.println("Supply " + type + ", 1 <= " + type + " <= 9");
			boolean good = true;
			int input = -1;
			try{
				input = Integer.parseInt(getInput());
			} catch(Exception e){
				System.out.println("Bad format, re-enter please");
				good = false;
			}
			if (good){
				variable = input;
			}
		}
		System.out.println(type + " is: " + variable);
		return variable;
	}
	public String getInput(){
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}
	public void setBoardHeight(Board board){
		while (board.getHeight() <= 3 || board.getHeight() >= 51){
			System.out.println("Supply board Height, 4 <= x <= 50");
			boolean good = true;
			int input = -1;
			try{
				input = Integer.parseInt(getInput());
			} catch(Exception e){
				System.out.println("Bad format, re-enter please");
				good = false;
			}
			if (good){
				board.setHeight(input);
			}
		}
		System.out.println("Board height is: " + board.getHeight());
	}
	public void setBoardWidth(Board board){
		while (board.getWidth() <= 3 || board.getWidth() >= 51){
			System.out.println("Supply board Width, 4 <= x <= 50");
			boolean good = true;
			int input = -1;
			try {
                	input = Integer.parseInt(getInput());
			} catch(Exception e){
				System.out.println("Bad format, re-enter please");
				good = false;
			}
			if (good){
				board.setWidth(input);
			}
		}
		System.out.println("Board width is: " + board.getWidth());
	}
}
