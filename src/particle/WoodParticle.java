package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class WoodParticle extends StaticParticle {


    public static final ArrayList<Color> colors = new ArrayList<>() {{
        add(new Color(82/256f, 50/256f, 24/256f));
        add(new Color(87/256f, 53/256f, 28/256f));
        add(new Color(76/256f, 38/256f, 17/256f));
    }};

    public WoodParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = createColor();
        this.lifetime = -1;
    }

    private Color createColor() {
        return colors.get((row * col) % colors.size());
    }

    @Override
    public void interact(Particle p) {

    }

}
