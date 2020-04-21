public class ParticleGrid {

    int numRows;
    int numCols;
    Particle[][] grid;

    public ParticleGrid(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.grid = new Particle[numRows][numCols];
    }

    public void clear() {
        grid = new Particle[numRows][numCols];
    }

    void set(int row, int col, Particle particle) {
        grid[row][col] = particle;
    }

    Particle get(int row, int col) {
        return grid[row][col];
    }

}
