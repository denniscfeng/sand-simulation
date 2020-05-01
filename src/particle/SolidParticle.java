package particle;

import java.util.Random;

public abstract class SolidParticle extends Particle {

    public SolidParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
    }

    @Override
    public void simulate() {

        // particle prefers to fall
        rowNext = row + 1;
        colNext = col;
        collide();

        // somewhere here goes interact()

    }

    @Override
    public void collide() {

        if (rowNext < particleGrid.numRows) {

            Particle p = particleGrid.get(rowNext, col);
            if (p != null) {

                if (p instanceof LiquidParticle) {

                    // force liquid particle to getting pushed on top of this particle
                    p.rowNext = row;
                    p.updatePosition();

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
