package particle;

import java.util.Random;

// Stationary particles are basically solid objects that don't move
public abstract class StationaryParticle extends Particle {

    public StationaryParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
    }

    @Override
    public void simulate() {

    }

    @Override
    public void collide() {

    }

    @Override
    public void interact(Particle p) {

    }
}
