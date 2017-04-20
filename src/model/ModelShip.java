package model;

public class ModelShip extends ModelObject {

	public ModelShip(World world, MVector velocity, MVector position, double radius) {
		super(world, velocity, position, radius);
		System.out.println("Created new " + this.toString());
	}


//	@Override
//	public void interpolatePosition(float alpha) {
//
//	}
//
//	@Override
//	public void updatePosition() {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public String toString() {
		return "ModelShip [position=" + position + ", velocity=" + velocity + ", maxSpeed=" + maxSpeed + ", name="
				+ name + "]\n";
	}

}
