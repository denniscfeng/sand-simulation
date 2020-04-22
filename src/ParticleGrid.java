import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ParticleGrid {

    int numRows;
    int numCols;
    Particle[][] grid;
    Random random;

    public ParticleGrid(int numRows, int numCols, Random random) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.random = random;
        this.grid = new Particle[numRows][numCols];
    }

    void clear() {
        grid = new Particle[numRows][numCols];
    }

    void set(int row, int col, Particle particle) {
        grid[row][col] = particle;
    }

    Particle get(int row, int col) {
        return grid[row][col];
    }

    // Spawn multiple particles given a single (x, y) pixel
    ArrayList<Particle> spawnParticles(int x, int y, ParticleGrid grid) {
        ArrayList<Particle> result = new ArrayList<>();
        for (int i = -3; i < 3; i++)
            for (int j = -3; j < 3; j++) {
                int row = Math.min(grid.numRows - 1, Math.max(0, x + i));
                int col = Math.min(grid.numCols - 1, Math.max(0, y + j));
                Particle temp = new Particle(row, col, Color.yellow, grid, random);
                result.add(temp);
            }
        return result;
    }

}
