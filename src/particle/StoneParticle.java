package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class StoneParticle extends SolidParticle {

    static final ArrayList<Color> colors = new ArrayList<>() {{
        add(new Color(181/256f, 181/256f, 181/256f));
        add(new Color(165/256f, 165/256f, 165/256f));
        add(new Color(190/256f, 190/256f, 190/256f));
    }};

    public StoneParticle(int row, int col, ParticleGrid particleGrid, Random random) {
        super(row, col, particleGrid, random);
        this.color = createColor();
    }

    private Color createColor() {
        return colors.get((row * col) % colors.size());
    }

    @Override
    public void collide() {

        if (rowNext < particleGrid.numRows) {
            Particle particleUnder = particleGrid.get(rowNext, col);

            if (!canCollide(particleUnder)) {
                pushParticle(particleUnder);
            } else {
                rowNext = row;
            }

        } else {
            rowNext = row;
        }
    }
}
