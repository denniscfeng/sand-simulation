package particle;

import java.util.ArrayList;
import java.util.Random;

public abstract class StaticParticle extends Particle {

    public StaticParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
    }

    @Override
    public ArrayList<Particle> simulate() {

        return interact();

    }

    @Override
    public void collide() {

        // particle does nothing, stays in same place

    }

    @Override
    public boolean canCollide(Particle p) {
        return true;
    }

}
