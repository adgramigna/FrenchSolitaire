//Andrew Gramigna, 2018

import java.util.ArrayList;
import java.util.List;

public class PSGame{

	private PSBoard board;
	private Space[][] spaces;
	private int rows;
	private int cols;
	private String type;
	private int SAX;
	private List<Space> emptySpaces;
	private List<Space> filledSpaces;
	private List<Space> potentialMoveSpaces; //empty and can be jumped into
	private List<Integer[]> states; //One state is a 1D array of the status of each Space. This is the series of states that make up a trial.
	private List<Integer> SAXCounts; //ArrayList of each SAX count, used for debugging
	private String path;
	
	public PSGame(PSBoard board){
		this.board = board;
		spaces = board.getSpaces();
		rows = board.getRows();
		cols = board.getCols();
		type = board.getType();
		SAX = board.calculateSAX();
		emptySpaces = new ArrayList<Space>();
		filledSpaces = new ArrayList<Space>();
		potentialMoveSpaces = new ArrayList<Space>();
		states = new ArrayList<Integer[]>();
		SAXCounts = new ArrayList<Integer>();
		path = "Path: ";
	}

	public PSBoard getBoard(){
		return board;
	}

	public void initialize(){
		initializeArrayLists();
		updateStates();
		updateSAX(SAX);
	}

