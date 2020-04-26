import java.awt.*;
import java.util.Random;

public class SandParticle extends Particle{

    public SandParticle(int row, int col, Color color, ParticleGrid particleGrid, Random random) {
        super(row, col, color, particleGrid, random);
    }

    public void simulate() {

        int rowNext = row;
        int colNext = col;

        if (row + 1 < particleGrid.numRows) {
            if (particleGrid.get(row + 1, col) != null) {
                int colLeft = col - 1;
                int colRight = col + 1;
                if (colLeft >= 0 && particleGrid.get(row + 1, colLeft) == null) {
                    rowNext = row + 1;
                    if (colRight < particleGrid.numCols && particleGrid.get(row + 1, colRight) == null) {
                        colNext = (random.nextInt(2) == 0) ? colLeft : colRight;
                    } else {
                        colNext = colLeft;
                    }
                } else if (colRight < particleGrid.numCols && particleGrid.get(row + 1, colRight) == null) {
                    rowNext = row + 1;
                    colNext = colRight;
                } else {
                    rowNext = row;
                }
            } else {
                rowNext = row + 1;
            }
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
