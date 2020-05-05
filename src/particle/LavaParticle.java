package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class LavaParticle extends LiquidParticle {

    public static final ArrayList<Color> colors = new ArrayList<>() {{
        add(new Color(231/256f, 128/256f, 31/256f));
        add(new Color(235/256f, 133/256f, 33/256f));
        add(new Color(226/256f, 123/256f, 30/256f));
    }};

    public LavaParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = createColor();
        this.fireCreateChance = 0.001;
    }

    private Color createColor() { return colors.get((row * col) % colors.size()); }

    // Override method to force lava to flow slower
    @Override
    public ArrayList<Particle> simulate() {

        // Always want lava to fall normally if nothing beneath it
        if (row < particleGrid.numRows - 1 && particleGrid.get(row + 1, col) == null) {
            rowNext = row + 1;
            colNext = col;

        // If there is something beneath lava particle, then flip coin to make it settle slower
        } else if (random.nextInt(2) == 0) {
            collide();

        } else {
            rowNext = row;
            colNext = col;
        }

        return interact();

    }

    @Override
    public ArrayList<Particle> interact() {
        // Check for any special interactions with other particles
        ArrayList<Particle> newParticles = new ArrayList<>();
        for (Particle neighborParticle : getNeighbors().values()) {

            // attempt to set neighbor particle on fire
            if (neighborParticle != null) {
                if (neighborParticle instanceof WaterParticle) {
                    newParticles.add(new StoneParticle(row, col, particleGrid, random));
                    setLifetime(0, -1);
                    neighborParticle.setLifetime(0, -1);
                } else {
                    neighborParticle.setAfire();
                }
            }

        }

        // with chance fireCreateChance, add a new Fire particle above the lava particle
        if (random.nextDouble() <= fireCreateChance) {
            HashMap<int[], Particle> neighbors = getNeighbors();
            for (int[] neighborCoords : neighbors.keySet()) {
                if (neighbors.get(neighborCoords) == null && (neighborCoords[0] == row - 1)) {
                    newParticles.add(new FireParticle(row - 1, col, particleGrid, random));
                    break;
                }
            }

        }

        if (newParticles.size() > 0) {
            return newParticles;
        }
        return null;

    }
}
