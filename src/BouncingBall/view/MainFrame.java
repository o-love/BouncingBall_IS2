package BouncingBall.view;

import BouncingBall.base.events.NotifyEvent;
import BouncingBall.base.events.PositionEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private final Canvas canvas;

    public MainFrame() {
        this.setTitle("Bouncing Ball");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(canvas = new Canvas());
        this.setVisible(true);
    }

    public BallDisplay ballDisplay() {
        return canvas;
    }

    private static class Canvas extends JPanel implements BallDisplay{
        private int x;
        private int y;
        private int size;
        private NotifyEvent onGrabbed = NotifyEvent.NULL;
        private NotifyEvent onReleased = NotifyEvent.NULL;
        private PositionEvent onDragged = PositionEvent.NULL;
        private boolean grabbed;

        public Canvas() {
            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent me) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (isNotOver(e.getX(), e.getY())) return;
                    grabbed = true;
                    onGrabbed.handle();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (!grabbed) return;
                    grabbed = false;
                    onReleased.handle();
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                }

                @Override
                public void mouseExited(MouseEvent me) {
                }

                private boolean isNotOver(int mx, int my) {
                    return mx < x - size || mx > x +size ||
                            my < y - size || my > y +size;
                }
            });
            this.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (!grabbed) return;
                    onDragged.handle(e.getX(), e.getY());
                }

                @Override
                public void mouseMoved(MouseEvent me) {
                }
            });
        }

        @Override
        public int height() {
            return getHeight();
        }

        @Override
        public int width() {
            return getWidth();
        }

        @Override
        public void draw(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
            repaint();
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.black);
            g.fillOval(x-size, y-size, size*2, size*2);
        }

        @Override
        public void onGrabbed(NotifyEvent event) {
            this.onGrabbed = event;
        }

        @Override
        public void onReleased(NotifyEvent event) {
            this.onReleased = event;
        }

        @Override
        public void onDragged(PositionEvent event) {
            this.onDragged = event;
        }


    }



}
