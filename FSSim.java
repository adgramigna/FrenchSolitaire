public class FSSim{
	public static void main(String[] args){
		FSBoard board = new FSBoard(Integer.parseInt(args[0]),Integer.parseInt(args[0]));
		board.initialize();
		board.makeEmptySpot();
		board.printBoard();
	}
}