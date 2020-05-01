package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class WallParticle extends StaticParticle {

    static final ArrayList<Color> colors = new ArrayList<>() {{
        add(new Color(153/256f, 153/256f, 153/256f));
        add(new Color(171/256f, 171/256f, 171/256f));
        add(new Color(166/256f, 166/256f, 166/256f));
    }};

    public WallParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = createColor();
    }

    private Color createColor() {
        return colors.get((row * col) % colors.size());
    }

    @Override
    public void interact(Particle p) {
        // Check for any special interactions with other particles
    }

}
