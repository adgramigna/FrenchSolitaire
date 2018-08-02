//Andrew Gramigna, 2018


public class PSBoard{

	private Space[][] spaces;
	private int rows;
	private int cols;
	private String type; //F, E or T
	private int SAX; //Only applicable with the T5 board

	public PSBoard(String type, int rows, int cols){
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

	//Checking to see if we are in a scenario where SAX count is relevant
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

	//checks each Space based on the SAX resource count and updates the SAX count accordindly
	public int calculateSAX(){
		SAX = 0;
		if (isT55()){ //first check if applicable, otherwise return 0
			if (spaces[1][0].getStatus()+spaces[2][0].getStatus()+spaces[3][0].getStatus() >= 2)
				SAX++;
			if (spaces[4][1].getStatus()+spaces[4][2].getStatus()+spaces[4][3].getStatus() >= 2)
				SAX++;
			if (spaces[1][1].getStatus()+spaces[2][2].getStatus()+spaces[3][3].getStatus() >= 2)
				SAX++; //S
			if (spaces[2][1].getStatus() == 1)
				SAX++;
			if (spaces[3][1].getStatus() == 1) 
				SAX++;
			if (spaces[3][2].getStatus() == 1)
				SAX++; //A
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
				SAX--; //X	
		}
		return SAX;
	}

	//different cases for initialization
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

	//initialize the French board into filled and non-existent spaces
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

	//initialize the English board into filled and non-existent spaces
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

	//initialize the Triangular board into filled and non-existent spaces
	public void initializeT(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				spaces[i][j] = new Space(i,j);
				spaces[i][j].setStatus(-1);
				if(i>=j){
					spaces[i][j].setStatus(1);
				}
			}
		}
	}

	//Takes one of the filled spaces and sets it to empty
	public void makeInitialEmpty(int emptyRow, int emptyCol){
		if(spaces[emptyRow][emptyCol].getStatus() != -1)
			spaces[emptyRow][emptyCol].setStatus(0);
		else
			System.out.println("Try again: Your board has no empty spaces.");
	}
}