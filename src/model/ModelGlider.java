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
	public String toString() {
		return "ModelGlider [position=" + position + ", velocity=" + velocity + ", maxSpeed=" + maxSpeed + ", name="
				+ name + "]\n";
	}

}
