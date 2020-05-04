package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SandParticle extends SolidParticle{

    static final ArrayList<Color> colors = new ArrayList<>() {{
        add(new Color(0.855f, 0.706f, 0.478f));
        add(new Color(0.800f, 0.686f, 0.478f));
        add(new Color(0.855f, 0.720f, 0.500f));
    }};

    public SandParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = createColor();
    }

    private Color createColor() {
        return colors.get((row * col) % colors.size());
    }

    @Override
    public void interact() {
        // Check for any special interactions with other particles
    }

}
