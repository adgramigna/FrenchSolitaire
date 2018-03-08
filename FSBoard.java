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
	private int empty;
	private int nonExistent;
	private List<Space> emptySpaces;
	private List<Space> filledSpaces;
	private List<Space> potentialMoveSpaces;
	private int filledCounter;


	public FSBoard(int rows, int cols){
		spaces = new Space[rows][cols];
		this.rows = rows;
		this.cols = cols;
		empty = 0;
		nonExistent = rows*cols;
		emptySpaces = new ArrayList<Space>();
		filledSpaces = new ArrayList<Space>();
		potentialMoveSpaces = new ArrayList<Space>();
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
						filledSpaces.add(spaces[i][j]);
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
					filledSpaces.add(spaces[i][j]);
				}
			}
		}
	}

	public void makeInitialEmpty(){
		spaces[rows/2][rows/2].setValue(0);
		empty++;
		emptySpaces.add(spaces[rows/2][cols/2]);
		potentialMoveSpaces.add(spaces[rows/2][cols/2]);
		for(int i = 0; i < filledSpaces.size(); i++){
			if(filledSpaces.get(i).getX() == rows/2 && filledSpaces.get(i).getY() == rows/2){
				filledSpaces.remove(i);
			}
		}
	}

	public void makeInitialEmpty(int emptyRow, int emptyCol){
		if(spaces[emptyRow][emptyCol].getValue() == 1){
			spaces[emptyRow][emptyCol].setValue(0);
			empty++;
			emptySpaces.add(spaces[emptyRow][emptyCol]);
			potentialMoveSpaces.add(spaces[emptyRow][emptyCol]);
			for(int i = 0; i < filledSpaces.size(); i++){
				if(filledSpaces.get(i).getX() == emptyRow && filledSpaces.get(i).getY() == emptyCol){
					filledSpaces.remove(i);
				}
			}
		}
		else
			System.out.println("Try again: Your board has no empty spaces.");
	}

	public void move(){
		int rand = (int)(Math.random()*potentialMoveSpaces.size());

		Space to = potentialMoveSpaces.get(rand);
		List<Space> from = new ArrayList<Space>();
		if (to.getX()-2 >= 0 && spaces[to.getX()-2][to.getY()].getValue()==1 && spaces[to.getX()-1][to.getY()].getValue()==1)
			from.add(spaces[to.getX()-2][to.getY()]);
		if (to.getX()+2 < rows && spaces[to.getX()+2][to.getY()].getValue()==1 && spaces[to.getX()+1][to.getY()].getValue()==1)
			from.add(spaces[to.getX()+2][to.getY()]);
		if (to.getY()-2 >= 0 && spaces[to.getX()][to.getY()-2].getValue()==1 && spaces[to.getX()][to.getY()-1].getValue()==1)
			from.add(spaces[to.getX()][to.getY()-2]);
		if (to.getY()+2 < cols && spaces[to.getX()][to.getY()+2].getValue()==1 && spaces[to.getX()][to.getY()+1].getValue()==1)
			from.add(spaces[to.getX()][to.getY()+2]);

		int rand2 = (int)(Math.random()*from.size());
		Space chosenFrom = from.get(rand2);

		System.out.println(to.toString());
		System.out.println(chosenFrom.toString());
		Space over;
		if (chosenFrom.getX()-to.getX() == 0 && chosenFrom.getY()-to.getY() > 0){
			over = spaces[to.getX()][to.getY()+1];
		}
		else if (chosenFrom.getX()-to.getX() == 0 && chosenFrom.getY()-to.getY() < 0){
			over = spaces[to.getX()][to.getY()-1];
		}
		else if (chosenFrom.getX()-to.getX() > 0 && chosenFrom.getY()-to.getY() == 0){
			over = spaces[to.getX()+1][to.getY()];
		}
		else{
			over = spaces[to.getX()-1][to.getY()];
		}
		chosenFrom.setValue(0);
		over.setValue(0);
		to.setValue(1);
		empty++;
		emptySpaces.add(over);
		emptySpaces.add(chosenFrom);
		filledSpaces.add(to);
		filledSpaces.remove(over);
		filledSpaces.remove(chosenFrom);
		emptySpaces.remove(to);
		potentialMoveSpaces.remove(to);

		for(Space s: emptySpaces){
			if(!potentialMoveSpaces.contains(s)){
				if((s.getY()-2 >= 0 && spaces[s.getX()][s.getY()-2].getValue() == 1 && spaces[s.getX()][s.getY()-1].getValue()==1)
				|| (s.getY()+2 < cols && spaces[s.getX()][s.getY()+2].getValue() == 1 && spaces[s.getX()][s.getY()+1].getValue()==1)
				|| (s.getX()-2 >= 0 && spaces[s.getX()-2][s.getY()].getValue() == 1 && spaces[s.getX()-1][s.getY()].getValue()==1)
				|| (s.getX()+2 < rows && spaces[s.getX()+2][s.getY()].getValue() == 1 && spaces[s.getX()+1][s.getY()].getValue()==1))
				potentialMoveSpaces.add(s);
			}
		}
	}

	public void printMisc(){
		System.out.println("NE:" + nonExistent + " Empty:"+ empty);
	}

	public void printSpaces(){
		for(int i=0; i<emptySpaces.size(); i++){
			System.out.println(i+" "+emptySpaces.get(i).toString());
		}
		System.out.println();
		for(int i=0; i<filledSpaces.size(); i++){
			System.out.println(i+" "+filledSpaces.get(i).toString());
		}
		System.out.println();
		for(int i=0; i<potentialMoveSpaces.size(); i++){
			System.out.println(i+" "+potentialMoveSpaces.get(i).toString());
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
		printSpaces();
		printBoard();
	}
}