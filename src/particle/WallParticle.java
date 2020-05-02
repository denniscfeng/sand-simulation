package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class WallParticle extends StationaryParticle {


    static final ArrayList<Color> colors = new ArrayList<>() {{
        add(new Color(108/256f, 108/256f, 108/256f));
        add(new Color(99/256f, 99/256f, 99/256f));
        add(new Color(85/256f, 85/256f, 85/256f));
    }};

    public WallParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = createColor();
    }

    private Color createColor() {
        return colors.get((row * col) % colors.size());
    }

}
