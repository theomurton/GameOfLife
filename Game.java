import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.*;
public class Game{
	private int x;
	private int y;
	private int z;
	private int delay;
	private boolean isQuit;
	public static void main (String[] args)throws Exception{
		Game game = new Game();
		game.playGame();
	}
	public void playGame () throws Exception{
		System.out.println("Type 'new' for new board or 'load' to load existing board.");
		String input = this.getInput();
		while (!input.equals("new") && !input.equals("load")){
			input = this.getInput();
		}
		Board board;
		if (input.equals("new")){
			board = new Board();
			this.setBoardHeight(board);
                	this.setBoardWidth(board);
                	board.setBoardSize(board.getHeight(), board.getWidth());
                	board.printBoard();
                	this.x = setVariable(x, "x");
                	this.y = setVariable(y, "y");
					this.z = setVariable(z, "z");
					//this.setX();
					//this.setY();
                	//this.setZ();
			board.swapIndex(0,0);
			board.swapIndex(0,2);
			board.swapIndex(1,1);
			board.swapIndex(1,2);
			board.swapIndex(2,1);
		} else {
			System.out.println("Supply file name for loading board.");
                	File givenFile = new File(this.getInput());
			board = this.loadBoard(this.decodeLoad(givenFile));
			board.printBoard();
		}
		while (this.isQuit == false){
			Board currentBoard = board.getBoard();
			Board newBoard = this.getNextBoard(currentBoard);
			System.out.println("");
			System.out.println("");
			newBoard.printBoard();
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
	public Board getNextBoard(Board board) throws Exception{
		Board newBoard = new Board();
		newBoard.setHeight(board.getHeight());
		newBoard.setWidth(board.getWidth());
		newBoard.setArray(board.getArray());
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
					newBoard.swapIndex(i, j);
				} else if (board.getIndex(i,j) == true && filled[i][j] >= this.x && filled[i][j] <= this.y){
					//nothing happens
				} else if (board.getIndex(i,j) == true && filled[i][j] > this.y){
					newBoard.swapIndex(i,j);
				} else if (board.getIndex(i,j) == false && filled[i][j] == this.z){
					newBoard.swapIndex(i,j);
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
		return newBoard;
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
