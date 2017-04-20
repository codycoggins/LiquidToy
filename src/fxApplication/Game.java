package fxApplication;

import model.MVector;
import model.ModelObject;
import model.World;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.Cursor;

import static fxApplication.Main.HEIGHT;
import static fxApplication.Main.SCALE;
import static fxApplication.Main.WIDTH;

import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class Game {
	int numGliders = 0;
	int numShips = 0;
	World world = null;
	List<GameObject> objects = new LinkedList<GameObject>();
	GameObject selectedObject = null;
	Dragboard db;
	Line dragLine = new Line();

    void load(World world, Pane pane, int numGliders, int numShips){
    	this.numGliders = numGliders;
    	this.numShips = numShips;
    	objects.clear();
    	this.world = world;
    	for (int i=0; i<numGliders; i++){
    		GameObject o = addObject(new GameGlider(world,pane,this));
    		o.setName("Glider"+i);
    	}
    	for (int i=0; i<numShips; i++){
    		GameObject o = addObject(new GameShip(world,pane,this));
    		o.setName("Ship"+i);
    	}
    	dragLine.setFill(Color.RED);
    	dragLine.setVisible(false);
    	pane.getChildren().add(dragLine);
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

	public void setHandlers(Scene scene){
		List<String> input = new ArrayList<String>();

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();

				// only add once... prevent duplicates
				if (!input.contains(code))
					input.add(code);
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				input.remove(code);
			}
		});

		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	System.out.println("CLICK SCENE");
            	if (selectedObject!=null){
            		selectedObject.unSelect();
            		selectedObject = null;
            	}
            }
		});

		scene.setOnDragOver(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* data is dragged over the target */
		        /* accept it only if it is not dragged from the same node
		         * and if it has a string data */
		    	System.out.println("SCENE RECEIVE DRAG OVER FROM " + event.getGestureSource());
		        if (event.getGestureSource() != scene ) {  // removed condition: && event.getDragboard().hasString()
		            /* allow for both copying and moving, whatever user chooses */
		            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

		            scene.setCursor(Cursor.CROSSHAIR);
	            	MVector startPos = selectedObject.getPosition();
		            putLineTruePosition(dragLine,startPos.getX(),startPos.getY(),event.getSceneX(),event.getSceneY() );
	            	dragLine.setFill(Color.RED);
	            	dragLine.setVisible(true);
	            	System.out.println("dragLine ("+ dragLine.getStartX()+","+ dragLine.getStartY()+ ","+dragLine.getEndX()+","+ dragLine.getEndY() );
	            }

//		        event.consume();
		    }
		});

		scene.setOnDragExited(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* mouse moved away, remove the graphical cues */
		    	System.out.print("SCENE DRAG EXITED");
		        scene.setFill(Color.WHITE);
		        scene.setCursor(Cursor.DEFAULT);
//		        event.consume();
		    }
		});

		scene.setOnDragDropped(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* data dropped */
		        /* if there is a string data on dragboard, read it and use it */
		        db = event.getDragboard();
		        boolean success = false;
	            System.out.println("SCENE RECEIVE DROP FROM " + event.getGestureSource());
	            System.out.println("DROP COORDINATES: "+ event.getSceneX()+","+event.getSceneY());
		        /* let the source know whether the string was successfully
		         * transferred and used */
	            if (selectedObject==null){
	            	System.out.println("WARNING: RECEIVED DROP BUT NO SELECTED OBJECT");
	            } else {
	            	MVector startPos = selectedObject.getPosition();
	            	putLineTruePosition(dragLine,startPos.getX(),startPos.getY(),event.getSceneX(),event.getSceneY() );
	            	dragLine.setFill(Color.GRAY);
	            	dragLine.setVisible(true);
	            	selectedObject.setWaypoint(new MVector(event.getSceneX(), event.getSceneY()));
	            	success=true;
	            }
		        event.setDropCompleted(success);
		        scene.setCursor(Cursor.DEFAULT);
//		        event.consume();
		     }
		});
	}

	/**
	 * JavaFX object position is counterintuitive for lines.
	 * This helper method simplifies it.
	 * @param line  Line to move
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	protected void putLineTruePosition(Line line, double x1, double y1, double x2, double y2) {
		MVector span = new MVector(x2-x1, y2-y1);
		double originX = x1 + span.getX()/2;
		double originY = y1 + span.getY()/2;
    	line.setTranslateX(originX);
    	line.setTranslateY(originY);
    	line.setStartX(0);
    	line.setStartY(0);
    	line.setEndX(span.getX());
    	line.setEndY(span.getY());

	}

	public GameObject getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(GameObject selectedObject) {
		this.selectedObject = selectedObject;
	}

	public World getWorld() {
		return world;
	}

	@Override
	public String toString() {
		return "Game [numGliders=" + numGliders + ", numShips=" + numShips + ", \nworld=" + world + ", \nobjects=" + objects
				+ "]";
	}

}
