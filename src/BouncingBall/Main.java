package BouncingBall;

import BouncingBall.model.*;
import BouncingBall.presenter.BallPresenter;
import BouncingBall.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        BallGravityObject ball = BallGravityObject.create(5, 5, 0.8, 0.8);
        Simulation simulation = Simulation.createSimulationWithUpdateOf(0.001);
        simulation.addBehavior(ball);
        MainFrame frame = new MainFrame();
        BallPresenter.with(ball, frame.ballDisplay(), simulation);
    }


}