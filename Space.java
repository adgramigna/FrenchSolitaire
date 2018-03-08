public class Space{

	private int x;
	private int y;
	private int value;

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

	public int getValue(){
		return value;
	}

	public void setValue(int newVal){
		value = newVal;
	}

	public String toString(){
		return "X: "+x+ " Y: "+y+ " value: "+value;
	}
}