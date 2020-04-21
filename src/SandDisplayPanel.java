import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SandDisplayPanel extends JPanel implements MouseListener, MouseMotionListener {

    ParticleGrid particleGrid;
    ArrayList<Particle> particleList;

    int cellSize;
    private int tool;
    private BufferedImage image;
    int[] mouse;

    public SandDisplayPanel(ParticleGrid particleGrid, ArrayList<Particle> particleList) {
        this.particleGrid = particleGrid;
        this.particleList = particleList;
        addMouseListener(this);
        addMouseMotionListener(this);

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

    public void clearDisplay() {
        image = new BufferedImage(particleGrid.numCols * cellSize, particleGrid.numRows * cellSize, BufferedImage.TYPE_INT_RGB);
    }

    public int getTool() {
        return tool;
    }
    public void setTool(int newTool) {
        tool = newTool;
    }

    private int[] toLocation(MouseEvent e) {
        int numRows = particleGrid.numRows;
        int numCols = particleGrid.numCols;
        int row = e.getY() / cellSize;
        int col = e.getX() / cellSize;
        if (row < 0 || row >= numRows || col < 0 || col >= numCols)
            return null;
        int[] loc = new int[2];
        loc[0] = row;
        loc[1] = col;
        return loc;
    }

    public int[] getMouseCoords() {
        return mouse;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouse = toLocation(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouse = toLocation(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouse = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }
}
