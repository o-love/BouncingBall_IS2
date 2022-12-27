package BouncingBall.base.events;

public interface NotifyEvent {
    NotifyEvent NULL = () -> {
    };

    void handle();
}
