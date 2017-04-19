package fxApplication;

import model.ModelObject;
import model.World;
import javafx.scene.layout.Pane;

import static fxApplication.Main.HEIGHT;
import static fxApplication.Main.SCALE;
import static fxApplication.Main.WIDTH;

import java.util.List;
import java.util.LinkedList;


public class Game {
	int numGliders = 0;
	int numShips = 0;
	World world = null;
	List<GameObject> objects = new LinkedList<GameObject>();

    void load(World world, Pane pane, int numGliders, int numShips){
    	this.numGliders = numGliders;
    	this.numShips = numShips;
    	objects.clear();
    	this.world = world;
    	for (int i=0; i<numGliders; i++){
    		GameObject o = addObject(new GameGlider(world));
    		o.setName("Glider"+i);
    		pane.getChildren().add(o);
    	}
    	for (int i=0; i<numShips; i++){
    		GameObject o = addObject(new GameShip(world));
    		o.setName("Ship"+i);
    		pane.getChildren().add(o);
    	}
    	System.out.println("Game Loaded: "+ this.toString());
    	System.out.println(world.toString());
    	System.out.println("pane: "+ pane.getChildren());
    }

    public GameObject addObject(GameObject gameObject) {
    	objects.add(gameObject);
    	return gameObject;
    }

    /*
     * Updates the position of all the nodes in the game based on their physics state.
     */
    void updatePositions(){
    	objects.forEach((o)->o.updatePosition());
    }

    /*
     * Interpolates the position of every node in the game between their current value and their
     * next value as indicated by their physics state (which in this case will be one step ahead).
     */
    void interpolatePositions(float alpha){
    	objects.forEach((o)->o.interpolatePosition(alpha));
    }

	@Override
	public String toString() {
		return "Game [numGliders=" + numGliders + ", numShips=" + numShips + ", \nworld=" + world + ", \nobjects=" + objects
				+ "]";
	}

}
