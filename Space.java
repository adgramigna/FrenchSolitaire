public class Space{

	private int x;
	private int y;
	private int status;

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

	public void setStatus(int newStatus){
		status = newStatus;
	}

	public String toString(){
		return "X: "+x+ " Y: "+y+ " status: "+status;
	}
}