package particle;

import java.awt.*;
import java.util.Random;

public abstract class Particle {

    public int row;
    public int col;
    public int rowNext;
    public int colNext;
    public Color color;
    ParticleGrid particleGrid;
    Random random;

    public Particle(int row, int col, ParticleGrid particleGrid, Random random) {
        this.row = row;
        this.col = col;
        this.rowNext = row;
        this.colNext = col;
        this.particleGrid = particleGrid;
        this.random = random;
        particleGrid.set(row, col, this);
    }

    public abstract void simulate();

    public abstract void collide();

    public abstract void interact(Particle p);

    // Add any other abstract methods that may be necessary below

}
