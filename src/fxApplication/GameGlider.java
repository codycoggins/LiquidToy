package fxApplication;

import java.util.Random;

import javafx.scene.paint.Color;
import model.ModelGlider;
import model.World;
import model.MVector;

import static fxApplication.Main.HEIGHT;
import static fxApplication.Main.SCALE;
import static fxApplication.Main.WIDTH;


public class GameGlider extends GameObject {
	static final float MAXSPEED = 1;

	public GameGlider(World world) {
		super(world);

		MVector velocity = new MVector(random.nextDouble(),random.nextDouble()).multiply(MAXSPEED);
		MVector position = new MVector(random.nextDouble() * WIDTH,random.nextDouble() * HEIGHT);
		setModelObject(new ModelGlider(world, velocity, position, this.getRadius()/SCALE) );
		modelObject.setMaxSpeed(MAXSPEED);
    	this.color = Color.BLUE;
    	setFill(Color.BLUE);


		System.out.println("Created new " + this.toString());
	}

}
