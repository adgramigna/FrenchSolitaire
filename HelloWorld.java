public class HelloWorld{
	public static void main(String[] args){
		System.out.println("Hello, World!");
		FSBoard board = new FSBoard(7,7);
		board.initialize();
		board.printBoard();
	}
}