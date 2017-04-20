package fxApplication;

import static fxApplication.Main.HEIGHT;
import static fxApplication.Main.SCALE;
import static fxApplication.Main.WIDTH;

import javafx.scene.paint.Color;
import model.MVector;
import model.ModelShip;
import model.World;

public class GameShip extends GameObject {
	static final float MAXSPEED = 3;

	public GameShip(World world) {
		super(world);

		MVector velocity = new MVector(random.nextDouble(),random.nextDouble()).multiply(MAXSPEED);
		MVector position = new MVector(random.nextDouble() * WIDTH,random.nextDouble() * HEIGHT);
		setModelObject(new ModelShip(world, velocity, position, this.getRadius()/SCALE) );
		modelObject.setMaxSpeed(MAXSPEED);
    	this.color = Color.RED;
    	setFill(Color.RED);

		System.out.println("Created new " + this.toString());

	}

}
