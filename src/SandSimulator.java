import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SandSimulator {

    public static void main(String[] args) {

        int numRows = 200;
        int numCols = 200;

        ParticleGrid particleGrid = new ParticleGrid(numRows, numCols);
        ArrayList<Particle> particleList = new ArrayList<>();

        SandDisplayPanel sandDisplayPanel = new SandDisplayPanel(particleGrid, particleList);
        SandSimulatorGUI sandSimulatorGUI = new SandSimulatorGUI(sandDisplayPanel);

        /*
        Random random = new Random();
        int numTestParticles = 20000;
        for (int i = 0; i<numTestParticles; i++) {
            int rCol = (int) Math.min(numCols - 1, Math.max(0, numCols * (0.1 * random.nextGaussian() + 0.5)));
            int rRow = random.nextInt(numRows);
            Particle particle = new Particle(rRow, rCol, Color.cyan, particleGrid, random);
            particleList.add(particle);
        }
         */

        // probably need TimerTask for this
        int fps = 60;
        double timePerFrame = 1000./fps;

        while (true) {

            long frameStartTime = System.currentTimeMillis();

            int[] mouse = sandDisplayPanel.getMouseCoords();
            int tool = sandDisplayPanel.getTool();

            if (mouse != null) {
                if (tool == 1) { // SAND
                    particleList.addAll(spawnParticles(mouse[0], mouse[1], particleGrid));
                }
            }

            if (tool == 0) {
                particleGrid.clear();
                particleList.clear();
                sandDisplayPanel.clearDisplay();
            } else {
                sandDisplayPanel.drawParticles();
            }

            sandDisplayPanel.repaint();

            for (Particle particle : particleList)
                particle.simulate();

            long frameEndTime = System.currentTimeMillis();
            long frameTimeTaken = frameEndTime - frameStartTime;

            if (frameTimeTaken < timePerFrame) {
                try {
                    Thread.sleep((long) timePerFrame - frameTimeTaken);
                }
                catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // Spawn multiple particles given a single (x, y) pixel
    private static List<Particle> spawnParticles(int x, int y, ParticleGrid grid) {
        List<Particle> result = new ArrayList<>();
        Random r = new Random();
        for (int i = -3; i < 3; i++)
            for (int j = -3; j < 3; j++) {
                int row = Math.min(grid.numRows - 1, Math.max(0, x + i));
                int col = Math.min(grid.numCols - 1, Math.max(0, y + j));
                Particle temp = new Particle(row, col, Color.yellow, grid, r);
                result.add(temp);
            }

        return result;
    }
}
