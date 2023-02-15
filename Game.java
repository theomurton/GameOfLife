import java.util.*;
public class Game{
	private int x;
	private int y;
	private int z;
	private boolean isQuit;
	public static void main (String[] args)throws Exception{
		Game game = new Game();
		Board board = new Board();
		game.playGame(board);
	}
	public void playGame (Board board) throws Exception{
		this.setBoardHeight(board);
		this.setBoardWidth(board);
		board.setBoardSize(board.getHeight(), board.getWidth());
		board.printBoard();
		this.setX();
		this.setY();
		this.setZ();
		board.swapIndex(1, 1);
		board.swapIndex(0,2);
		board.swapIndex(0, 3);
		board.swapIndex(1, 3);
		board.swapIndex(2, 3);
		board.printBoard();
		while (this.isQuit == false){
			Board currentBoard = board.getBoard();
			Board newBoard = this.getNextBoard(currentBoard);
			System.out.println("");
			System.out.println("");
			newBoard.printBoard();
			Thread.sleep(1000);
		}
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
	public int scanBoard(Board board, int x, int y){
		int number = 0;
		for (int i = -1; i <=1; i++){
			for (int j = -1; j <=1; j++){
				//don't want to check the centre square
				if (i==0 && j==0){
					number = number;
				} else {
					int xCo = x + i;
					int yCo = y + j;
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
					if (board.getIndex(xCo, yCo)){
						number++;
					}
				}
			}
		}
		return number;
	}
	public void setX(){
		while (this.x < 1 || this.x > 9){
			System.out.println("Supply x, 1 <= x <= 9");
			boolean good = true;
			int input = -1;
			try{
				input = Integer.parseInt(getInput());
			} catch(Exception e){
				System.out.println("Bad format, re-enter please");
				good = false;
			}
			if (good){
				this.x = input;
			}
		}
		System.out.println("x is: " + this.x);
	}
	public void setY(){
		while (this.y < 1 || this.y > 9){
			System.out.println("Supply y, 1 <= y <= 9");
			boolean good = true;
			int input = -1;
			try{
				input = Integer.parseInt(getInput());
			} catch(Exception e){
				System.out.println("Bad format, re-enter please");
				good = false;
			}
			if (good){
				this.y = input;
			}
		}
		System.out.println("y is: " + this.y);
	}
	public void setZ(){
		while (this.z < 1 || this.z > 9){
			System.out.println("Supply z, 1 <= z <= 9");
			boolean good = true;
			int input = -1;
			try{
				input = Integer.parseInt(getInput());
			} catch(Exception e){
				System.out.println("Bad format, re-enter please");
				good = false;
			}
			if (good){
				this.z = input;
			}
		}
		System.out.println("z is: " + this.z);
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
