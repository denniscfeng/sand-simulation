import java.awt.*;
import java.util.Random;

public class SandParticle extends Particle{

    public SandParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = Color.yellow;
    }

    public void simulate() {

        int rowNext = row;
        int colNext = col;

        if (row + 1 < particleGrid.numRows) {
            if (particleGrid.get(row + 1, col) != null) {

                if (particleGrid.get(row + 1, col) instanceof WaterParticle) {
                    interact(particleGrid.get(row + 1, col));
                    return;

                } else {
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

        if (p != null && !(p instanceof SandParticle)) {

            if (p instanceof WaterParticle) {
                p.rowLast = row;
                this.rowLast = this.row;

                int temp;
                temp = p.row;
                p.row = this.row;
                this.row = temp;
            }
        }
        // Check for any special interactions with other particles
    }

}
