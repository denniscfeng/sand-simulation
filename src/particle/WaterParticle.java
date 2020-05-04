package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class WaterParticle extends LiquidParticle {

    public static final ArrayList<Color> colors = new ArrayList<>() {{
        add(new Color(88/256f, 128/256f, 195/256f));
        add(new Color(90/256f, 131/256f, 198/256f));
        add(new Color(85/256f, 123/256f, 187/256f));
    }};

    public WaterParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = createColor();
        this.lifetime = -1;
    }

    private Color createColor() {
        return colors.get((row * col) % colors.size());
    }

    @Override
    public void interact(Particle p) {
        // Check for any special interactions with other particles
    }

}
