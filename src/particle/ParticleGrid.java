package particle;

import java.util.ArrayList;
import java.util.Random;

public class ParticleGrid {

    public int numRows;
    public int numCols;
    Particle[][] grid;
    Random random;

    public ParticleGrid(int numRows, int numCols, Random random) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.random = random;
        this.grid = new Particle[numRows][numCols];
    }

    public void clear() {
        grid = new Particle[numRows][numCols];
    }

    public void set(int row, int col, Particle particle) {
        grid[row][col] = particle;
    }

    public void remove(int row, int col) {
        grid[row][col] = null;
    }

    public Particle get(int row, int col) {
        if (!(row >= 0 && row < numRows && col >= 0 && col < numCols)) {
            return null;
        }
        return grid[row][col];
    }

    // Spawn multiple particles given a single (x, y) pixel
    public ArrayList<Particle> spawnParticles(int x, int y, ParticleTool particleTool, int brushWidth) {
        ArrayList<Particle> result = new ArrayList<>();
        for (int i = -brushWidth; i < brushWidth; i++)
            for (int j = -brushWidth; j < brushWidth; j++) {
                int row = Math.min(numRows - 1, Math.max(0, x + i));
                int col = Math.min(numCols - 1, Math.max(0, y + j));

                if (get(row, col) == null) {
                    result.add(ParticleFactory.getParticle(row, col, particleTool, this, random));
                }

            }
        return result;
    }

    public Random getRandom() {
        return random;
    }

}
