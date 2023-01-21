package BouncingBall.model;

import java.util.*;

public interface Simulation {

    void addBehavior(Behavior behavior);
    void removeBehavior(Behavior behavior);

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

            @Override
            public void removeBehavior(Behavior behavior) {
                this.behaviors.remove(behavior);
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
