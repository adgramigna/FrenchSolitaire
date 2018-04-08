public class FSSim{
	public static void main(String[] args){
		int wins = 0;
		int losses = 0;
		// for(int i = 0; i<200000000; i++){
		// 	FSBoard board = new FSBoard(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[1]));
		// 	board.setup(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		// 	PSGame game = new PSGame(board);
		// 	game.initializeArrayLists();
		// 	game.trial();
		// 	if(game.isVictory()){
		// 		game.printResult();
		// 		wins++;
		// 		break;
		// 	}
		// 	else
		// 		losses++;
		// }
		FSBoard board = new FSBoard(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[1]));
		board.setup(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		PSGame game = new PSGame(board);
		game.initialize();
		game.trial();
		game.printResult();
		System.out.println(wins+" "+losses);
	}
}