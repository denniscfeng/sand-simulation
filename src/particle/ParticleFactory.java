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
            case FIRE:
                return new FireParticle(row, col, particleGrid, random);
            case WOOD:
                return new WoodParticle(row, col, particleGrid, random);
            case LAVA:
                return new LavaParticle(row, col, particleGrid, random);
            case STONE:
                return new StoneParticle(row, col, particleGrid, random);
            case METHANE:
                return new MethaneParticle(row, col, particleGrid, random);
            case OIL:
                return new OilParticle(row, col, particleGrid, random);
            case _ERASE:
                return null;
            default:
                return null;
        }
    }

}
