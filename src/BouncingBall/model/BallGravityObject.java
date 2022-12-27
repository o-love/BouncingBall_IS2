package BouncingBall.model;

import BouncingBall.base.events.NotifyEvent;

import static BouncingBall.base.MathUtils.root;

public class BallGravityObject implements GravityObject {

    private final double elasticity;
    private final double radius;

    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private NotifyEvent notifyEvent;

    public static BallGravityObject create(double x, double y, double elasticity, double radius) {
        return new BallGravityObject(x, y, elasticity, radius);
    }

    private BallGravityObject(double x, double y, double elasticity, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.elasticity = elasticity;

        this.velocityY = 0f;
        this.velocityX = 0f;
        this.notifyEvent = NotifyEvent.NULL;
    }

    @Override
    public void apply(double timeDelta) {
        applyToPosition(timeDelta);

        if (hasHitFloor()) {
            applyBounceOf(timeDelta);
        }

        addGravity(timeDelta);

        this.notifyEvent.handle();
    }

    private void applyToPosition(double timeDelta) {
        this.x += velocityX * timeDelta;
        this.y += velocityY * timeDelta;
    }

    private boolean hasHitFloor() {
        return ballDistanceToFloor() < 0;
    }

    private double ballDistanceToFloor() {
        return this.y - this.radius;
    }

    private void applyBounceOf(double timeDelta) {
        revertY(timeDelta);

        this.y = radius + -contactVelocity() * elasticity * contactTimeDelta(timeDelta);
        this.velocityY = -contactVelocity() * elasticity - GRAVITY_ACCELERATION * contactTime();
    }

    private void revertY(double timeDelta) {
        this.y -= velocityY * timeDelta;
    }

    private double contactTimeDelta(double timeDelta) {
        return timeDelta - contactTime();
    }

    private double contactVelocity() {
        return velocityY + GRAVITY_ACCELERATION * contactTime();
    }

    private double contactTime() {
        return root(GRAVITY_ACCELERATION/2, velocityY, ballDistanceToFloor());
    }

    private void addGravity(double timeDelta) {
        this.velocityY += GRAVITY_ACCELERATION * timeDelta;
    }

    public double radius() {
        return this.radius;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public void x(double x) {
        this.x = x;
    }

    @Override
    public void y(double y) {
        this.y = y;
    }

    @Override
    public double velocityX() {
        return this.velocityX;
    }

    @Override
    public double velocityY() {
        return this.velocityY;
    }

    @Override
    public void velocityX(double velocity) {
        this.velocityX = velocity;
    }

    @Override
    public void velocityY(double velocity) {
        this.velocityY = velocity;
    }

    @Override
    public void onChange(NotifyEvent notifyEvent) {
        this.notifyEvent = notifyEvent;
    }
}
