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
	private List<Space> emptyIndecies;
	private List<Space> filledIndecies;
	private int filledCounter;
	private Space[][] spaces;


	public FSBoard(int rows, int cols){
		board = new int[rows][cols];
		this.rows = rows;
		this.cols = cols;
		empty = 0;
		nonExistent = rows*cols;
		emptyIndecies = new ArrayList<Space>();
		filledIndecies = new ArrayList<Space>();
		spaces = new Space[rows][cols];
	}

	public void initializeF(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j<cols; j++){
				board[i][j] = -1;
				spaces[i][j] = new Space(i,j);
				spaces[i][j].setValue(-1);
				for (int k = 0; k <= rows/2; k++){
					if ((i == k || i == rows-k-1) && (j >= cols/2-k-1 && j <= cols/2+k+1)){
						board[i][j] = 1;
						spaces[i][j].setValue(1);
						nonExistent--;
						filledIndecies.add(spaces[i][j]);
					}
				}
			}
		}
	}

	public void initializeE(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j<cols; j++){
				board[i][j] = -1;
				spaces[i][j] = new Space(i,j);
				spaces[i][j].setValue(-1);
				if(j <= cols/2+1 && j >= cols/2-1 || i >= rows/2-1 && i <= rows/2+1){
					board[i][j] = 1;
					spaces[i][j].setValue(1);
					nonExistent--;
					filledIndecies.add(spaces[i][j]);
				}
			}
		}
	}

	public void makeInitialEmpty(){
		board[rows/2][rows/2] = 0;
		spaces[rows/2][rows/2].setValue(0);
		empty++;
		emptyIndecies.add(spaces[rows/2][cols/2]);
		for(int i = 0; i < filledIndecies.size(); i++){
			if(filledIndecies.get(i).getX() == rows/2 && filledIndecies.get(i).getY() == rows/2){
				filledIndecies.remove(i);
			}
		}
	}

	public void makeInitialEmpty(int emptyRow, int emptyCol){
		if(board[emptyRow][emptyCol] == 1){
			board[emptyRow][emptyCol] = 0;
			spaces[emptyRow][emptyCol].setValue(0);
			empty++;
			emptyIndecies.add(spaces[emptyRow][emptyCol]);
			for(int i = 0; i < filledIndecies.size(); i++){
				if(filledIndecies.get(i).getX() == emptyRow && filledIndecies.get(i).getY() == emptyCol){
					filledIndecies.remove(i);
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
			System.out.println(i+" "+emptyIndecies.get(i).toString());
		}
		for(int i=0; i<filledIndecies.size(); i++){
			System.out.println(i+" "+filledIndecies.get(i).toString());
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