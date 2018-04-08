import java.util.ArrayList;
import java.util.List;

public class PSGame{

	private FSBoard board;
	private Space[][] spaces;
	private int rows;
	private int cols;
	private String type;
	private List<Space> emptySpaces;
	private List<Space> filledSpaces;
	private List<Space> potentialMoveSpaces;
	private List<Integer[]> states;
	
	public PSGame(FSBoard board){
		this.board = board;
		spaces = board.getSpaces();
		rows = board.getRows();
		cols = board.getCols();
		type = board.getType();
		emptySpaces = new ArrayList<Space>();
		filledSpaces = new ArrayList<Space>();
		potentialMoveSpaces = new ArrayList<Space>();
		states = new ArrayList<Integer[]>();
	}

	public void initialize(){
		initializeArrayLists();
		updateStates();
	}

	public void initializeArrayLists(){
		for (int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				Space space = spaces[i][j];
				int value = space.getValue();
				if(value == 0){
					emptySpaces.add(space);
					potentialMoveSpaces.add(space);
				}
				else if(value == 1){
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
				int value = space.getValue();
				state[i*rows+j] = value;
			}
		}
		states.add(state);
	}

	public void move(){
		int rand = (int)(Math.random()*potentialMoveSpaces.size());
		Space to = potentialMoveSpaces.get(rand);
		List<Space> from = new ArrayList<Space>();
		findFrom(to, from);
		int rand2 = (int)(Math.random()*from.size());
		Space chosenFrom = from.get(rand2);

		// System.out.println(to.toString());
		// System.out.println(chosenFrom.toString());
		Space over = findOver(to, chosenFrom);

		chosenFrom.setValue(0);
		over.setValue(0);
		to.setValue(1);

		updateArrayLists(to, over, chosenFrom);
		
		updateStates();
		//printSizes();
	}

	public void findFrom(Space to, List<Space> from){
		if (to.getX()-2 >= 0 && spaces[to.getX()-2][to.getY()].getValue()==1 && spaces[to.getX()-1][to.getY()].getValue()==1)
			from.add(spaces[to.getX()-2][to.getY()]);
		if (to.getX()+2 < rows && spaces[to.getX()+2][to.getY()].getValue()==1 && spaces[to.getX()+1][to.getY()].getValue()==1)
			from.add(spaces[to.getX()+2][to.getY()]);
		if (to.getY()-2 >= 0 && spaces[to.getX()][to.getY()-2].getValue()==1 && spaces[to.getX()][to.getY()-1].getValue()==1)
			from.add(spaces[to.getX()][to.getY()-2]);
		if (to.getY()+2 < cols && spaces[to.getX()][to.getY()+2].getValue()==1 && spaces[to.getX()][to.getY()+1].getValue()==1)
			from.add(spaces[to.getX()][to.getY()+2]);
		if(type.equals("T")){
			if (to.getY()-2 >= 0 && to.getX()-2 >= 0 && spaces[to.getX()-2][to.getY()-2].getValue()==1 && spaces[to.getX()-1][to.getY()-1].getValue()==1)
				from.add(spaces[to.getX()-2][to.getY()-2]);
			if (to.getY()+2 < cols && to.getX()+2 < rows && spaces[to.getX()+2][to.getY()+2].getValue()==1 && spaces[to.getX()+1][to.getY()+1].getValue()==1)
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

	public void updateArrayLists(Space to, Space chosenFrom, Space over){
		emptySpaces.add(over);
		emptySpaces.add(chosenFrom);
		filledSpaces.add(to);
		filledSpaces.remove(over);
		filledSpaces.remove(chosenFrom);
		emptySpaces.remove(to);
		potentialMoveSpaces.clear();
		for(Space s: emptySpaces){
				if((s.getY()-2 >= 0 && spaces[s.getX()][s.getY()-2].getValue() == 1 && spaces[s.getX()][s.getY()-1].getValue()==1)
				|| (s.getY()+2 < cols && spaces[s.getX()][s.getY()+2].getValue() == 1 && spaces[s.getX()][s.getY()+1].getValue()==1)
				|| (s.getX()-2 >= 0 && spaces[s.getX()-2][s.getY()].getValue() == 1 && spaces[s.getX()-1][s.getY()].getValue()==1)
				|| (s.getX()+2 < rows && spaces[s.getX()+2][s.getY()].getValue() == 1 && spaces[s.getX()+1][s.getY()].getValue()==1))
				potentialMoveSpaces.add(s);
				if(type.equals("T")){
					if ((s.getY()-2 >= 0 && s.getX()-2 >= 0 && spaces[s.getX()-2][s.getY()-2].getValue()==1 && spaces[s.getX()-1][s.getY()-1].getValue()==1)
					|| (s.getY()+2 < cols && s.getX()+2 < rows && spaces[s.getX()+2][s.getY()+2].getValue()==1 && spaces[s.getX()+1][s.getY()+1].getValue()==1))
					potentialMoveSpaces.add(s);
				}
		}
		
	}

	public FSBoard getBoard(){
		return board;
	}

	public void trial(){
		//printAll();
		while(potentialMoveSpaces.size()>0){
			move();
			//printAll();
		}
		//printResult();
	}

	public boolean isVictory(){
		return filledSpaces.size() == 1;
	}

	public void printSpaces(){
		// for(int i=0; i<emptySpaces.size(); i++){
		// 	System.out.println("Empty: "+i+" "+emptySpaces.get(i).toString());
		// }
		// System.out.println();
		// for(int i=0; i<filledSpaces.size(); i++){
		// 	System.out.println("Fill: "+i+" "+filledSpaces.get(i).toString());
		// }
		// System.out.println();
		for(int i=0; i<potentialMoveSpaces.size(); i++){
			System.out.println("Move: "+i+" "+potentialMoveSpaces.get(i).toString());
		}
		System.out.println();
		// for(int i=0; i<states.size(); i++){
		// 	for(int j = 0; j<states.get(i).length; j++){
		// 		System.out.println("States: "+i+" "+j+" "+states.get(i)[j]);
		// 	}
		// 	System.out.println();
		// }
	}

	public void printSizes(){
		System.out.println("Empty Size:" + emptySpaces.size());
		System.out.println("Filled Size:" + filledSpaces.size());
		System.out.println("Move Size:" + potentialMoveSpaces.size());
	}

	public void printBoard(Integer[] state){
		for(int i = 0; i < state.length; i++){
			if(i%cols == 0 && type.equals("T")){
				for (int j = i/rows; j <= rows/2+1; j++)
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
		System.out.println();
	}

	public void printResult(){
		for(Integer[] state: states){
			printBoard(state);
		}
		printSizes();
		if (isVictory())
			System.out.println("YOU WIN");
		else
			System.out.println("You're an egg-nor-a-moose");
	}

	public void printAll(){
		printSpaces();
		printSizes();
		printBoard(states.get(states.size()-1));
	}
}