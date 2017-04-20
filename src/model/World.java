package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class World {

	float sizeX = 300;
	float sizeY = 300;

	protected List<ModelObject> objects = new LinkedList<ModelObject>();

	public World (float width, float height) {
		System.out.println("World Constuctor");
		this.sizeX = width;
		this.sizeY = height;
	}

    /*
     * Updates the position of all the model objects in the game based on their physics state.
     */
	public void step(float dt) {
//		System.out.println("World.step("+dt+") will update "+ objects.size() + " objects." );
		objects.forEach((o)->o.updatePosition(dt));
		Map<ModelObject,ModelObject> collisions = checkCollisions();
	}

    public void addObject(ModelObject o){
    	objects.add(o);
    }
    public void clearObjects(){objects.clear();}

	@Override
	public String toString() {
		return "World [sizeX=" + sizeX + ", sizeY=" + sizeY + ", objects=" + objects + "]";
	}
	public float getSizeX() {
		return sizeX;
	}

	public void setSizeX(float sizeX) {
		this.sizeX = sizeX;
	}

	public float getSizeY() {
		return sizeY;
	}

	public void setSizeY(float sizeY) {
		this.sizeY = sizeY;
	}
	public Map<ModelObject,ModelObject> checkCollisions(){
		Map<ModelObject,ModelObject> collisions = new HashMap<ModelObject,ModelObject>();
		ModelObject o1=null;
		ModelObject o2=null;
		for(int i=0; i < objects.size(); i++) {
			o1=objects.get(i);
			for(int j=i+1; j < objects.size(); j++) {
				o2=objects.get(j);
				if (o1==o2) continue;
				MVector delta = o1.getPosition().delta(o2.getPosition());
				if (delta.length()< (o1.radius+ o2.radius )) {
//					System.out.println("Collision!! "+ o1.name + " and " + o2.name + " distance " + delta.length());
					collisions.put(o1, o2);
					o1.collisionWith(o2);
					o2.collisionWith(o1);
				}
			}
		}
		return collisions;
	}
	public ModelObject nearest(MVector x){
		double minDistance = (new MVector(sizeX,sizeY).length()); //max possible distance in world
		ModelObject near = null;
		for (ModelObject o: objects){
			double d = x.delta(o.position).length();
			if (d < minDistance){
				minDistance = d;
				near = o;
			}
		}
		if (near!=null){
			System.out.println("You are "+ minDistance + " from " + near.getName());
		}
		return near;
	}
}
