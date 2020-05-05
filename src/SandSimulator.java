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
        ArrayList<Particle> particleAddList = new ArrayList<>();
        ArrayList<Particle> particleRemoveList = new ArrayList<>();

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

        // Set fps cap
        int fps = 60;
        double timePerFrame = 1000f/fps;

        // Display avg fps every fpsCountTime ms
        int fpsCountTime = 5000;
        long framesCount = 0;
        long fpsStartTime = System.currentTimeMillis();

        while (true) {
            // Start timing frame
            long frameStartTime = System.currentTimeMillis();

            // Mouse input stuff
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
                sandSimulatorGUI.setParticleTool("SAND"); // Only clear screen once
            }

            // Show frame and count
            sandDisplayPanel.display();
            framesCount += 1;

            // Shuffle particle simulation order
            Collections.shuffle(particleList);

            // Simulate!
            for (Particle particle : particleList) {
                if (particle.lifetime == 0) { // Particles that have a finite lifespan are removed
                    particleRemoveList.add(particle);
                    particleGrid.remove(particle.row, particle.col);
                } else {
                    // Simulate a particle and add any new particles that are created
                    ArrayList<Particle> newParticles = particle.simulate();
                    if (newParticles != null) {
                        particleAddList.addAll(newParticles);
                    }
                    particle.updatePosition();
                }
            }

            particleList.removeAll(particleRemoveList);
            particleRemoveList.clear();

            particleList.addAll(particleAddList);
            particleAddList.clear();


            // End timing frame
            long frameEndTime = System.currentTimeMillis();
            long frameTimeTaken = frameEndTime - frameStartTime;

            // pause simulation if faster than fps
            if (frameTimeTaken < timePerFrame) {
                try {
                    Thread.sleep((long) timePerFrame - frameTimeTaken);
                }
                catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            // Show fps if its been fpsCountTime
            long fpsTimeTaken = frameEndTime - fpsStartTime;
            if (fpsTimeTaken > fpsCountTime) {

                long avgfps = (fpsTimeTaken/framesCount);

                System.out.printf("FPS: %d     Time per frame: %d ms\n", avgfps, 1000/avgfps);
                fpsStartTime = frameEndTime;
                framesCount = 0;

            }

        }
    }

}
