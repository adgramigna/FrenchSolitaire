//Update numTrials to run a different number of trials

public class FSSim{
	public static void main(String[] args){
		int wins = 0;
		int losses = 0;
		int numTrials = 1; //change to 1 to test just one trial
		for(int i = 1; i<=numTrials; i++){
			if(i%1000000 == 0)
				System.out.println(i); //to see how close we are to finishing; runtime high with large number of trials
			FSBoard board = new FSBoard(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[1])); //F 7, for example
			board.setup(Integer.parseInt(args[2]), Integer.parseInt(args[3])); //initalEmptySpace location, 0 2 for example.
			PSGame game = new PSGame(board);
			game.initialize();
			game.trial();
			game.printResult(); //printResult every trial when uncommented
			if(game.isVictory()){
				//game.printResult(); //only printResult on a victory
				wins++;
				//break; //uncomment if only running until one victory achieved
			}
			else
				losses++;
		}
		System.out.println(wins+" "+losses);
		System.out.println(((double)(wins)/numTrials));
		try{
			System.out.println("We expect a victory roughly every "+Math.round((double)((numTrials)/(wins))) + " games!");	
		}
		catch (ArithmeticException e){
			System.out.println("ERROR: you are trying to "+e.getMessage()+ ". Perhaps you never won...");
		}
	}
}