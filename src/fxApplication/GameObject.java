package fxApplication;

import static fxApplication.Main.HEIGHT;
import static fxApplication.Main.SCALE;
import static fxApplication.Main.WIDTH;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import model.ModelObject;
import model.World;

public abstract class GameObject {
    protected Random random = new Random();
    protected ModelObject modelObject = null;
    protected World world = null;
    protected Color color = null;
    protected double radius = 0;
    protected ImageView iView = null;
    protected Image image = null;

	public GameObject(World world, Pane pane)
    {
    	this.world = world;
    	this.color = Color.GRAY;
        radius = SCALE * 0.25;
		iView = new ImageView();
        iView.setFitWidth(50);
        iView.setPreserveRatio(true);
        iView.setSmooth(true);
        iView.setCache(true);
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

	@Override
	public String toString() {
		return "GameObject [name=" + getName() + ", modelObject=" + modelObject + ", color=" + color
				+ "]\n";
	}

}
