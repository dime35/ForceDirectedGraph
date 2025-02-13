public class Vector {
	private final float x;
	private final float y;

	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float x() {
		return x;
	}
	
	public float y() {
		return y;
	}

	public float norm () {
		return (float) Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));
	}

	public Vector plus(Vector v2) {
		return new Vector(x + v2.x, y + v2.y);
	}
	
	public Vector minus(Vector v2) {
		return new Vector(x - v2.x, y - v2.y);
	}

	public Vector times(float d) {
		return new Vector(x * d, y *d);
	}
	
	public static float dotProduct(Vector v1, Vector v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}
	public static Vector normalize(Vector v) {
		float mag = v.norm();
		return v.times (1.0f / mag);
	}

	public static Vector zero() {
		return new Vector(0, 0);
	}

	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	


}
