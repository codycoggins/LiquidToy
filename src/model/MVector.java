package model;

/**
 * A 2 dimensional Vector implemented in an immutable strategy
 * @author c.coggins
 *
 */
public final class MVector extends java.lang.Object {
	final private double x;
	final private double y;

	public MVector(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public MVector add(MVector b){
		return new MVector(x + b.getX(),y + b.getY());
	}

	public MVector delta(MVector b){
		return new MVector(x - b.getX(),y - b.getY());
	}

	public MVector multiply(double c){
		return new MVector(x*c, y*c);
	}

	public MVector divide(double d){
		return new MVector(x/d, y/d);
	}

	public double length(){
		return (Math.sqrt(x*x + y*y) );
	}

	public MVector unitVector() {
		return this.divide(this.length());
	}

	@Override
	public String toString() {
		return "MVector [x=" + x + ", y=" + y + "]";
	}
}
