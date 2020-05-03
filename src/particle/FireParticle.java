package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class FireParticle extends GasParticle {

    static final ArrayList<Color> colors = new ArrayList<>() {{
        add(new Color(255/256f, 117/256f, 0/256f));
        add(new Color(215/256f, 53/256f, 2/256f));
        add(new Color(255/256f, 0/256f, 0/256f));
    }};

    public FireParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = createColor();
        this.lifetime = random.nextInt(100) + 20;
    }

    @Override
    public void simulate() {

        // particle moves up, occasionally moving side to side
        int direction = random.nextInt(4);
        if (direction == 0) { // go left
            rowNext = row;
            colNext = col - 1;
        } else if (direction <= 1) { // go right
            rowNext = row;
            colNext = col + 1;
        } else { // go up
            rowNext = row - 1;
            colNext = col;
        }

        lifetime -= 1;

        collide();

        // somewhere here goes interact()

    }

    private Color createColor() {
        return colors.get(random.nextInt(colors.size()));
    }

    @Override
    public void interact(Particle p) {
        // Check for any special interactions with other particles
    }

}