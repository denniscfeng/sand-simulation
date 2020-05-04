package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MethaneParticle extends GasParticle {

    static final ArrayList<Color> colors = new ArrayList<>() {{
        add(new Color(21/256f, 21/256f, 21/256f));
        add(new Color(31/256f, 31/256f, 31/256f));
        add(new Color(41/256f, 41/256f, 41/256f));
    }};

    public MethaneParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = createColor();
        this.minLifetime = 50;
        this.maxLifetime = 200;
        setLifetime(this.minLifetime, this.maxLifetime);
    }

    private Color createColor() {
        return colors.get(random.nextInt(colors.size()));
    }

    @Override
    public ArrayList<Particle> interact() {
        // Check for any special interactions with other particles
        return null;
    }

}
