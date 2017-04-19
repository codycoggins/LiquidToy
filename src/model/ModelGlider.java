package model;

public class ModelGlider extends ModelObject {

	public ModelGlider(World world, MVector velocity, MVector position) {
		super(world, velocity, position);
		System.out.println("Created new " + this.toString());
	}


//	@Override
//	public void interpolatePosition(float alpha) {
//		// TODO Auto-generated method stub
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
		return "ModelGlider [position=" + position + ", velocity=" + velocity + ", maxSpeed=" + maxSpeed + ", name="
				+ name + "]\n";
	}

}
