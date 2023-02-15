public class Board{
	private int height;
	private int width;
	private boolean[][] board;
	public void setHeight(int height){
		this.height = height;
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
	public boolean[][] getBoard(){
		return this.board;
	}
}
