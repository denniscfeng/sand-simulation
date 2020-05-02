package particle;

import java.util.Random;

public class ParticleFactory {

    public static Particle getParticle(int row, int col, ParticleTool particleTool, ParticleGrid particleGrid, Random random) {
        switch(particleTool) {
            case WALL:
                return new WallParticle(row, col, particleGrid, random);
            case SAND:
                return new SandParticle(row, col, particleGrid, random);
            case WATER:
                return new WaterParticle(row, col, particleGrid, random);
            default:
                return null;
        }
    }

}
