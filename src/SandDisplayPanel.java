import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import particle.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class SandDisplayPanel extends JPanel implements MouseListener, MouseMotionListener, GLEventListener {

    // OpenGL vars
    final private GLProfile profile;
    final private GLCapabilities capabilities;
    GLCanvas glcanvas;

    ParticleGrid particleGrid;
    ArrayList<Particle> particleList;

    private int cellSize;
    private float scaleX;
    private float scaleY;
    int[] mouse;


    public SandDisplayPanel(ParticleGrid particleGrid, ArrayList<Particle> particleList) {
        this.particleGrid = particleGrid;
        this.particleList = particleList;

        cellSize = Math.max(1, 600 / Math.max(particleGrid.numRows, particleGrid.numCols));
        scaleX = 2f / particleGrid.numCols;
        scaleY = 2f / particleGrid.numRows;
        profile = GLProfile.get(GLProfile.GL2);
        capabilities = new GLCapabilities(profile);
        Dimension dims = new Dimension(particleGrid.numCols * cellSize, particleGrid.numRows * cellSize);

        // CANVAS
        glcanvas = new GLCanvas(capabilities);
        glcanvas.addGLEventListener(this);
        glcanvas.setSize(dims);
        glcanvas.addMouseListener(this);
        glcanvas.addMouseMotionListener(this);

        // JFRAME
        this.add(glcanvas);
//        this.setPreferredSize(dims);
        this.setSize(dims);

    }

    private void setColor(GL2 gl, int row, int col, Color color) {
        float start_x = (col * scaleX - 1);
        float start_y = (row * scaleY - 1) * -1f;
        float end_x = start_x + scaleX;
        float end_y = start_y + scaleY;
        float[] c = color.getColorComponents(null);

        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3f(c[0], c[1], c[2]);
        gl.glVertex2f(start_x, start_y);
        gl.glVertex2f(end_x, start_y);
        gl.glVertex2f(end_x, end_y);
        gl.glVertex2f(start_x, end_y);
        gl.glEnd();
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

    int[] getMouseCoords() {
        return mouse;
    }

    // Mouse input methods
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

    // GlEventListener methods
    @Override
    public void init(GLAutoDrawable drawable) { }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // Without this function it would produce an error on close
        System.exit(0);
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        // Solving High-DPI issue
        double dpiScalingFactor = ((Graphics2D) getGraphics()).getTransform().getScaleX();
        int viewWidth = (int) (particleGrid.numCols * cellSize * dpiScalingFactor);
        int viewHeight = (int) (particleGrid.numCols * cellSize * dpiScalingFactor);
        gl.glViewport(0, 0, viewWidth, viewHeight);

        for (Particle particle : particleList) {
            setColor(gl, particle.row, particle.col, particle.color);
        }

    }

    public void display() {
        glcanvas.display();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int i, int i1, int i2, int i3) { }

    public void handleFileUpload(File file) {
        particleGrid.clear();
        particleList.clear();
        BufferedImage img;

        try {
            img = ImageIO.read(file);
        } catch (Exception e) {
            System.out.println("Error with image upload");
            return;
        }

        ArrayList<Particle> particles = new ArrayList<>();
        int width = img.getWidth();
        int height = img.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int  clr   = img.getRGB(x, y);
                int  r = (clr & 0x00ff0000) >> 16;
                int  g = (clr & 0x0000ff00) >> 8;
                int  b =  clr & 0x000000ff;
                int[] coords = getCoordFromImage(x, y, width, height);
                Particle temp = getClosestParticle(new Color(r, g, b), coords[0], coords[1]);
                particleGrid.set(coords[0], coords[1], temp);
                particles.add(temp);
            }
        }

        particleList.addAll(particles);
    }

    private int[] getCoordFromImage(int x, int y, int width, int height) {
        int[] result = new int[2];
        result[0] = particleGrid.numCols * y / height;
        result[1] = particleGrid.numRows * x / width;
        return result;
    }

    ArrayList<Color> particleColors = new ArrayList<>() {{
        add(SandParticle.colors.get(0));
        add(WaterParticle.colors.get(0));
    }};

    private Particle getClosestParticle(Color c, int row, int col) {
        double closest = Integer.MAX_VALUE;
        int particleType = -1;

        for (int i = 0; i < particleColors.size(); i++) {
            double diff = colorDistance(c, particleColors.get(i));
            if (diff < closest) {
                closest = diff;
                particleType = i;
            }
        }

        switch (particleType) {
            case 0:
                return new SandParticle(row, col, particleGrid, particleGrid.getRandom());
            case 1:
                return new WaterParticle(row, col, particleGrid, particleGrid.getRandom());
            default:
                return null;
        }


    }

    private double colorDistance(Color c1, Color c2) {
        double dRed = Math.pow(c1.getRed() - c2.getRed(), 2);
        double dGreen = Math.pow(c1.getGreen() - c2.getGreen(), 2);
        double dBlue = Math.pow(c1.getBlue() - c2.getBlue(), 2);
        return dRed + dGreen + dBlue;
    }

}
