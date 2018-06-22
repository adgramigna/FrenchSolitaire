import java.util.ArrayList;
import java.util.List;

public class FSSim{
	public static void main(String[] args){
		// int wins = 0;
		// int losses = 0;
		// int numTrials = 1;
		// for(int i = 1; i<=numTrials; i++){
		// 	if(i%1000000 == 0)
		// 		System.out.println(i);
		// 	FSBoard board = new FSBoard(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[1]));
		// 	board.setup(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		// 	PSGame game = new PSGame(board);
		// 	game.initialize();
		// 	game.trial();
		// 	if(game.isVictory()){
		// 		//game.printResult();
		// 		wins++;
		// 		//System.out.println("win "+i+" "+wins);
		// 		//break;
		// 	}
		// 	else
		// 		losses++;
		// 	}
		// 	System.out.println(wins+" "+losses);
		// 	System.out.println(((double)(wins)/numTrials));
		// 	System.out.println("We expect a victory roughly every "+numTrials/wins + " games!");
		
		FSBoard board = new FSBoard(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[1]));
		board.setup(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		if(board.getType().equals("T") && board.getRows() == 5 && board.getCols() == 5){}
		PSGame game = new PSGame(board);
		game.initialize();
		game.trial();
		game.printResult();
	}
}