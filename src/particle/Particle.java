package particle;

import java.awt.*;
import java.util.Random;

public abstract class Particle {

    public int row;
    public int col;
    public int rowNext;
    public int colNext;
    public Color color;
    public int lifetime;
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

    // used by simulate to check particle collisions, which can result in calling
    // simulate() on other particles
    public abstract void collide();

    // used by collide to check if a type of particle can collide with another
    // (empty space included!), otherwise it can push the other out of the way
    public abstract boolean canCollide(Particle p);

    // updates position based on next positions set by simulate()
    public void updatePosition() {
        if (particleGrid.get(row, col) == this) {
            particleGrid.set(row, col, null);
        }
        row = rowNext;
        col = colNext;
        particleGrid.set(row, col, this);
    }

    // push particles (liquids, gasses(?))out of the way, preferring directly to the sides
    // but able to swap positions as well
    public void pushParticle(Particle p) {
        if (p == null) {
            return;
        }
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
            p.colNext = col;
        }
        p.updatePosition();
    }

    // used by specific particle types to change properties of other particles
    public abstract void interact(Particle p);


}
