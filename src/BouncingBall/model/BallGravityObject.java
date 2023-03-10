package BouncingBall.model;

import BouncingBall.base.events.NotifyEvent;

import java.util.Objects;

import static BouncingBall.base.MathUtils.root;

public class BallGravityObject implements GravityObject {

    private final double elasticity;
    private final double radius;

    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private double rightLimit;

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
            applyBounceOffFloorOf(timeDelta);
        }

        if (hasHitSide()) {
            applyBounceOffWallOf();
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

    private void applyBounceOffFloorOf(double timeDelta) {
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

    private boolean hasHitSide() {
        return ballDistanceToLeftLimit() < 0 || ballDistanceToRightLimit() < 0;
    }

    private double ballDistanceToLeftLimit() {
        return this.x - this.radius;
    }

    private double ballDistanceToRightLimit() {
        return this.rightLimit - (this.x + this.radius);
    }

    private void applyBounceOffWallOf() {
        flipPositionAfterWallBounce();
        flipAndReduceVelocityAfterWallBounce();
    }

    private void flipAndReduceVelocityAfterWallBounce() {
        this.velocityX = -velocityX * 0.8;
    }

    private void flipPositionAfterWallBounce() {
        double wallValue = ballDistanceToLeftLimit() < 0 ? 0 : this.rightLimit;
        this.x = 2 * wallValue - this.x;

        ensureMinimumSeparationFromBorder();
    }

    private void ensureMinimumSeparationFromBorder() {
        if (ballDistanceToLeftLimit() < 0) {
            this.x = this.radius;
        } else if (ballDistanceToRightLimit() < 0) {
            this.x = this.rightLimit - this.radius;
        }
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

        this.notifyEvent.handle();
    }

    @Override
    public void y(double y) {
        this.y = y;

        this.notifyEvent.handle();
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
    public void worldHorizontalLimit(double horizontalLimit) {
        if (horizontalLimit <= 0) throw new IllegalArgumentException("The Horizontal Limit must be greater than 0");

        this.rightLimit = horizontalLimit;
    }

    @Override
    public void onChange(NotifyEvent notifyEvent) {
        Objects.requireNonNull(notifyEvent);

        this.notifyEvent = notifyEvent;
    }
}
