//Andrew Gramigna, 2018

//French Solitaire Simulator.

//V1: Goal of this program is to find a winning path for the game of French Solitaire
//For more information visit <<<1>>>

import java.util.*;

public class FSBoard{

	private int[][] board;
	private int rows;
	private int cols;
	private int empty;
	private int nonExistent;
	private List<List<Integer>> emptyIndecies;
	private List<List<Integer>> filledIndecies;
	private int filledCounter;


	public FSBoard(int rows, int cols){
		board = new int[rows][cols];
		this.rows = rows;
		this.cols = cols;
		empty = 0;
		nonExistent = rows*cols;
		emptyIndecies = new ArrayList<List<Integer>>();
		filledIndecies = new ArrayList<List<Integer>>();
		filledCounter = 0;
	}

	public void initializeIndecies(){
		for(int i = 0; i < rows*cols; i++)  {
        	emptyIndecies.add(new ArrayList<Integer>());
    	}
    	for(int i = 0; i < rows*cols; i++)  {
        	filledIndecies.add(new ArrayList<Integer>());
    	}
	}

	public void initializeF(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j<cols; j++){
				board[i][j] = -1;
				for (int k = 0; k <= rows/2; k++){
					if ((i == k || i == rows-k-1) && (j >= cols/2-k-1 && j <= cols/2+k+1)){
						board[i][j] = 1;
						nonExistent--;
						filledIndecies.get(filledCounter).add(i);
						filledIndecies.get(filledCounter).add(j);
						filledCounter++;
					}
				}
			}
		}
	}

	public void initializeE(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j<cols; j++){
				board[i][j] = -1;
				if(j <= cols/2+1 && j >= cols/2-1 || i >= rows/2-1 && i <= rows/2+1){
					board[i][j] = 1;
					nonExistent--;
					filledIndecies.get(filledCounter).add(i);
					filledIndecies.get(filledCounter).add(j);
					filledCounter++;
				}
			}
		}
	}

	public void makeInitialEmpty(){
		board[rows/2][rows/2] = 0;
		empty++;
		emptyIndecies.get(0).add(rows/2);
		emptyIndecies.get(0).add(rows/2);
		for(int i = 0; i < filledIndecies.size(); i++){
			if(!filledIndecies.get(i).isEmpty()){
				if(filledIndecies.get(i).get(0) == rows/2 && filledIndecies.get(i).get(1) == rows/2){
					filledIndecies.get(i).remove(1);
					filledIndecies.get(i).remove(0);
				}
			}
		}
	}

	public void makeInitialEmpty(int emptyRow, int emptyCol){
		if(board[emptyRow][emptyCol] == 1){
			board[emptyRow][emptyCol] = 0;
			empty++;
			emptyIndecies.get(0).add(emptyRow);
			emptyIndecies.get(0).add(emptyCol);	
			for(int i = 0; i < filledIndecies.size(); i++){
				if(!filledIndecies.get(i).isEmpty()){
					if(filledIndecies.get(i).get(0) == emptyRow && filledIndecies.get(i).get(1) == emptyCol){
						filledIndecies.get(i).remove(1);
						filledIndecies.get(i).remove(0);
					}
				}
			}
		}
		else
			System.out.println("Try again: Your board has no empty spaces.");
	}

	public void move(){

	}

	public void printBoard(){
		System.out.println("NE:" + nonExistent + " Empty:"+ empty);
		for(int i=0; i<emptyIndecies.size(); i++){
			if(!emptyIndecies.get(i).isEmpty())
				System.out.println(i+" "+emptyIndecies.get(i));
		}
		for(int i=0; i<filledIndecies.size(); i++){
			if(!filledIndecies.get(i).isEmpty())
				System.out.println(i+" "+filledIndecies.get(i));
		}
		for(int i = 0; i < rows; i++){
			for(int j = 0; j<cols; j++){
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