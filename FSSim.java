public class FSSim{
	public static void main(String[] args){
		FSBoard board = new FSBoard(Integer.parseInt(args[1]),Integer.parseInt(args[1]));
		board.initialize(args[0]);
		board.makeInitialEmpty(Integer.parseInt(args[2]),Integer.parseInt(args[3]));
		board.play();

	}
}