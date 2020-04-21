import java.awt.*;
import java.util.Random;

public class Particle {

    int row;
    int col;
    int rowLast;
    int colLast;
    Color color;
    ParticleGrid particleGrid;
    Random random;

    public Particle(int row, int col, Color color, ParticleGrid particleGrid, Random random) {
        this.row = row;
        this.col = col;
        this.rowLast = row;
        this.colLast = col;
        this.color = color;
        this.particleGrid = particleGrid;
        this.random = random;
        particleGrid.set(row, col, this);
    }

    void simulate() {

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

}
