package particle;

import java.util.Random;

public abstract class SolidParticle extends Particle {

    public SolidParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
    }

    // run simulation step of the particle, touching other type particles as necessary
    @Override
    public void simulate() {

        // if a particle has not been moved by a collision already, update its next position and run collide
        if (rowNext == row && colNext == col) {
            rowNext = row + 1;
            colNext = col;
            collide();
        }

        // somewhere here goes interact()

        // position update
        particleGrid.set(row, col, null);
        row = rowNext;
        col = colNext;
        particleGrid.set(row, col, this);

    }

    // check if particle can move into next position
    // if particle collides into another particle, modify particles next positions as needed
    @Override
    public void collide() {

        if (rowNext < particleGrid.numRows) {

            Particle p = particleGrid.get(rowNext, col);
            if (p != null) {

                if (p instanceof LiquidParticle) {

                    p.rowNext = row;

                } else {

                    int colLeft = col - 1;
                    int colRight = col + 1;

                    if (colLeft >= 0 && particleGrid.get(rowNext, colLeft) == null) {
                        if (colRight < particleGrid.numCols && particleGrid.get(rowNext, colRight) == null) {
                            colNext = (random.nextInt(2) == 0) ? colLeft : colRight;
                        } else {
                            colNext = colLeft;
                        }
                    } else if (colRight < particleGrid.numCols && particleGrid.get(rowNext, colRight) == null) {
                        colNext = colRight;
                    } else {
                        rowNext = row;
                    }

                }

            }

        } else {
            rowNext = row;
        }

    }

}
