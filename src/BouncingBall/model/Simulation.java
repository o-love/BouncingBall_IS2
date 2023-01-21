package BouncingBall.model;

import java.util.*;

public interface Simulation {

    void addBehavior(Behavior behavior);

    interface Behavior {
        void apply(double timeDelta);
    }

    static Simulation createSimulationWithUpdateOf(double seconds) {
        long milliseconds = (long) (seconds * 1000.);
        return new Simulation() {
            final Collection<Behavior> behaviors = new HashSet<>();

            {
                new Timer().scheduleAtFixedRate(worldUpdater(), milliseconds, milliseconds);
            }

            @Override
            public void addBehavior(Behavior behavior) {
                this.behaviors.add(behavior);
            }

            private TimerTask worldUpdater() {
                return new TimerTask() {
                    @Override
                    public void run() {
                        behaviors.forEach(behavior -> behavior.apply(seconds));
                    }
                };
            }
        };
    }
}
