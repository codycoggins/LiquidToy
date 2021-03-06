package model;

import fxApplication.GameObject;

public class ModelGlider extends ModelObject {

	public ModelGlider(World world, MVector velocity, MVector position, double radius) {
		super(world, velocity, position, radius);
		System.out.println("Created new " + this.toString());
	}

	@Override
	public synchronized void collisionWith(ModelObject o){
		super.collisionWith(o);
		// getting hit pushes the glider

		this.velocity = o.velocity;

	}

	@Override
	protected synchronized MVector updateVelocity(float dt) {
		super.updateVelocity(dt);
		// Avoid
		ModelObject nearObj = world.nearest(this);
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
		return "ModelGlider [position=" + position + ", velocity=" + velocity + ", maxSpeed=" + maxSpeed + ", name="
				+ name + "]\n";
	}

}
