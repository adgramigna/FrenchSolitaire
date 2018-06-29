import java.util.ArrayList;
import java.util.List;

//As is: One trial
//Uncomment once: Trial until you win
//Uncomment twice, comment out break statement line 25: Runs numTrials times with statistics.

public class FSSim{
	public static void main(String[] args){
		// int wins = 0;
		// int losses = 0;
		// int numTrials = 10000;
		// for(int i = 1; i<=numTrials; i++){
		// 	if(i%1000000 == 0)
		// 		System.out.println(i);
		// 	FSBoard board = new FSBoard(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[1]));
		// 	board.setup(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		// 	PSGame game = new PSGame(board);
		// 	game.initialize();
		// 	game.trial();
		// 	if(game.isVictory()){
		// 		game.printResult();
		// 		wins++;
		// 		//System.out.println("win "+i+" "+wins);
		// 		break;
		// 	}
		// 	else
		// 		losses++;
		// 	}
		// 	// System.out.println(wins+" "+losses);
		// 	// System.out.println(((double)(wins)/numTrials));
		// 	// System.out.println("We expect a victory roughly every "+numTrials/wins + " games!");
		
		FSBoard board = new FSBoard(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[1]));
		board.setup(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		PSGame game = new PSGame(board);
		game.initialize();
		game.trial();
		game.printResult();
	}
}