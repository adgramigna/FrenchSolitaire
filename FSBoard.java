//Andrew Gramigna, 2018

//French Solitaire Simulator.

//V1: Goal of this program is to find a winning path for the game of French Solitaire
//For more information visit <<<1>>>

import java.util.ArrayList;
import java.util.List;

public class FSBoard{

	private Space[][] spaces;
	private int rows;
	private int cols;

	public FSBoard(int rows, int cols){
		spaces = new Space[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}

	public void setup(String type, int emptyRow, int emptyCol){
		initialize(type);
		makeInitialEmpty(emptyRow, emptyCol);
	}

	public Space[][] getSpaces(){
		return spaces;
	}

	public int getRows(){
		return rows;
	}

	public int getCols(){
		return cols;
	}

	public void initialize(String type){
		switch(type){
			case "F":
				initializeF();
				break;
			case "E":
				initializeE();
				break;
			default:
				System.out.println("Problem with initialization!");
				break;
		}
	}

	public void initializeF(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j<cols; j++){
				spaces[i][j] = new Space(i,j);
				spaces[i][j].setValue(-1);
				for (int k = 0; k <= rows/2; k++){
					if ((i == k || i == rows-k-1) && (j >= cols/2-k-1 && j <= cols/2+k+1)){
						spaces[i][j].setValue(1);
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
				}
			}
		}
	}

	public void makeInitialEmpty(int emptyRow, int emptyCol){
		if(spaces[emptyRow][emptyCol].getValue() == 1)
			spaces[emptyRow][emptyCol].setValue(0);
		else
			System.out.println("Try again: Your board has no empty spaces.");
	}
}