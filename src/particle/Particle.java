package particle;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public abstract class Particle {

    public int row;
    public int col;
    public int rowNext;
    public int colNext;

    public Color color;

    public int lifetime = -1;
    public int minLifetime = -1;
    public int maxLifetime = -1;

    public boolean onFire = false;
    public double flammability = -1;
    public int minBurntime = -1;
    public int maxBurntime = -1;
    public double fireCreateChance = -1;

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
    // additionally, return any new particles created by simulation;
    public abstract ArrayList<Particle> simulate();

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

    // get map of particles nearby to interact with
    public HashMap<int[], Particle> getNeighbors() {
        HashMap<int[], Particle> neighborMap = new HashMap<>();
        if (particleGrid.checkBounds(row - 1, col)) neighborMap.put(new int[]{row - 1, col}, particleGrid.get(row - 1, col));
        if (particleGrid.checkBounds(row + 1, col)) neighborMap.put(new int[]{row + 1, col}, particleGrid.get(row + 1, col));
        if (particleGrid.checkBounds(row, col - 1)) neighborMap.put(new int[]{row, col - 1}, particleGrid.get(row, col - 1));
        if (particleGrid.checkBounds(row, col + 1)) neighborMap.put(new int[]{row, col + 1}, particleGrid.get(row, col + 1));
        return neighborMap;
    }

    // attempt to set this particle on Fire based on parameters
    public void setAfire() {
        if (flammability >= 0 && !onFire && (random.nextDouble() <= flammability)) {
            onFire = true;
            setLifetime(minBurntime, maxBurntime);
        }
    }

    // reduce lifetime and produce fire particles, being replaced with fire
    // particle when burnt out
    public ArrayList<Particle> burn() {
        ArrayList<Particle> newParticles = new ArrayList<>();
        if (onFire) {
            lifetime -= 1;

            // with chance fireCreateChance, add a new Fire particle to an empty space by the particle
            // except for underneath it
            if (random.nextDouble() <= fireCreateChance) {
                HashMap<int[], Particle> neighbors = getNeighbors();
                ArrayList<int[]> emptySpaces = new ArrayList<>();
                for (int[] neighborCoords : neighbors.keySet()) {
                    if (neighbors.get(neighborCoords) == null && (neighborCoords[0] == row - 1)) {
                        emptySpaces.add(neighborCoords);
                    }
                }
                if (emptySpaces.size() > 0) {
                    int[] emptySpace = emptySpaces.get(random.nextInt(emptySpaces.size()));
                    newParticles.add(new FireParticle(emptySpace[0], emptySpace[1], particleGrid, random));
                }

            }

            if (lifetime == 0) {
                newParticles.add(new FireParticle(row, col, particleGrid, random));
            }
        }

        if (newParticles.size() > 0) {
            return newParticles;
        }
        return null;

    }

    // cancels fire and resets lifetime
    public void extinguish() {
        if (onFire) {
            onFire = false;
            setLifetime(minLifetime, maxLifetime);
        }
    }

    // sets lifetime, can be either exactly minLifetime or random range between
    // minLifetime and maxLifetime
    public void setLifetime(int minLifetime, int maxLifetime) {
        if (maxLifetime >= 0) {
            lifetime = random.nextInt(maxLifetime - minLifetime) + minLifetime;
        } else {
            lifetime = minLifetime;
        }
    }

    // used by specific particle types to change properties of other particles adjacent to them
    // allows for creation of new particles
    public ArrayList<Particle> interact() {
        // most particles will not need interact()
        return null;
    }


}
