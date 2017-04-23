package fxApplication;

import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.Group;
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

	public GameGlider(World world, Group root, Game game) {
		super(world, root, game);
		image = new Image("res/wg-icon-transparent.png");
        iView.setImage(image);
		MVector velocity = new MVector(random.nextDouble(),random.nextDouble()).multiply(MAXSPEED);
		MVector position = new MVector(random.nextDouble() * WIDTH,random.nextDouble() * HEIGHT);
		setModelObject(new ModelGlider(world, velocity, position, this.getRadius()/SCALE) );
		modelObject.setGameObject(this);
		modelObject.setMaxSpeed(MAXSPEED);
		root.getChildren().add(iView);
		System.out.println("Created new " + this.toString());
		setHandlers();
	}

	@Override
	public void collisionWith(GameObject o){
		if (o.getClass() == GameShip.class ) {
			System.out.println(this.getName() + " is in trouble, it was hit by " + o.getName());
			// set Glider on new random course influenced by ship speed.
			MVector modifier = new MVector(random.nextDouble(),random.nextDouble()).unitVector().multiply(MAXSPEED);
			 modelObject.setVelocity(o.getVelocity().add(modifier));
		}
	}

	@Override
	public void setHandlers(){
		super.setHandlers();

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
