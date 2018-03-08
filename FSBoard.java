//Andrew Gramigna, 2018

//French Solitaire Simulator.

//V1: Goal of this program is to find a winning path for the game of French Solitaire
//For more information visit <<<1>>>

import java.util.*;

public class FSBoard{

	private Space[][] spaces;
	private int rows;
	private int cols;
	private int empty;
	private int nonExistent;
	private List<Space> emptyIndecies;
	private List<Space> filledIndecies;
	private int filledCounter;


	public FSBoard(int rows, int cols){
		spaces = new Space[rows][cols];
		this.rows = rows;
		this.cols = cols;
		empty = 0;
		nonExistent = rows*cols;
		emptyIndecies = new ArrayList<Space>();
		filledIndecies = new ArrayList<Space>();
	}

	public void initializeF(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j<cols; j++){
				spaces[i][j] = new Space(i,j);
				spaces[i][j].setValue(-1);
				for (int k = 0; k <= rows/2; k++){
					if ((i == k || i == rows-k-1) && (j >= cols/2-k-1 && j <= cols/2+k+1)){
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
				spaces[i][j] = new Space(i,j);
				spaces[i][j].setValue(-1);
				if(j <= cols/2+1 && j >= cols/2-1 || i >= rows/2-1 && i <= rows/2+1){
					spaces[i][j].setValue(1);
					nonExistent--;
					filledIndecies.add(spaces[i][j]);
				}
			}
		}
	}

	public void makeInitialEmpty(){
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
		if(spaces[emptyRow][emptyCol].getValue() == 1){
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

	public void printMisc(){
		System.out.println("NE:" + nonExistent + " Empty:"+ empty);
	}

	public void printFilledAndEmpty(){
		for(int i=0; i<emptyIndecies.size(); i++){
			System.out.println(i+" "+emptyIndecies.get(i).toString());
		}
		System.out.println();
		for(int i=0; i<filledIndecies.size(); i++){
			System.out.println(i+" "+filledIndecies.get(i).toString());
		}
	}

	public void printBoard(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j<cols; j++){
				if(spaces[i][j].getValue() == -1)
					System.out.print(' ');
				if(spaces[i][j].getValue() == 1)
					System.out.print('.');
				if(spaces[i][j].getValue() == 0)
					System.out.print('o');
				System.out.print(' ');
			}
			System.out.println();
		}
	}

	public void printAll(){
		printMisc();
		printFilledAndEmpty();
		printBoard();
	}
}