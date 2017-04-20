package model;

import fxApplication.GameObject;

public abstract class ModelObject {
	protected MVector position; //(meters)
	protected MVector velocity; //(meters/second)
	protected double maxSpeed = 0;
	protected double radius = 0.1;
	protected String name = "ModelObject Instance";
	protected World world = null;
	protected GameObject gameObject = null;

	public ModelObject(World world, MVector velocity, MVector position, double radius) {
		super();
		this.velocity = velocity;
		this.position = position;
		this.world = world;
		this.radius = radius;
		world.addObject(this);
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public MVector getVelocity() {
		return velocity;
	}

	public void setVelocity(MVector velocity) {
		if (velocity.length()> maxSpeed){
			System.out.println (this.name + " has a maximum speed of " + this.maxSpeed);
			velocity = velocity.unitVector().multiply(maxSpeed);
		}
		this.velocity = velocity;
	}

	public void setPosition(MVector position) {
		this.position = position;
	}

	public void updatePosition(float dt) {
		position = position.add(velocity.multiply(dt));
		doBounce();
//		System.out.println("updatePosition("+dt+") on " + toString());
	}

	private void doBounce(){
		if (position.getX()<0 && velocity.getX()<0) {
			velocity = new MVector(- velocity.getX(), velocity.getY());
		}
		if (position.getY()<0 && velocity.getY()<0) {
			velocity = new MVector(velocity.getX(), - velocity.getY());
		}
		if (position.getX()> world.getSizeX() && velocity.getX()>0) {
			velocity = new MVector(- velocity.getX(), velocity.getY());
		}
		if (position.getY()> world.getSizeY() && velocity.getY()>0) {
			velocity = new MVector(velocity.getX(), -velocity.getY());
		}
	}

	public MVector getPosition() {
		return position;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public GameObject getGameObject() {
		return gameObject;
	}

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	public void collisionWith(ModelObject o){
	}

	@Override
	public String toString() {
		return "ModelObject [name="	+ name + ",position=" + position + ", velocity=" + velocity +
				", maxSpeed=" + maxSpeed + "]";
	}
}
