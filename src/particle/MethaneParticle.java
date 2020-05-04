package particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
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
        this.flammability = 1.0;
        this.minBurntime = 1;
        this.maxBurntime = -1;
        this.fireCreateChance = 1.0;
        setLifetime(this.minLifetime, this.maxLifetime);
    }

    private Color createColor() {
        return colors.get(random.nextInt(colors.size()));
    }

    // methane burns differently than other flammable types
    @Override
    public ArrayList<Particle> burn() {
        ArrayList<Particle> newParticles = new ArrayList<>();
        if (onFire) {

            lifetime -= 1;
            if (lifetime < 0) { // to ensure that the lifetime does not get decreased to -1
                lifetime = 0;
            }

            HashMap<int[], Particle> neighbors = getNeighbors();
            ArrayList<int[]> emptySpaces = new ArrayList<>();
            for (int[] neighborCoords : neighbors.keySet()) {
                if (neighbors.get(neighborCoords) == null) {
                    emptySpaces.add(neighborCoords);
                }
            }

            if (lifetime == 0) {
                // if this particle is burnt out, "explode"
//                newParticles.add(new FireParticle(row, col, particleGrid, random));
                for (int[] emptySpace : emptySpaces) {
                    newParticles.add(new FireParticle(emptySpace[0], emptySpace[1], particleGrid, random));
                }
            } else if (random.nextDouble() <= fireCreateChance) {
                // with chance fireCreateChance, add a new Fire particle to an empty space by the particle
                if (emptySpaces.size() > 0) {
                    int[] emptySpace = emptySpaces.get(random.nextInt(emptySpaces.size()));
                    newParticles.add(new FireParticle(emptySpace[0], emptySpace[1], particleGrid, random));
                }
            }

        }

        if (newParticles.size() > 0) {
            return newParticles;
        }
        return null;

    }

    @Override
    public void extinguish() {
        // cannot extinguish methane fire with water!?
    }

    @Override
    public ArrayList<Particle> interact() {

        return burn();

    }

}
