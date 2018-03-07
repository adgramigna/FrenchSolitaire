//Andrew Gramigna, 2018

//French Solitaire Simulator.

//V1: Goal of this program is to find a winning path for the game of French Solitaire
//For more information visit <<<1>>>

public class FSBoard{

	private int[][] board;

	public FSBoard(int rows, int cols){
		board = new int[rows][cols];
	}

	public void initializeF(){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j<board[0].length; j++){
				board[i][j] = -1;
				for (int k = 0; k <= board.length/2; k++){
					if ((i == k || i == board.length-k-1) && (j >= board[0].length/2-k-1 && j <= board[0].length/2+k+1))
						board[i][j] = 1;
				}
			}
		}
	}

	public void initializeE(){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j<board[0].length; j++){
				board[i][j] = -1;
				if(j <= board[0].length/2+1 && j >= board[0].length/2-1 || i >= board.length/2-1 && i <= board.length/2+1)
					board[i][j] = 1;
			}
		}
	}

	public void makeEmptySpot(){
		board[board.length/2][board.length/2] = 0;
	}

	public void makeEmptySpot(int emptyRow, int emptyCol){
		if(board[emptyRow][emptyCol] == 1)
			board[emptyRow][emptyCol] = 0;
		else
			System.out.println("Try again: Your board has no empty spaces.");
	

	}

	public void printBoard(){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j<board[0].length; j++){
				if(board[i][j] == -1)
					System.out.print(' ');
				if(board[i][j] == 1)
					System.out.print('.');
				if(board[i][j] == 0)
					System.out.print('o');
				System.out.print(' ');
			}
			System.out.println();
		}
	}
}