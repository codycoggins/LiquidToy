package fxApplication;

import static fxApplication.Main.HEIGHT;
import static fxApplication.Main.SCALE;
import static fxApplication.Main.WIDTH;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import model.ModelObject;
import model.World;

public abstract class GameObject extends Image{
    protected Random random = new Random();
    protected ModelObject modelObject = null;
    protected World world = null;
    protected Color color = null;

	public GameObject(World world)
    {
    	this.world = world;
    	this.color = Color.GRAY;
        setRadius(SCALE * 0.1);
        setFill(Color.GRAY);
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
        setTranslateX(modelObject.getPosition().getX() * SCALE);
        setTranslateY((modelObject.getPosition().getY()) * SCALE);
//        System.out.println("updatePosition: " + getTranslateX() + ","+ getTranslateY());
    }

    public void interpolatePosition(float alpha)
    {
        setTranslateX(alpha * modelObject.getPosition().getX() * SCALE + (1 - alpha) * getTranslateX());
        setTranslateY(alpha * (modelObject.getPosition().getY()) * SCALE + (1- alpha) * getTranslateY());
//        System.out.println("interpolatePosition: " + getTranslateX() + ","+ getTranslateY());
    }

    public String getName(){
    	return modelObject.getName();
    }
    public void setName(String name){
    	modelObject.setName(name);
    }

	@Override
	public String toString() {
		return "GameObject [name=" + getName() + ", modelObject=" + modelObject + ", color=" + color
				+ "]\n";
	}

}
