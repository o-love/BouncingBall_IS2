package BouncingBall.view;

import BouncingBall.base.events.NotifyEvent;
import BouncingBall.base.events.PositionEvent;

public interface BallDisplay {
    int height();
    int width();

    void draw(int x, int y, int size);

    void onGrabbed(NotifyEvent event);
    void onReleased(NotifyEvent event);
    void onDragged(PositionEvent event);
}
