//import java.awt.*;
import particle.Particle;
import particle.ParticleGrid;
import particle.ParticleTool;

import java.util.ArrayList;
import java.util.Collections;
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

//        Random r = new Random();
//        int numTestParticles = 20000;
//        for (int i = 0; i<numTestParticles; i++) {
//            int rCol = (int) Math.min(numCols - 1, Math.max(0, numCols * (0.1 * random.nextGaussian() + 0.5)));
//            int rRow = random.nextInt(numRows);
//            particle.Particle particle = new particle.SandParticle(rRow, rCol, Color.YELLOW, particleGrid, r);
//            particleList.add(particle);
//        }

        int fps = 60;
        double timePerFrame = 1000./fps;

        while (true) {

            long frameStartTime = System.currentTimeMillis();

            int[] mouse = sandDisplayPanel.getMouseCoords();
            ParticleTool particleTool = sandSimulatorGUI.getParticleTool();
            int brushWidth = sandSimulatorGUI.getBrushWidth();

            if (mouse != null) {
                if (particleTool == ParticleTool._ERASE) { // ERASE

                    int x = mouse[0];
                    int y = mouse[1];

                    for (int i = -brushWidth; i < brushWidth; i++) {
                        for (int j = -brushWidth; j < brushWidth; j++) {
                            int row = Math.min(numRows - 1, Math.max(0, x + i));
                            int col = Math.min(numCols - 1, Math.max(0, y + j));
                            Particle toRemove = particleGrid.get(row, col);
                            particleList.remove(toRemove);
                            particleGrid.set(row, col, null);
                        }
                    }

                } else {
                    particleList.addAll(particleGrid.spawnParticles(mouse[0], mouse[1], particleTool, brushWidth));
                }
            }

            if (particleTool == ParticleTool._CLEAR) {
                particleGrid.clear();
                particleList.clear();
            }

            sandDisplayPanel.display();

            Collections.shuffle(particleList);

            for (Particle particle : particleList) {
                particle.simulate();
                particle.updatePosition();
            }

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
