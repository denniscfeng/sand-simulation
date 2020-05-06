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
    }

    private Color createColor() {
        return colors.get((row * col) % colors.size());
    }

    @Override
    public boolean canCollide(Particle p) {
        return !(p instanceof OilParticle) && (p instanceof LiquidParticle || p instanceof SolidParticle || p instanceof StaticParticle);
    }

    @Override
    public ArrayList<Particle> interact() {

        for (Particle neighborParticle : getNeighbors().values()) {

            // if water contacts a fire particle OR something that is
            // on fire, put it out
            if (neighborParticle != null && neighborParticle.onFire) {
                neighborParticle.extinguish();
            }

        }


        return null;
    }

}
