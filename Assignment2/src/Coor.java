package maze;


//For the convenient of comparison and hash the coordinates
public class Coor {
	public int x, y;
		
	Coor(int _x, int _y){
		x = _x;
		y = _y;
	}
		
	Coor(Coor other){
		x = other.x;
		y = other.y;
	}
		
	@Override
	public boolean equals(Object other) {
		return x == ((Coor) (other)).x && y == ((Coor) (other)).y;
	}

	@Override
	public int hashCode() {
		return 37 * x + y;
	}
}
