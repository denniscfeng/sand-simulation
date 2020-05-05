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
    public void collide() {

        // If we are not at the bottom
        if (rowNext < particleGrid.numRows) {

            Particle particleUnder = particleGrid.get(rowNext, col);

            // If the cell below is not another liquid or solid particle (can be empty or gas(?)),
            // fall down and push the other particle aside, also fall below oil particles
            if (!canCollide(particleUnder) || particleUnder instanceof OilParticle) {

                pushParticle(particleUnder);

            } else {

                int colLeft = col - 1;
                int colRight = col + 1;
                Particle particleLeft = particleGrid.get(rowNext, colLeft);
                Particle particleRight = particleGrid.get(rowNext, colRight);

                // Check if the cell to the bottom left is in frame and not a solid particle
                if (colLeft >= 0 && !canCollide(particleLeft)) {

                    // Check if the cell to the bottom right is in frame and not a solid particle
                    if (colRight < particleGrid.numCols && !canCollide(particleRight)) {

                        // If so, choose between bottom left and bottom right randomly. Else, choose left
                        colNext = (random.nextInt(2) == 0) ? colLeft : colRight;

                        // Otherwise, just fall into bottom left
                    } else {
                        colNext = colLeft;
                    }

                    // Push the other particle aside
                    pushParticle(particleGrid.get(rowNext, colNext));

                    // If bottom left is full, check cell at bottom right similarly if it contains a SolidParticle or not
                } else if (colRight < particleGrid.numCols && !canCollide(particleRight)) {

                    colNext = colRight;

                    // Push the other particle aside
                    pushParticle(particleGrid.get(rowNext, colNext));

                    // If bottom left and bottom right are full, then stay in same row, but move to left or right column
                } else {
                    rowNext = row;

                    // Do similar checks to see if cells to immediate right and left are empty

                    particleLeft = particleGrid.get(row, colLeft);
                    particleRight = particleGrid.get(row, colRight);

                    if (colLeft >= 0 && !canCollide(particleLeft)) {
                        if (colRight < particleGrid.numCols && !canCollide(particleRight)) {
                            colNext = (random.nextInt(2) == 0) ? colLeft : colRight;
                        } else {
                            colNext = colLeft;
                        }
                    } else if (colRight < particleGrid.numCols && !canCollide(particleRight)) {
                        colNext = colRight;
                    }
                }

            }

            // If we are at bottom of frame, stay in same place
        } else {
            rowNext = row;
        }

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
