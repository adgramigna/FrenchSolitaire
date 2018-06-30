import java.util.ArrayList;
import java.util.List;

public class PSGame{

	private FSBoard board;
	private Space[][] spaces;
	private int rows;
	private int cols;
	private String type;
	private int SAX;
	private int moveNumber;
	private List<Space> emptySpaces;
	private List<Space> filledSpaces;
	private List<Space> potentialMoveSpaces;
	private List<Integer[]> states;
	private List<Integer> SAXCounts;
	private String path;
	
	public PSGame(FSBoard board){
		this.board = board;
		spaces = board.getSpaces();
		rows = board.getRows();
		cols = board.getCols();
		type = board.getType();
		SAX = board.calculateSAX();
		moveNumber = 0;
		emptySpaces = new ArrayList<Space>();
		filledSpaces = new ArrayList<Space>();
		potentialMoveSpaces = new ArrayList<Space>();
		states = new ArrayList<Integer[]>();
		SAXCounts = new ArrayList<Integer>();
		path = "Path: ";
	}

	public void initialize(){
		initializeArrayLists();
		updateStates();
		updateSAX(SAX);
	}

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
				state[i*rows+j] = status;
			}
		}
		states.add(state);
		//printBoard(state);
	}

	public void move(){
		Space chosenFrom, to, over;
		int prevSAX = SAXCounts.get(SAXCounts.size()-1);
		int currSAX = prevSAX;
		List<Space> jumpsFrom = new ArrayList<Space>();
		List<Space> jumpsTo = new ArrayList<Space>();
		
		fillJumps(jumpsFrom, jumpsTo);

		do{
			int rand = (int)(Math.random()*jumpsFrom.size());
			chosenFrom = jumpsFrom.get(rand);
			to = jumpsTo.get(rand);
			over = findOver(to, chosenFrom);
			printPossibleJumps(jumpsFrom, jumpsTo);

			printMovingSpaces(to, chosenFrom, over);

			updateUponMove(to, chosenFrom, over);
			currSAX = board.calculateSAX();
			if (currSAX < prevSAX && jumpsFrom.size() > 1){
				undoUpdateUponMove(to, chosenFrom, over);
				removePossibleJump(jumpsFrom, jumpsTo, rand);
			}
			else
				break;
			//break; //Uncomment when not using SAX count in T55.
		}while(true);
		
		updateArrayLists(to, chosenFrom, over);
		updatePath(to, chosenFrom);
		
		updateStates();
		updateSAX(currSAX);
	}

	public void updateUponMove(Space to, Space chosenFrom, Space over){
		chosenFrom.setStatus(0);
		over.setStatus(0);
		to.setStatus(1);
	}

	public void undoUpdateUponMove(Space to, Space chosenFrom, Space over){
		chosenFrom.setStatus(1);
		over.setStatus(1);
		to.setStatus(0);
	}

	public void removePossibleJump(List<Space> jumpsFrom, List<Space> jumpsTo, int index){
		jumpsFrom.remove(index);
		jumpsTo.remove(index);
	}

	public void findFrom(Space to, List<Space> from){
		if (to.getX()-2 >= 0 && spaces[to.getX()-2][to.getY()].getStatus()==1 && spaces[to.getX()-1][to.getY()].getStatus()==1)
			from.add(spaces[to.getX()-2][to.getY()]);
		if (to.getX()+2 < rows && spaces[to.getX()+2][to.getY()].getStatus()==1 && spaces[to.getX()+1][to.getY()].getStatus()==1)
			from.add(spaces[to.getX()+2][to.getY()]);
		if (to.getY()-2 >= 0 && spaces[to.getX()][to.getY()-2].getStatus()==1 && spaces[to.getX()][to.getY()-1].getStatus()==1)
			from.add(spaces[to.getX()][to.getY()-2]);
		if (to.getY()+2 < cols && spaces[to.getX()][to.getY()+2].getStatus()==1 && spaces[to.getX()][to.getY()+1].getStatus()==1)
			from.add(spaces[to.getX()][to.getY()+2]);
		if(type.equals("T")){
			if (to.getY()-2 >= 0 && to.getX()-2 >= 0 && spaces[to.getX()-2][to.getY()-2].getStatus()==1 && spaces[to.getX()-1][to.getY()-1].getStatus()==1)
				from.add(spaces[to.getX()-2][to.getY()-2]);
			if (to.getY()+2 < cols && to.getX()+2 < rows && spaces[to.getX()+2][to.getY()+2].getStatus()==1 && spaces[to.getX()+1][to.getY()+1].getStatus()==1)
				from.add(spaces[to.getX()+2][to.getY()+2]);
		}
	}

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

	public void fillJumps(List<Space> jumpsFrom, List<Space> jumpsTo){
		for(Space p: potentialMoveSpaces){
			List<Space> from = new ArrayList<Space>();
			findFrom(p, from);
			for(Space f: from){
				jumpsFrom.add(f);
				jumpsTo.add(p);
			}
		}
	}

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
		potentialMoveSpaces.clear();
		for(Space s: emptySpaces){
				if((s.getY()-2 >= 0 && spaces[s.getX()][s.getY()-2].getStatus() == 1 && spaces[s.getX()][s.getY()-1].getStatus()==1)
				|| (s.getY()+2 < cols && spaces[s.getX()][s.getY()+2].getStatus() == 1 && spaces[s.getX()][s.getY()+1].getStatus()==1)
				|| (s.getX()-2 >= 0 && spaces[s.getX()-2][s.getY()].getStatus() == 1 && spaces[s.getX()-1][s.getY()].getStatus()==1)
				|| (s.getX()+2 < rows && spaces[s.getX()+2][s.getY()].getStatus() == 1 && spaces[s.getX()+1][s.getY()].getStatus()==1))
				potentialMoveSpaces.add(s);
				if(type.equals("T") && !potentialMoveSpaces.contains(s)){
					if ((s.getY()-2 >= 0 && s.getX()-2 >= 0 && spaces[s.getX()-2][s.getY()-2].getStatus()==1 && spaces[s.getX()-1][s.getY()-1].getStatus()==1)
					|| (s.getY()+2 < cols && s.getX()+2 < rows && spaces[s.getX()+2][s.getY()+2].getStatus()==1 && spaces[s.getX()+1][s.getY()+1].getStatus()==1))
					potentialMoveSpaces.add(s);
				}
		}
	}

	public String shorthandSpace(Space s){
		int x = s.getX()+1;
		int y = s.getY();
		return whichLetter(y)+x;
	}

	public void updatePath(Space to, Space chosenFrom){
		path += shorthandSpace(chosenFrom)+"-"+shorthandSpace(to);
		if(potentialMoveSpaces.size() != 0){
			path+= ", ";
		}
	}

	public void printMovingSpaces(Space to, Space chosenFrom, Space over){
		System.out.println("From: "+ shorthandSpace(chosenFrom) + " to: " + shorthandSpace(to) + " Over: " + shorthandSpace(over));
	}

	public void printPossibleJumps(List<Space> jumpsFrom, List<Space> jumpsTo){
		for(int i = 0; i <jumpsFrom.size(); i++){
			System.out.print(shorthandSpace(jumpsFrom.get(i))+"-"+shorthandSpace(jumpsTo.get(i)));
			if(i != jumpsFrom.size()-1){
				System.out.print(", ");
			}
			System.out.println();
		}
	}

	public void updateSAX(int SAX){
		SAXCounts.add(SAX);
	}

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

	public FSBoard getBoard(){
		return board;
	}

	public void trial(){
		while(potentialMoveSpaces.size()>0){
			System.out.println(++moveNumber);
			System.out.println();
			move();
		}
	}

	public boolean isVictory(){
		return filledSpaces.size() == 1;
	}

	public void printSizes(){
		System.out.println("Empty Size:" + emptySpaces.size());
		System.out.println("Filled Size:" + filledSpaces.size());
		System.out.println("Move Size:" + potentialMoveSpaces.size());
	}

	public void printBoard(Integer[] state, String[] splitPath, int index){
		if(index != 0)
			System.out.println(splitPath[index]);
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

	public void printResult(){
		String[] splitPath = path.split("[:,]");
		for(Integer[] state: states){
			System.out.println(states.indexOf(state));
			printBoard(state, splitPath, states.indexOf(state));
			if(board.isT55()){
				System.out.println("SAX Count: "+SAXCounts.get(states.indexOf(state)));
				System.out.println();
			}
		}
		System.out.println(path);
		printSizes();
		if (isVictory())
			System.out.println("YOU WIN");
		else
			System.out.println("You're an egg-nor-a-moose");
	}
}