package fxApplication;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.ModelGlider;
import model.ModelObject;
import model.World;
import model.MVector;

import static fxApplication.Main.HEIGHT;
import static fxApplication.Main.SCALE;
import static fxApplication.Main.WIDTH;


public class GameGlider extends GameObject {
	static final double MAXSPEED = 0.5d;

	public GameGlider(World world, Pane pane) {
		super(world, pane);
		image = new Image("res/wg-icon-transparent.png");
        iView.setImage(image);
		MVector velocity = new MVector(random.nextDouble(),random.nextDouble()).multiply(MAXSPEED);
		MVector position = new MVector(random.nextDouble() * WIDTH,random.nextDouble() * HEIGHT);
		setModelObject(new ModelGlider(world, velocity, position, this.getRadius()/SCALE) );
		modelObject.setGameObject(this);
		modelObject.setMaxSpeed(MAXSPEED);
		pane.getChildren().add(iView);
		System.out.println("Created new " + this.toString());
	}

	@Override
	public void collisionWith(GameObject o){
		if (o.getClass() == GameShip.class ) {
			System.out.println(this.getName() + " is in trouble, it was hit by " + o.getName());
		}
	}

}
