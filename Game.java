import java.util.*;
public class Game{
	public static void main(String[] args){
		Game game = new Game();
		Board board = new Board();
		game.playGame(board);
	}
	public void playGame(Board board){
		while (board.getHeight() <= 3 || board.getHeight() >= 51){
			System.out.println("Supply board Height, 4 <= x <= 50");
			boolean good = true;
			int input = -1;
			try{
				input = Integer.parseInt(getInput());
			} catch(Exception e){
				System.out.println("bad format");
				good = false;
			}
			if (good){
			board.setHeight(input);
			}
		}
		System.out.println("Board height is: " + board.getHeight());
		while (board.getWidth() <= 3 || board.getWidth() >= 51){
			System.out.println("Supply board Width, 4 <= x <= 50");
                	board.setWidth(Integer.parseInt(getInput()));
		}
		System.out.println("Board width is: " + board.getWidth());
	}
	public String getInput(){
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}
}
