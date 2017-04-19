package fxApplication;

import java.util.function.Consumer;

import javafx.animation.AnimationTimer;

/*
 * Adapted from https://github.com/svanimpe/fx-game-loops
 *
 * Game loop implementation using fixed time steps (with a maximum time step, inherited from the
 * GameLoop superclass) and interpolation. See the blog post http://svanimpe.be/blog/game-loops-fx.html for details.
 *
 */

public abstract class GameLoop extends AnimationTimer
{
    private float maximumStep = Float.MAX_VALUE;

    private final Consumer<Float> updater;
    private final Runnable renderer;
    private final Consumer<Float> interpolater;
    private final Consumer<Integer> fpsReporter;

    public GameLoop(Consumer<Float> updater, Runnable renderer, Consumer<Float> interpolater, Consumer<Integer> fpsReporter)
    {
        this.updater = updater;
        this.renderer = renderer;
        this.interpolater = interpolater;
        this.fpsReporter = fpsReporter;
    }

    private static final float timeStep = 0.0166f;

    private long previousTime = 0;
    private float accumulatedTime = 0;

    private float secondsElapsedSinceLastFpsUpdate = 0f;
    private int framesSinceLastFpsUpdate = 0;

    public float getMaximumStep()
    {
        return maximumStep;
    }

    public void setMaximumStep(float maximumStep)
    {
        this.maximumStep = maximumStep;
    }

    @Override
    public void handle(long currentTime)
    {
        if (previousTime == 0) {
            previousTime = currentTime;
            return;
        }

        float secondsElapsed = (currentTime - previousTime) / 1e9f; /* nanoseconds to seconds */
        float secondsElapsedCapped = Math.min(secondsElapsed, getMaximumStep());
        accumulatedTime += secondsElapsedCapped;
        previousTime = currentTime;

        if (accumulatedTime < timeStep) {
            float remainderOfTimeStepSincePreviousInterpolation = timeStep - (accumulatedTime - secondsElapsed);
            float alphaInRemainderOfTimeStep = secondsElapsed / remainderOfTimeStepSincePreviousInterpolation;
            interpolater.accept(alphaInRemainderOfTimeStep);
            return;
        }

        while (accumulatedTime >= 2 * timeStep) {

        	updater.accept(timeStep);
            accumulatedTime -= timeStep;
        }
        renderer.run();
        updater.accept(timeStep);
        accumulatedTime -= timeStep;
        float alpha = accumulatedTime / timeStep;
        interpolater.accept(alpha);

        secondsElapsedSinceLastFpsUpdate += secondsElapsed;
        framesSinceLastFpsUpdate++;
        if (secondsElapsedSinceLastFpsUpdate >= 0.5f) {
            int fps = Math.round(framesSinceLastFpsUpdate / secondsElapsedSinceLastFpsUpdate);
            fpsReporter.accept(fps);
            secondsElapsedSinceLastFpsUpdate = 0;
            framesSinceLastFpsUpdate = 0;
        }
    }

    @Override
    public void stop()
    {
        previousTime = 0;
        accumulatedTime = 0;
        secondsElapsedSinceLastFpsUpdate = 0f;
        framesSinceLastFpsUpdate = 0;
        super.stop();
    }

    @Override
    public String toString()
    {
        return "Fixed time steps with interpolation";
    }
}
