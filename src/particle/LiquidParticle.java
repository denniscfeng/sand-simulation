package particle;

import java.util.Random;

public abstract class LiquidParticle extends Particle{

    public LiquidParticle(int row, int col, ParticleGrid particleGrid, Random random) {
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

        // If we are not at the bottom
        if (rowNext < particleGrid.numRows) {

            // If the cell immediately below is not empty
            if (particleGrid.get(rowNext, col) != null) {
                int colLeft = col - 1;
                int colRight = col + 1;

                // Check if the cell to the bottom left is in frame and empty
                if (colLeft >= 0 && particleGrid.get(rowNext, colLeft) == null) {

                    // Check if the cell to the bottom right is in frame and empty
                    if (colRight < particleGrid.numCols && particleGrid.get(rowNext, colRight) == null) {

                        // If so, choose between bottom left and bottom right randomly. Else, choose left
                        colNext = (random.nextInt(2) == 0) ? colLeft : colRight;
                    } else {
                        colNext = colLeft;
                    }

                    // If bottom left is full, check cell at bottom right without choosing randomly
                } else if (colRight < particleGrid.numCols && particleGrid.get(rowNext, colRight) == null) {
                    colNext = colRight;

                    // If bottom left and bottom right are full, then stay in same row, but move to left or right column
                } else {
                    rowNext = row;

                    // Do similar checks to see if cells to immediate right and left are empty
                    if (colLeft >= 0 && particleGrid.get(row, colLeft) == null) {
                        if (colRight < particleGrid.numCols && particleGrid.get(row, colRight) == null) {
                            colNext = (random.nextInt(2) == 0) ? colLeft : colRight;
                        } else {
                            colNext = colLeft;
                        }
                    } else if (colRight < particleGrid.numCols && particleGrid.get(row, colRight) == null) {
                        colNext = colRight;
                    }
                }

            }

        // If we are at bottom of frame, stay in same place
        } else {
            rowNext = row;
        }

    }

}
