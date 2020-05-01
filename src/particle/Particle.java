package particle;

import java.awt.*;
import java.util.Random;

public abstract class Particle {

    public int row;
    public int col;
    int rowLast;
    int colLast;
    public Color color;
    ParticleGrid particleGrid;
    Random random;

    public Particle(int row, int col, ParticleGrid particleGrid, Random random) {
        this.row = row;
        this.col = col;
        this.rowLast = row;
        this.colLast = col;
        this.particleGrid = particleGrid;
        this.random = random;
        particleGrid.set(row, col, this);
    }

    public abstract void simulate();

    public abstract void interact(Particle p);

    // Add any other abstract methods that may be necessary below

}
