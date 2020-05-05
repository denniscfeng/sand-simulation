package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class OilParticle extends LiquidParticle {

    public static final ArrayList<Color> colors = new ArrayList<>() {{
        add(new Color(55/256f, 58/256f, 54/256f));
        add(new Color(52/256f, 60/256f, 54/256f));
        add(new Color(55/256f, 58/256f, 52/256f));
    }};

    public OilParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = createColor();
        this.flammability = 0.9;
        this.minBurntime = 50;
        this.maxBurntime = 300;
        this.fireCreateChance = 0.15;
    }

    private Color createColor() { return colors.get((row * col) % colors.size()); }

    @Override
    public ArrayList<Particle> interact() {
        return burn();
    }
}

