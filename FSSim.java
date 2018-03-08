public class FSSim{
	public static void main(String[] args){
		int wins = 0;
		int losses = 0;
		for(int i = 0; i<1000000; i++){
			FSBoard board = new FSBoard(Integer.parseInt(args[1]),Integer.parseInt(args[1]));
			board.setup(args[0], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
			board.trial();
			if(board.isVictory())
				wins++;
			else
				losses++;
		}
		System.out.println(wins+" "+losses);
	}
}