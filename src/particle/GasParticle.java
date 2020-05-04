package particle;

import java.util.ArrayList;
import java.util.Random;

public abstract class GasParticle extends Particle {

    public GasParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
    }

    @Override
    public ArrayList<Particle> simulate() {

        // particle randomly moves, but preferring in a non-down direction
        int direction = random.nextInt(7);
        if (direction == 0) { // go down
            rowNext = row + 1;
            colNext = col;
        } else if (direction <= 2) { // go left
            rowNext = row;
            colNext = col - 1;
        } else if (direction <= 4) { // go right
            rowNext = row;
            colNext = col + 1;
        } else {
            rowNext = row - 1;
            colNext = col;
        }

        collide();

        if (rowNext == row && colNext == col) { // if particle stays still, decrease its lifetime
            lifetime -= 1;
        }

        return interact();

    }

    @Override
    public void collide() {

        if (!particleGrid.checkBounds(rowNext, colNext)) {
            rowNext = row;
            colNext = col;
        } else {
            Particle particleNear = particleGrid.get(rowNext, colNext);
            if (canCollide(particleNear)) {
                rowNext = row;
                colNext = col;
            }
        }

    }

    @Override
    public boolean canCollide(Particle p) {
        return !(p == null);
    }

}
