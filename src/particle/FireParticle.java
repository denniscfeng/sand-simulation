package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class FireParticle extends GasParticle {

    public static final ArrayList<Color> colors = new ArrayList<>() {{
        add(new Color(255/256f, 117/256f, 0/256f));
        add(new Color(215/256f, 53/256f, 2/256f));
        add(new Color(255/256f, 0/256f, 0/256f));
    }};

    public FireParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = createColor();
        this.minLifetime = 20;
        this.maxLifetime = 120;
        this.onFire = true;
        setLifetime(this.minLifetime, this.maxLifetime);
    }

    @Override
    public ArrayList<Particle> simulate() {

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

        if (onFire) {
            lifetime -= 1;
        } else {
            lifetime = 0;
        }

        collide();

        return interact();

    }

    @Override
    public void extinguish() {
        this.onFire = false;
    }

    private Color createColor() {
        return colors.get(random.nextInt(colors.size()));
    }

    @Override
    public ArrayList<Particle> interact() {

        for (Particle neighborParticle : getNeighbors().values()) {

            // attempt to set neighbor particle on fire
            if (neighborParticle != null) {
                neighborParticle.setAfire();
            }

        }

        return null;

    }

}
