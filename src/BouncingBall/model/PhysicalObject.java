package BouncingBall.model;

import BouncingBall.base.events.NotifyEvent;

public interface PhysicalObject extends Simulation.Behavior {
    double x();
    double y();

    void x(double x);
    void y(double y);

    double velocityX();
    double velocityY();

    void velocityX(double velocity);
    void velocityY(double velocity);

    void worldHorizontalLimit(double horizontalLimit);

    void onChange(NotifyEvent notifyEvent);
}
