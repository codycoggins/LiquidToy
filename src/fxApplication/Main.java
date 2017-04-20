package fxApplication;

import java.util.function.Consumer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import model.World;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


/**
 * @author c.coggins
 *
 * Helpful examples:
 * https://github.com/svanimpe/fx-game-loops
 */
public class Main extends Application {
    public static final float SCALE = 100f; /* pixels per meter */
    public static final float WIDTH = 12f; /* 12m */
    public static final float HEIGHT = 8f; /* 8m */
	private static final int MIN_FRAME_RATE = 30;

    Game activeGame=null;
    GameLoop gameLoop = null;
    World world = new World(WIDTH, HEIGHT);

	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();

	        Consumer<Float> updater = secondsElapsed -> world.step(secondsElapsed);
			Runnable renderer = () -> activeGame.updatePositions();
	        Consumer<Float> interpolater = alpha -> activeGame.interpolatePositions(alpha);
	        Consumer<Integer> fpsReporter = fps -> System.out.println(String.format("FPS: %d", fps));

	        Scene scene = new Scene(root, WIDTH * SCALE, HEIGHT * SCALE);
			activeGame = new Game();
			activeGame.load(world, root, 1,3 );
			activeGame.setHandlers(scene);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle("LiquidToy");
			primaryStage.setResizable(false);
			primaryStage.show();

			gameLoop = new GameLoop(updater, renderer, interpolater, fpsReporter){} ;
			gameLoop.setMaximumStep(1f/MIN_FRAME_RATE);
			gameLoop.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
