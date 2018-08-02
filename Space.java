//Andrew Gramigna, 2018

public class Space{

	private int x; //row
	private int y; //column
	private int status; //-1, 0 ,1 (non-existent, empty, filled)

	public Space(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getStatus(){
		return status;
	}

	public void setStatus(int newStatus){ //switch from filled to empty during game
		status = newStatus;
	}

	public String toString(){
		return "X: "+x+ " Y: "+y+ " status: "+status;
	}
}