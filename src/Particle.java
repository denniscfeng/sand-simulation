import java.awt.*;
import java.util.Random;

public abstract class Particle {

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

    public abstract void simulate();

    public abstract void interact(Particle p);

    // Add any other abstract methods that may be necessary belowZ

}
