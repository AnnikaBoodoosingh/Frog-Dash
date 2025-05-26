import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class Log extends Thread {

    private JPanel panel;
    private int x;         // x-position of the log
    private int y;         // y-position of the log
    private int dx;        // horizontal movement speed

    private Color backgroundColour;
    private int logWidth = 50;  // Width of the log
    private int logHeight = 20; // Height of the log

    boolean isRunning;

    public Log(JPanel p, int xPos, int yPos, int speed) {
        panel = p;
        backgroundColour = panel.getBackground();
        x = xPos;
        y = yPos;
        dx = speed; // Speed of the log's movement
    }

    // Draws the log with a brown color and some texture
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(139, 69, 19)); // Brown color for the log
        g2.fill(new Rectangle2D.Double(x, y, logWidth, logHeight)); // Draw the log rectangle

        // Adding texture to simulate wood grain
        g2.setColor(new Color(160, 82, 45)); // Darker brown for texture lines
        for (int i = 0; i < logWidth; i += 10) {
            g2.drawLine(x + i, y, x + i + 5, y + logHeight);  // Vertical wood grain texture
        }
    }

    // Moves the log horizontally based on the speed (dx)
    public void move() {
        if (!panel.isVisible()) return;

        int panelWidth = panel.getWidth();

        // Update the x position of the log, moving it based on dx
        x = x + dx;

        // If the log goes off-screen, reset its position
        if (x > panelWidth) {
            x = -logWidth;  // Start from the left again
        }
    }
    
     public Rectangle getBounds() {
        return new Rectangle(x, y, logWidth, logHeight);
    }

    @Override
    public void run() {
        isRunning = true;

        try {
            while (isRunning) {
                move();
                panel.repaint();  // Refresh the screen
                sleep(100);  // Speed of the log's movement
            }
        } catch (InterruptedException e) {}
    }
}
