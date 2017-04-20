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
	protected synchronized MVector updateVelocity(float dt) {
		super.updateVelocity(dt);
		// Avoid
		ModelObject nearObj = world.nearest(this);
		if (nearObj.getClass()== ModelGlider.class) return velocity;
		if (nearObj != null){
			if (position.delta(nearObj.getPosition()).length() < AVOID_THRESHOLD) {
				System.out.print(getName() + " is avoiding " + nearObj.getName());
				MVector escapeVector = position.delta(nearObj.getPosition());
				setVelocity(escapeVector.unitVector().multiply(maxSpeed));
			}
		}
		return velocity;
	}
	@Override
	public String toString() {
		return "ModelShip [position=" + position + ", velocity=" + velocity + ", maxSpeed=" + maxSpeed + ", name="
				+ name + "]\n";
	}

}
