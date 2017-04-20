package fxApplication;

import static fxApplication.Main.HEIGHT;
import static fxApplication.Main.SCALE;
import static fxApplication.Main.WIDTH;

import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.MVector;
import model.ModelObject;
import model.World;

public abstract class GameObject {
    protected Random random = new Random();
    protected ModelObject modelObject = null;
    protected World world = null;
    protected Game game = null;
    protected Color color = null;
    protected double radius = 0;
    protected ImageView iView = null;
    protected Image image = null;
    protected Circle circle = null;
    final GameObject source = this; // for references in event handlers

	public GameObject(World world, Group root, Game game)
    {
    	this.world = world;
    	this.color = Color.GRAY;
    	this.game = game;
        radius = SCALE * 0.25;
//        circle = new Circle(radius * 2);
//        circle.setFill(Color.LIGHTGREY);
		iView = new ImageView();
        iView.setFitWidth(50);
        iView.setPreserveRatio(true);
        iView.setSmooth(true);
        iView.setCache(true);
        iView.setPickOnBounds(true);
    }

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Random getRandom() {
		return random;
	}

    public ModelObject setModelObject(ModelObject modelObject) {
		this.modelObject = modelObject;
		return modelObject;
	}

	public ModelObject getModelObject(){
    	return modelObject;
    }

    public void updatePosition()
    {
        iView.setTranslateX(modelObject.getPosition().getX() * SCALE - radius);
        iView.setTranslateY(modelObject.getPosition().getY() * SCALE - radius);
//        System.out.println("updatePosition: " + getTranslateX() + ","+ getTranslateY());
    }

    public void interpolatePosition(float alpha)
    {
        iView.setTranslateX(alpha * (modelObject.getPosition().getX() * SCALE -radius)+ (1 - alpha) * iView.getTranslateX());
        iView.setTranslateY(alpha * (modelObject.getPosition().getY() * SCALE - radius)+ (1- alpha) * iView.getTranslateY());
//        System.out.println("interpolatePosition: " + getTranslateX() + ","+ getTranslateY());
    }

    public String getName(){
    	return modelObject.getName();
    }
    public void setName(String name){
    	modelObject.setName(name);
    }

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public MVector getPosition (){
		return (modelObject.getPosition().multiply(SCALE));
	}

	public MVector getVelocity (){
		return (modelObject.getVelocity().multiply(SCALE));
	}

	public ImageView getiView() {
		return iView;
	}

	public void setiView(ImageView iView) {
		this.iView = iView;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void collisionWith(GameObject o){
	}

	public void setHandlers(){
		iView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	System.out.println("CLICK " + source.getName());
            	source.select();
            	event.consume();
            }
		});
		// see dragdrop sample code https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm
		iView.setOnDragDetected(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent event) {
		        /* drag was detected, start a drag-and-drop gesture*/
            	System.out.println("START DRAG AND DROP");
		        /* allow any transfer mode */
		        game.db = source.iView.startDragAndDrop(TransferMode.ANY);
		        source.select();
		        ClipboardContent content = new ClipboardContent();
		        content.putString(source.getName());
		        /* Put a string on a dragboard */
		        game.db.setContent(content);
//		        event.consume();
		    }
		});
		iView.setOnDragDone(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* the drag and drop gesture ended */
		        /* if the data was successfully moved, clear it */
		    	System.out.println(source.getName() + " DRAG DONE");
		    	source.unSelect();
//		        event.consume();
		    }
		});
	}

	public GameObject select() {
		System.out.println("SELECT "+ this.getName());
		game.setSelectedObject(this);
		iView.setStyle("-fx-border-color: slateblue; -fx-border-radius: 30;");
		iView.setFitWidth(100);
		return this;
	}

	public GameObject unSelect() {
		System.out.println("UNSELECT "+ this.getName());
		if (this == game.getSelectedObject()) {
			game.setSelectedObject(null);
			iView.setFitWidth(50);
		}
		return this;
	}
	@Override
	public String toString() {
		return "GameObject [name=" + getName() + ", modelObject=" + modelObject + ", color=" + color
				+ "]\n";
	}

	public void setWaypoint(MVector w) {
		// scale the waypoint to model space and sent to modelObject
		System.out.print(this.getName() + ": new waypoint (pixels) " + w);
		modelObject.setWaypoint(w.divide(SCALE));
	}
}
