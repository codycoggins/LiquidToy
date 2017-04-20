package fxApplication;

import static fxApplication.Main.HEIGHT;
import static fxApplication.Main.SCALE;
import static fxApplication.Main.WIDTH;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.MVector;
import model.ModelShip;
import model.World;

public class GameShip extends GameObject {
	static final double MAXSPEED = 1d;

	public GameShip(World world, Pane pane) {
		super(world, pane);
		image = new Image("res/boat_48.png");
        iView.setImage(image);
        iView.setFitWidth(48);

		MVector velocity = new MVector(random.nextDouble(),random.nextDouble()).multiply(MAXSPEED);
		MVector position = new MVector(random.nextDouble() * WIDTH,random.nextDouble() * HEIGHT);
		setModelObject(new ModelShip(world, velocity, position, this.getRadius()/SCALE) );
		modelObject.setMaxSpeed(MAXSPEED);
		modelObject.setGameObject(this);
		pane.getChildren().add(iView);

		System.out.println("Created new " + this.toString());

	}

}
