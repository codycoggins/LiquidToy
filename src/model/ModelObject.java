package model;

import java.util.LinkedList;
import java.util.List;

import fxApplication.GameObject;

public abstract class ModelObject {
	protected MVector position; //(meters)
	protected MVector velocity; //(meters/second)
	protected List<MVector> waypoints = new LinkedList<MVector>();
	protected double maxSpeed = 0;
	protected static final double AVOID_THRESHOLD = 1;
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

	public synchronized void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public String getName() {
		return name;
	}

	public synchronized void setName(String name) {
		this.name = name;
	}

	public World getWorld() {
		return world;
	}

	public synchronized void setWorld(World world) {
		this.world = world;
	}

	public MVector getVelocity() {
		return velocity;
	}

	public synchronized void setVelocity(MVector velocity) {
		// reduce velocity to maximum speed if needed.
		if (velocity.length()> maxSpeed){
//			System.out.println (this.name + " has a maximum speed of " + this.maxSpeed);
			velocity = velocity.unitVector().multiply(maxSpeed);
		}
		this.velocity = velocity;
	}

	public synchronized void setPosition(MVector position) {
		this.position = position;
	}

	public synchronized void updatePosition(float dt) {

		position = position.add(velocity.multiply(dt));
		doBounce();
		updateVelocity(dt);
//		System.out.println("updatePosition("+dt+") on " + toString());
	}

	protected synchronized MVector updateVelocity(float dt){
		// Put highest priority rules at end.
		// TODO: implement a Strategy design pattern
		// TODO: limit acceleration for smoother steering.
		if (hasWaypoints()){
			// steer object towards waypoints
			setVelocity (waypoints.get(0).delta(position) );
//			System.out.println(getName()+  "waypoint new velocity=" + velocity);
		}
		doBounce();
		return velocity;
	}

	private synchronized void doBounce(){
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

	public synchronized void addWaypoint(MVector v){
		waypoints.add(v);
	}

	public synchronized void clearWaypoints(){
		waypoints.clear();
	}

	/**
	 * Clear any existing waypoints and set new one.
	 *
	 * @param v New Destination
	 */
	public synchronized void setWaypoint(MVector w){
		System.out.print(this.getName() + ": new waypoint (model) " + w);
		clearWaypoints();
		addWaypoint(w);
	}

	public boolean hasWaypoints(){
		if (waypoints.size()> 0 ) return true;
		return false;
	}

	public MVector popWaypoint(){
		return waypoints.remove(0);
	}

	public boolean isWaypointAchieved(){
		if (!hasWaypoints()) return false;
		if (waypoints.get(0).delta(position).length() < radius) return true;
		return false;
	}
	public MVector getPosition() {
		return position;
	}

	public double getRadius() {
		return radius;
	}

	public synchronized void setRadius(double radius) {
		this.radius = radius;
	}

	public GameObject getGameObject() {
		return gameObject;
	}

	public synchronized void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	public synchronized void collisionWith(ModelObject o){
	}

	@Override
	public String toString() {
		return "ModelObject [name="	+ name + ",position=" + position + ", velocity=" + velocity +
				", maxSpeed=" + maxSpeed + "]";
	}
}
