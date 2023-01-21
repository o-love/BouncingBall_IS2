package BouncingBall.presenter;

import BouncingBall.model.BallGravityObject;
import BouncingBall.model.Simulation;
import BouncingBall.view.BallDisplay;

import java.time.Instant;

public class BallPresenter {
    private static final double hMax = 12;
    private final BallGravityObject ball;
    private final BallDisplay display;
    private final Simulation simulation;

    private double grabbedX;
    private double grabbedY;
    private Instant grabbedInstance;

    public static BallPresenter with(BallGravityObject ball, BallDisplay display, Simulation simulation) {
        return new BallPresenter(ball, display, simulation);
    }

    private BallPresenter(BallGravityObject ball, BallDisplay display, Simulation simulation) {
        this.ball = ball;
        this.ball.onChange(this::ballChanged);
        this.ball.worldHorizontalLimit(hMax);

        this.display = display;
        this.display.onGrabbed(this::ballGrabbed);
        this.display.onReleased(this::ballReleased);
        this.display.onDragged(this::ballDragged);

        this.simulation = simulation;
    }

    private void ballGrabbed() {
        this.grabbedX = ball.x();
        this.grabbedY = ball.y();
        this.grabbedInstance = Instant.now();

        this.simulation.removeBehavior(ball);
    }

    private void ballReleased() {
        ball.velocityX((ball.x() - this.grabbedX) / (this.grabbedInstance.getNano() / 1e9D));
        ball.velocityY((ball.y() - this.grabbedY) / (this.grabbedInstance.getNano() / 1e9D));

        this.simulation.addBehavior(ball);
    }

    private void ballDragged(int x, int y) {
        ball.x(hMax - viewToModel(x));
        ball.y(hMax - viewToModel(y));
    }

    private void ballChanged() {
        display.draw(display.width() - modelToView(ball.x()),  display.height() - modelToView(ball.y()), modelToView(ball.radius()));
    }

    private int modelToView(double m) {
        return (int) (m * display.height() / hMax);
    }

    private double viewToModel(int p) {
        return p * hMax / display.height();
    }



}
