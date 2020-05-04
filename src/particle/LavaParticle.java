package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LavaParticle extends LiquidParticle {

    static final ArrayList<Color> colors = new ArrayList<>() {{
        add(new Color(251/256f, 108/256f, 21/256f));
        add(new Color(255/256f, 113/256f, 23/256f));
        add(new Color(246/256f, 103/256f, 20/256f));
    }};

    public LavaParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = createColor();
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
        return null;
    }
}
