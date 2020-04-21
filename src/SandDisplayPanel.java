import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SandDisplayPanel extends JPanel {

    ParticleGrid particleGrid;
    ArrayList<Particle> particleList;

    int cellSize;
    private BufferedImage image;

    public SandDisplayPanel(ParticleGrid particleGrid, ArrayList<Particle> particleList) {
        this.particleGrid = particleGrid;
        this.particleList = particleList;

        this.cellSize = Math.max(1, 600 / Math.max(particleGrid.numRows, particleGrid.numCols));

        this.image = new BufferedImage(particleGrid.numCols * cellSize, particleGrid.numRows * cellSize, BufferedImage.TYPE_INT_RGB);
        this.setPreferredSize(new Dimension(particleGrid.numCols * cellSize, particleGrid.numRows * cellSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    private void setColor(int row, int col, Color color) {
        Graphics g = image.getGraphics();
        g.setColor(color);
        g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
    }

    void drawParticles() {
        for (Particle particle : particleList) {
            setColor(particle.rowLast, particle.colLast, Color.black);
            setColor(particle.row, particle.col, particle.color);
        }
    }

}
