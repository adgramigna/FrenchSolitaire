public class FSSim{
	public static void main(String[] args){
		FSBoard board = new FSBoard(Integer.parseInt(args[1]),Integer.parseInt(args[1]));
		if(args[0].equals("E"))
			board.initializeE();
		else
			board.initializeF();
		board.makeEmptySpot();
		board.printBoard();
	}
}