	//properly setting up filledSpaces, emptySpaces, and potentialMoveSpaces
	//The initialEmptySpace must be a potentialMoveSpace or else we would never be able to start the game
	public void initializeArrayLists(){
		for (int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				Space space = spaces[i][j];
				int status = space.getStatus();
				if(status == 0){
					emptySpaces.add(space);
					potentialMoveSpaces.add(space);
				}
				else if(status == 1){
					filledSpaces.add(space);
				}
			}
		} 
	}


	public void updateStates(){
		Integer[] state = new Integer[rows*cols];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				Space space = spaces[i][j];
				int status = space.getStatus();
				state[i*rows+j] = status; //filling out 1D array for a single state
			}
		}
		states.add(state); //adding to states ArrayList, looped through at the end to print board.
	}

	//a single jump
	public void move(){
		Space chosenFrom, to, over; //3 spaces involved in a jump
		int prevSAX = SAXCounts.get(SAXCounts.size()-1); //keeping track to test if SAX decreases after a jump
		int currSAX = prevSAX;
		List<Space> jumpsFrom = new ArrayList<Space>();
		List<Space> jumpsTo = new ArrayList<Space>();
		
		fillJumps(jumpsFrom, jumpsTo); //fills these two ArrayLists with possible jumps from every potentialMoveSpace

		do{
			int rand = (int)(Math.random()*jumpsFrom.size());
			chosenFrom = jumpsFrom.get(rand);
			to = jumpsTo.get(rand); //random jump chosen
			over = findOver(to, chosenFrom);

			//printPossibleJumps(jumpsFrom, jumpsTo);
			//printMovingSpaces(to, chosenFrom, over); //debugging

			updateSpaces(to, chosenFrom, over); 
			currSAX = board.calculateSAX(); //New SAX calculated. Comment when not using SAX count in T5.
			if (currSAX < prevSAX && jumpsFrom.size() > 1){ //currSAX and prevSAX will both = 0 on non T5 board.
				undoUpdateSpaces(to, chosenFrom, over); //reverts the jump
				removePossibleJump(jumpsFrom, jumpsTo, rand); //we can't pick that jump again.
			} //only executed if SAX count not lowered and we have more jumps.
			else
				break; //jump executed
		}while(true); //find a new jump 
		
		updateArrayLists(to, chosenFrom, over);
		updatePath(to, chosenFrom);
		
		updateStates();
		updateSAX(currSAX);
	}

	//Finds every possible jump by looping through every possible potentialMoveSpace
	//And finding every possible "from" that can jump into that Space
	public void fillJumps(List<Space> jumpsFrom, List<Space> jumpsTo){
		for(Space p: potentialMoveSpaces){
			List<Space> from = new ArrayList<Space>();
			findFrom(p, from);
			for(Space f: from){
				jumpsFrom.add(f);
				jumpsTo.add(p); //add the jumps
			}
		}
	}

	//Spaces updated reflecting a jump
	public void updateSpaces(Space to, Space chosenFrom, Space over){
		chosenFrom.setStatus(0);
		over.setStatus(0);
		to.setStatus(1);
	}

	//Spaces reverted to their status before a jump
	public void undoUpdateSpaces(Space to, Space chosenFrom, Space over){
		chosenFrom.setStatus(1);
		over.setStatus(1);
		to.setStatus(0);
	}

	//remove a jump (used if the jump lowers the SAX count)
	public void removePossibleJump(List<Space> jumpsFrom, List<Space> jumpsTo, int index){
		jumpsFrom.remove(index);
		jumpsTo.remove(index);
	}

	//Checks all possible Spaces that could have a jump into the Space "to".
	//Possible to jump if specific Spaces statuses are filled (Space from and 
	//Space over). Spaces checked are dependent on the type of board, since 
	//Triangular allows for diagonal jumps
	public void findFrom(Space to, List<Space> from){
		if (to.getX()-2 >= 0 && spaces[to.getX()-2][to.getY()].getStatus()==1 && spaces[to.getX()-1][to.getY()].getStatus()==1)
			from.add(spaces[to.getX()-2][to.getY()]);
		if (to.getX()+2 < rows && spaces[to.getX()+2][to.getY()].getStatus()==1 && spaces[to.getX()+1][to.getY()].getStatus()==1)
			from.add(spaces[to.getX()+2][to.getY()]);
		if (to.getY()-2 >= 0 && spaces[to.getX()][to.getY()-2].getStatus()==1 && spaces[to.getX()][to.getY()-1].getStatus()==1)
			from.add(spaces[to.getX()][to.getY()-2]);
		if (to.getY()+2 < cols && spaces[to.getX()][to.getY()+2].getStatus()==1 && spaces[to.getX()][to.getY()+1].getStatus()==1)
			from.add(spaces[to.getX()][to.getY()+2]);
		if(type.equals("T")){//Two more options for triangular
			if (to.getY()-2 >= 0 && to.getX()-2 >= 0 && spaces[to.getX()-2][to.getY()-2].getStatus()==1 && spaces[to.getX()-1][to.getY()-1].getStatus()==1)
				from.add(spaces[to.getX()-2][to.getY()-2]);
			if (to.getY()+2 < cols && to.getX()+2 < rows && spaces[to.getX()+2][to.getY()+2].getStatus()==1 && spaces[to.getX()+1][to.getY()+1].getStatus()==1)
				from.add(spaces[to.getX()+2][to.getY()+2]);
		}
	}

	//Given a Space we are leaving and a Space we are going to,
	//this method finds the Space we are jumping over.
	public Space findOver(Space to, Space chosenFrom){
		if (chosenFrom.getX()-to.getX() == 0 && chosenFrom.getY()-to.getY() > 0)
			return spaces[to.getX()][to.getY()+1];
		else if (chosenFrom.getX()-to.getX() == 0 && chosenFrom.getY()-to.getY() < 0)
			return spaces[to.getX()][to.getY()-1];
		else if (chosenFrom.getX()-to.getX() > 0 && chosenFrom.getY()-to.getY() == 0)
			return spaces[to.getX()+1][to.getY()];
		else if (chosenFrom.getX()-to.getX() < 0 && chosenFrom.getY()-to.getY() == 0)
			return spaces[to.getX()-1][to.getY()];
		else if (chosenFrom.getX()-to.getX() > 0 && Math.abs(chosenFrom.getY()-to.getY()) == 2)
			return spaces[to.getX()+1][to.getY()+1];
		else if (chosenFrom.getX()-to.getX() < 0 && Math.abs(chosenFrom.getY()-to.getY()) == 2)
			return spaces[to.getX()-1][to.getY()-1];
		else
			return null;
	}

	//ArrayLists updated reflecting a jump
	public void updateArrayLists(Space to, Space chosenFrom, Space over){
		updateEmptySpaces(to, chosenFrom, over);
		updateFilledSpaces(to, chosenFrom, over);
		updatePotentialMoveSpaces();		
	}

	public void updateEmptySpaces(Space to, Space chosenFrom, Space over){
		emptySpaces.add(chosenFrom);
		emptySpaces.add(over);
		emptySpaces.remove(to);
	}

	public void updateFilledSpaces(Space to, Space chosenFrom, Space over){
		filledSpaces.remove(chosenFrom);
		filledSpaces.remove(over);
		filledSpaces.add(to);
	}

	public void updatePotentialMoveSpaces(){
		potentialMoveSpaces.clear(); //reset every time, because a potentialMoveSpace can change to an Empty Space after a jump
		for(Space s: emptySpaces){
				if((s.getY()-2 >= 0 && spaces[s.getX()][s.getY()-2].getStatus() == 1 && spaces[s.getX()][s.getY()-1].getStatus()==1)
				|| (s.getY()+2 < cols && spaces[s.getX()][s.getY()+2].getStatus() == 1 && spaces[s.getX()][s.getY()+1].getStatus()==1)
				|| (s.getX()-2 >= 0 && spaces[s.getX()-2][s.getY()].getStatus() == 1 && spaces[s.getX()-1][s.getY()].getStatus()==1)
				|| (s.getX()+2 < rows && spaces[s.getX()+2][s.getY()].getStatus() == 1 && spaces[s.getX()+1][s.getY()].getStatus()==1)) //conditions necessary for an empty Space to be able to be jumped into
				potentialMoveSpaces.add(s);
				if(type.equals("T") && !potentialMoveSpaces.contains(s)){ //Two more check for Triangular board
					if ((s.getY()-2 >= 0 && s.getX()-2 >= 0 && spaces[s.getX()-2][s.getY()-2].getStatus()==1 && spaces[s.getX()-1][s.getY()-1].getStatus()==1)
					|| (s.getY()+2 < cols && s.getX()+2 < rows && spaces[s.getX()+2][s.getY()+2].getStatus()==1 && spaces[s.getX()+1][s.getY()+1].getStatus()==1))
					potentialMoveSpaces.add(s);
				}
		}
	}

	//Used to convert x and y values to common notation, e.g. 0 2 becomes c1
	public String shorthandSpace(Space s){
		int x = s.getX()+1;
		int y = s.getY();
		return whichLetter(y)+x;
	}

	//Finds letter based on y value. Works up to 9 columns
	public String whichLetter(int y){
		switch(y){
			case 0:
				return "a";
			case 1:
				return "b";
			case 2:
				return "c";
			case 3:
				return "d";
			case 4:
				return "e";
			case 5:
				return "f";
			case 6:
				return "g";
			case 7:
				return "h";
			case 8:
				return "i";
			default:
				return Integer.toString(y);
		}
	}

	//Path to be printed at the end, Spaces involved in the jump added each move
	public void updatePath(Space to, Space chosenFrom){
		path += shorthandSpace(chosenFrom)+"-"+shorthandSpace(to);
		if(potentialMoveSpaces.size() != 0){
			path+= ", ";
		}
	}

	//For debugging
	public void printMovingSpaces(Space to, Space chosenFrom, Space over){
		System.out.println("From: "+ shorthandSpace(chosenFrom) + " to: " + shorthandSpace(to) + " Over: " + shorthandSpace(over));
	}

	//Used for debugging, prints all possible jumps at a given state
	public void printPossibleJumps(List<Space> jumpsFrom, List<Space> jumpsTo){
		for(int i = 0; i <jumpsFrom.size(); i++){
			System.out.print(shorthandSpace(jumpsFrom.get(i))+"-"+shorthandSpace(jumpsTo.get(i)));
			if(i != jumpsFrom.size()-1){
				System.out.print(", ");
			}
			System.out.println();
		}
	}

	//the current SAX count is added to an ArrayList
	public void updateSAX(int SAX){
		SAXCounts.add(SAX);
	}

	//a trial repeats the move() method until we cannot move anymore
	public void trial(){
		while(potentialMoveSpaces.size()>0){ //no more moves if size of potentialMoveSpaces is 0.
			move();
		}
	}

	public boolean isVictory(){
		return filledSpaces.size() == 1;
	}

	//prints the board a state and the jump that got to that state
	public void printBoard(Integer[] state, String[] splitPath, int index){
		if(index != 0)
			System.out.println(splitPath[index]); //which jump
		for(int i = 0; i < state.length; i++){
			if(i%cols == 0 && type.equals("T")){
				for (int j = i/rows; j < rows; j++)
					System.out.print(' ');
			}
			if(state[i] == -1)
				System.out.print(' ');
			if(state[i] == 1)
				System.out.print('.');
			if(state[i] == 0)
				System.out.print('o');
			System.out.print(' ');
			if (i%cols == cols-1)
				System.out.println();
		}
		if(!board.isT55()){
			System.out.println();
		}
		
	}

	//Prints the board at every state, followed by the path taken in the game
	public void printResult(){
		String[] splitPath = path.split("[:,]"); //path split to find the jump at a state
		for(Integer[] state: states){
			System.out.println(states.indexOf(state)); //jump number
			printBoard(state, splitPath, states.indexOf(state)); //prints board at a state
			if(board.isT55()){
				System.out.println("SAX Count: "+SAXCounts.get(states.indexOf(state))); //print SAX count at a state as well
				System.out.println();
			}
		}
		if(rows > 9)
			System.out.println("NOTE: Path will not display properly because number of rows is too large.");
		System.out.println(path);
		if (isVictory())
			System.out.println("YOU WIN");
		else
			System.out.println("You still have " + filledSpaces.size() + " pegs remaining. You're an egg-nor-a-moose");
	}
}