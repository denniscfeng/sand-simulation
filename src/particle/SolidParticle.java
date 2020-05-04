package particle;

import java.util.ArrayList;
import java.util.Random;

public abstract class SolidParticle extends Particle {

    public SolidParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
    }

    @Override
    public ArrayList<Particle> simulate() {

        // particle prefers to fall
        rowNext = row + 1;
        colNext = col;
        collide();

        return interact();

    }

    @Override
    public void collide() {

        // If we are not at the bottom
        if (rowNext < particleGrid.numRows) {

            Particle particleUnder = particleGrid.get(rowNext, col);

            // If the cell below is not a solid particle (can be empty), fall down and
            // push the other particle aside
            if (!canCollide(particleUnder)) {

                pushParticle(particleUnder);

            } else {

                int colLeft = col - 1;
                int colRight = col + 1;
                Particle particleLeft = particleGrid.get(rowNext, colLeft);
                Particle particleRight = particleGrid.get(rowNext, colRight);

                // Check if the cell to the bottom left is in frame and not a solid particle
                if (colLeft >= 0 && !canCollide(particleLeft)) {

                    // Check if the cell to the bottom right is in frame and not a solid particle
                    if (colRight < particleGrid.numCols && !canCollide(particleRight)) {

                        // If so, choose between bottom left and bottom right randomly. Else, choose left
                        colNext = (random.nextInt(2) == 0) ? colLeft : colRight;

                        // Otherwise, just fall into bottom left
                    } else {
                        colNext = colLeft;
                    }

                    // Push the other particle aside
                    pushParticle(particleGrid.get(rowNext, colNext));

                    // If bottom left is full, check cell at bottom right similarly if it contains a SolidParticle or not
                } else if (colRight < particleGrid.numCols && !canCollide(particleRight)) {

                    colNext = colRight;

                    // Push the other particle aside
                    pushParticle(particleGrid.get(rowNext, colNext));
                } else {
                    rowNext = row;
                }

            }

        } else {
            rowNext = row;
        }

    }

    @Override
    public boolean canCollide(Particle p) {
        return p instanceof SolidParticle || p instanceof StaticParticle;
    }


}
