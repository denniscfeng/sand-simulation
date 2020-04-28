import java.awt.*;
import java.util.Random;

public class WaterParticle extends Particle{

    public WaterParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = Color.blue;
    }

    public void simulate() {

        int rowNext = row;
        int colNext = col;

        // If we are not at the bottom
        if (row + 1 < particleGrid.numRows) {

            // If the cell immediately below is not empty
            if (particleGrid.get(row + 1, col) != null) {
                int colLeft = col - 1;
                int colRight = col + 1;

                // Check if the cell to the bottom left is in frame and empty
                if (colLeft >= 0 && particleGrid.get(row + 1, colLeft) == null) {
                    rowNext = row + 1;

                    // Check if the cell to the bottom right is in frame and empty
                    if (colRight < particleGrid.numCols && particleGrid.get(row + 1, colRight) == null) {

                        // If so, choose between bottom left and bottom right randomly. Else, choose left
                        colNext = (random.nextInt(2) == 0) ? colLeft : colRight;
                    } else {
                        colNext = colLeft;
                    }

                // If bottom left is full, check cell at bottom right without choosing randomly
                } else if (colRight < particleGrid.numCols && particleGrid.get(row + 1, colRight) == null) {
                    rowNext = row + 1;
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

            // If cell immediately beneath current cell is empty, then just move down
            } else {
                rowNext = row + 1;
            }

        // If we are at bottom of frame, stay in same place
        } else {
            rowNext = row;
        }

        rowLast = row;
        colLast = col;
        particleGrid.set(row, col, null);

        row = rowNext;
        col = colNext;
        particleGrid.set(row, col, this);

    }

    public void interact(Particle p) {
        // Check for any special interactions with other particles
    }

}
