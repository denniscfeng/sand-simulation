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

                // push liquids out of the way, preferring to the sides but able to push water above
                if (p instanceof LiquidParticle) {

                    int pColLeft = p.col - 1;
                    int pColRight = p.col + 1;

                    if (pColLeft >= 0 && particleGrid.get(p.row, pColLeft) == null) {
                        if (pColRight < particleGrid.numCols && particleGrid.get(p.row, pColRight) == null) {
                            p.colNext = (random.nextInt(2) == 0) ? pColLeft : pColRight;
                        } else {
                            p.colNext = pColLeft;
                        }
                    } else if (pColRight < particleGrid.numCols && particleGrid.get(p.row, pColRight) == null) {
                        p.colNext = pColRight;
                    } else {
                        p.rowNext = row;
                    }

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
