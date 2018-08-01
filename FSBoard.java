//Andrew Gramigna, 2018

//French Solitaire Simulator.

//V1: Goal of this program is to find a winning path for the game of French Solitaire
//For more information visit <<<1>>>

public class FSBoard{

	private Space[][] spaces;
	private int rows;
	private int cols;
	private String type;
	private int SAX;

	public FSBoard(String type, int rows, int cols){
		spaces = new Space[rows][cols];
		this.rows = rows;
		this.cols = cols;
		this.type = type;
		this.SAX = 0;

	}

	public void setup(int emptyRow, int emptyCol){
		initialize();
		makeInitialEmpty(emptyRow, emptyCol);
	}

	public boolean isT55(){
		return type.equals("T") && rows == 5 && cols == 5;
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

	public String getType(){
		return type;
	}

	public int calculateSAX(){
		SAX = 0;
		if (isT55()){
			if (spaces[0][0].getStatus() == 1)
				SAX--;
			if (spaces[2][0].getStatus() == 1)
				SAX--;
			if (spaces[4][0].getStatus() == 1)
				SAX--;
			if (spaces[2][2].getStatus() == 1)
				SAX--;
			if (spaces[4][2].getStatus() == 1)
				SAX--;
			if (spaces[4][4].getStatus() == 1)
				SAX--;
			if (spaces[2][1].getStatus() == 1)
				SAX++;
			if (spaces[3][1].getStatus() == 1) 
				SAX++;
			if (spaces[3][2].getStatus() == 1)
				SAX++;
			if (spaces[1][0].getStatus()+spaces[2][0].getStatus()+spaces[3][0].getStatus() >= 2)
				SAX++;
			if (spaces[4][1].getStatus()+spaces[4][2].getStatus()+spaces[4][3].getStatus() >= 2)
				SAX++;
			if (spaces[1][1].getStatus()+spaces[2][2].getStatus()+spaces[3][3].getStatus() >= 2)
				SAX++;
		}
		return SAX;
	}

	public void initialize(){
		switch(type){
			case "F":
				initializeF();
				break;
			case "E":
				initializeE();
				break;
			case "T":
				initializeT();
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
				spaces[i][j].setStatus(-1);
				for (int k = 0; k <= rows/2; k++){
					if ((i == k || i == rows-k-1) && (j >= cols/2-k-1 && j <= cols/2+k+1)){
						spaces[i][j].setStatus(1);
					}
				}
			}
		}
	}

	public void initializeE(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j<cols; j++){
				spaces[i][j] = new Space(i,j);
				spaces[i][j].setStatus(-1);
				if(j <= cols/2+1 && j >= cols/2-1 || i >= rows/2-1 && i <= rows/2+1){
					spaces[i][j].setStatus(1);
				}
			}
		}
	}

	public void initializeT(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j<cols; j++){
				spaces[i][j] = new Space(i,j);
				spaces[i][j].setStatus(-1);
				if(i>=j){
					spaces[i][j].setStatus(1);
				}
			}
		}
	}

	public void makeInitialEmpty(int emptyRow, int emptyCol){
		if(spaces[emptyRow][emptyCol].getStatus() != -1)
			spaces[emptyRow][emptyCol].setStatus(0);
		else
			System.out.println("Try again: Your board has no empty spaces.");
	}
}