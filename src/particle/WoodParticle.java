package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class WoodParticle extends StationaryParticle {


    static final ArrayList<Color> colors = new ArrayList<>() {{
        add(new Color(82/256f, 50/256f, 24/256f));
        add(new Color(87/256f, 53/256f, 28/256f));
        add(new Color(90/256f, 49/256f, 17/256f));
    }};

    public WoodParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = createColor();
    }

    private Color createColor() {
        return colors.get((row * col) % colors.size());
    }

}
