import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SandSimulator {

    public static void main(String[] args) {

        int numRows = 300;
        int numCols = 300;

        Random random = new Random();

        ParticleGrid particleGrid = new ParticleGrid(numRows, numCols, random);
        ArrayList<Particle> particleList = new ArrayList<>();

        SandDisplayPanel sandDisplayPanel = new SandDisplayPanel(particleGrid, particleList);
        SandSimulatorGUI sandSimulatorGUI = new SandSimulatorGUI(sandDisplayPanel);

        Random r = new Random();
        int numTestParticles = 20000;
        for (int i = 0; i<numTestParticles; i++) {
            int rCol = (int) Math.min(numCols - 1, Math.max(0, numCols * (0.1 * random.nextGaussian() + 0.5)));
            int rRow = random.nextInt(numRows);
            Particle particle = new Particle(rRow, rCol, Color.YELLOW, particleGrid, r);
            particleList.add(particle);
        }

        // probably need TimerTask for this
        int fps = 60;
        double timePerFrame = 1000./fps;

        while (true) {

            long frameStartTime = System.currentTimeMillis();

            int[] mouse = sandDisplayPanel.getMouseCoords();
            int tool = sandDisplayPanel.getTool();

            if (mouse != null) {
                switch (tool) {
                    case 1: // ERASE
                        Particle toRemove = particleGrid.get(mouse[0], mouse[1]);
                        particleList.remove(toRemove);
                        particleGrid.set(mouse[0], mouse[1], null);
                        break;
                    case 2: // SAND
                        particleList.addAll(particleGrid.spawnParticles(mouse[0], mouse[1], particleGrid));
                        break;
                }
            }

            if (tool == 0) {
                particleGrid.clear();
                particleList.clear();
            }

            sandDisplayPanel.display();

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

}
