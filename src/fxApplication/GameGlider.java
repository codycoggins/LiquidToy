package fxApplication;

import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import model.ModelGlider;
import model.World;
import model.MVector;

import static fxApplication.Main.HEIGHT;
import static fxApplication.Main.SCALE;
import static fxApplication.Main.WIDTH;


public class GameGlider extends GameObject {
	static final double MAXSPEED = 0.5d;

	public GameGlider(World world, Pane pane, Game game) {
		super(world, pane, game);
		image = new Image("res/wg-icon-transparent.png");
        iView.setImage(image);
		MVector velocity = new MVector(random.nextDouble(),random.nextDouble()).multiply(MAXSPEED);
		MVector position = new MVector(random.nextDouble() * WIDTH,random.nextDouble() * HEIGHT);
		setModelObject(new ModelGlider(world, velocity, position, this.getRadius()/SCALE) );
		modelObject.setGameObject(this);
		modelObject.setMaxSpeed(MAXSPEED);
		pane.getChildren().add(iView);
		System.out.println("Created new " + this.toString());
		setHandlers();
	}

	@Override
	public void collisionWith(GameObject o){
		if (o.getClass() == GameShip.class ) {
			System.out.println(this.getName() + " is in trouble, it was hit by " + o.getName());
		}
	}

	@Override
	public void setHandlers(){
		super.setHandlers();
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
		    	System.out.println("GLIDER DRAG DONE");
		    	source.unSelect();
//		        event.consume();
		    }
		});
	}

//	@Override
//	public GameObject select() {
//		super.select();
//	}
//
//	public void notifyStartDragAndDrop(TransferMode.ANY){
//
//	}
}
