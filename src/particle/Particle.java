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

    // run simulation step of the particle, to determine next positions
    public abstract void simulate();

    // used by simulate to check particle collisions, which can result in calling simulate() on other particles
    public abstract void collide();

    // updates position based on next positions set by simulate()
    public void updatePosition() {
        if (particleGrid.get(row, col) == this) {
            particleGrid.set(row, col, null);
        }
        row = rowNext;
        col = colNext;
        particleGrid.set(row, col, this);
    }

    // used by specific particle types to change properties of other particles
    public abstract void interact(Particle p);


}
