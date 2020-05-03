package particle;

import java.util.Random;

public abstract class GasParticle extends Particle {

    public GasParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
    }

    @Override
    public void simulate() {

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

        // somewhere here goes interact()

    }

    @Override
    public void collide() {

        if (rowNext >= particleGrid.numRows || rowNext <= 0) {
            rowNext = row;
        } else if (colNext >= particleGrid.numCols || colNext <= 0) {
            colNext = col;
        }

        Particle particleNear = particleGrid.get(rowNext, colNext);
        if (canCollide(particleNear)) {
            rowNext = row;
            colNext = col;
        }

    }

    @Override
    public boolean canCollide(Particle p) {
        return !(p == null);
    }

}
