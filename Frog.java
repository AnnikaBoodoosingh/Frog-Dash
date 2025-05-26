import java.awt.*;

public class Frog {
    private int x, y;
    private int size;
    private int panelWidth, panelHeight; // Game panel size limits

    public Frog(int startX, int startY, int panelWidth, int panelHeight) {
        x = startX;
        y = startY;
        size = 20; // Frog size
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Frog head
        g2d.setColor(new Color(34, 139, 34));  
        g2d.fillOval(x, y, size, size);

        // Eyes
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x + 2, y - 1, 5, 5);  
        g2d.fillOval(x + 14, y - 1, 5, 5);

        // Pupils
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x + 2, y - 1, 2, 2);
        g2d.fillOval(x + 14, y - 1, 2, 2);

        // Mouth
        g2d.setColor(Color.BLACK);
        g2d.drawArc(x + 5, y + 10, 10, 5, 0, -180);

        // Blush (pink cheeks)
        g2d.setColor(new Color(255, 182, 193)); // Light pink
        g2d.fillOval(x + 3, y + 6, 4, 2);
        g2d.fillOval(x + 14, y + 6, 4, 2); 
    }

    public void move(int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;

        // Ensure the frog stays inside the panel
        if (newX >= 0 && newX + size <= panelWidth) {
            x = newX;
        }
        if (newY >= 0 && newY + size <= panelHeight) {
            y = newY;
        }
    }
    
    public boolean collidesWith(Log log) {
        Rectangle frogBounds = new Rectangle(x, y, size, size);
        return frogBounds.intersects(log.getBounds());
    }

    public int getX() { return x; }
    public int getY() { return y; }
    
    // Method to reset frog's position
    public void reset() {
        // Reset to the starting position
        int startX = panelWidth / 2 - 20;
        int startY = panelHeight - 50 + 8;  
        this.x = startX;
        this.y = startY;
    }
}
