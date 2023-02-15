public class Board implements Cloneable{
	private int height;
	private int width;
	private boolean[][] board;
	public void setHeight(int height){
		this.height = height;
	}
	public void setArray(boolean[][] array){
		this.board = array;
	}
	public void setWidth(int width){
                this.width = width;
        }
	public int getHeight(){
		return this.height;
	}
	public int getWidth(){
                return this.width;
        }
	public boolean[][] getArray(){
		return this.board;
	}
	public void setBoardSize(int height, int width){
		this.board = new boolean[height][width];
	}
	public void printBoard(){
		for (int i = 0; i < this.getHeight(); i++){
			for(int j = 0; j < this.getWidth(); j++){
				if (this.board[i][j]){
					System.out.print("M");
				} else {
					System.out.print("O");
				}
			}
			System.out.println("");
		}
	}
	public Board getBoard(){
		return this;
	}
	public boolean getIndex(int x, int y){
		return this.board[x][y];
	}
	public void swapIndex(int x, int y){
		if (this.board[x][y]){
			this.board[x][y] = false;
		} else {
			this.board[x][y] = true;
		}
	}
}
