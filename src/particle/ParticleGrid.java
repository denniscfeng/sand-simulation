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

    public Particle get(int row, int col) {
        return grid[row][col];
    }

    // Spawn multiple particles given a single (x, y) pixel
    public ArrayList<Particle> spawnParticles(int x, int y, ParticleType particleType, int brushWidth) {
        ArrayList<Particle> result = new ArrayList<>();
        for (int i = -brushWidth; i < brushWidth; i++)
            for (int j = -brushWidth; j < brushWidth; j++) {
                int row = Math.min(numRows - 1, Math.max(0, x + i));
                int col = Math.min(numCols - 1, Math.max(0, y + j));

                if (get(row, col) == null) {

                    Particle temp = null;
                    if (particleType == ParticleType.SAND) {
                        temp = new SandParticle(row, col, this, random);
                    } else if (particleType == ParticleType.WATER) {
                        temp = new WaterParticle(row, col, this, random);
                    }

                    result.add(temp);

                }

            }
        return result;
    }

}
