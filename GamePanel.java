import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class GamePanel extends JPanel {
    private Frog frog;
    private ArrayList<Log> logs;
    private Random rand;
    private int score;
    private int lives;  // Add lives variable
    private boolean reachedTop;
    
    public void resetFrogPosition() {
       int panelWidth = 400;
       int panelHeight = 400;
       int bottomRectangleHeight = 50;
       int startX = panelWidth / 2 - 20;
       int startY = panelHeight - bottomRectangleHeight + 8;
       frog = new Frog(startX, startY, panelWidth, panelHeight); // Reset frog's position
       repaint();  // Redraw after resetting frog
    }
    
    public int getScore() {
       return score;  
    }

    public void setScore(int newScore) {
       score = newScore;  
       repaint();  // Update UI if needed
    }
    
    public int getLives() {
       return lives;
    }
    
    public void setLives(int newLives) {
        lives = newLives;
        repaint(); 
    }

    public GamePanel() {
        setFocusable(true);
        
        int panelWidth = 400;
        int panelHeight = 400;
        int bottomRectangleHeight = 50;
        int startX = panelWidth / 2 - 20;
        int startY = panelHeight - bottomRectangleHeight + 8;
        
        frog = new Frog(startX, startY, panelWidth, panelHeight);
        
        rand = new Random();
        logs = new ArrayList<>();
        score = 0;
        lives = 3;  // Start with 3 lives
        reachedTop = false;

        int logWidth = 50;
        int[] yPositions = {52, 85, 120, 155, 190, 225, 262, 293, 326};  
        int[] logSpeeds = new int[yPositions.length];
        int xStart = -logWidth;
        
        for (int i = 0; i < yPositions.length; i++) {
            logSpeeds[i] = rand.nextInt(14) + 8;
        }

        for (int i = 0; i < yPositions.length; i++) {
            int yPosition = yPositions[i];
            int speed = logSpeeds[i];

            for (int j = 0; j < 8; j++) {
                int xOffset = j * (logWidth + 60); 
                if (xOffset + logWidth > panelWidth) {
                    xOffset = panelWidth - logWidth;
                }
                
                Log log = new Log(this, xOffset, yPosition, speed);
                logs.add(log);
                log.start();
            }
        }
    }

    public void moveFrog(int dx, int dy) {
        int previousY = frog.getY();  // Store current Y position before moving
        frog.move(dx, dy);
        
        int bottomY = getHeight() - 50;  // Bottom Y position where the frog should score
        
        if (frog.getY() <= 48 && !reachedTop) {
            score += 50;
            reachedTop = true;
        }
        
        if (frog.getY() >= bottomY && reachedTop) {
        score += 50;  // Add points when reaching the bottom
        reachedTop = false;  // Reset to allow scoring again when moving up
        }  
   
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int rectangleHeight = 30;
        int topSpace = 48;
        int bottomRectangleHeight = 45;
        int rowCount = 9;
        
        g.setColor(new Color(139, 69, 19));
        g.fillRect(0, 0, getWidth(), topSpace);

        for (int i = 0; i < rowCount; i++) {
            int yPosition = topSpace + (getHeight() - bottomRectangleHeight - topSpace) / rowCount * i;
            g.setColor(new Color(173, 216, 230));
            g.fillRect(0, yPosition, getWidth(), rectangleHeight);
        }

        int bottomYPosition = getHeight() - bottomRectangleHeight;
        g.setColor(new Color(139, 69, 19));
        g.fillRect(0, bottomYPosition, getWidth(), bottomRectangleHeight);

        frog.draw(g);

        for (Log log : logs) {
            log.draw(g);
            if (frog.collidesWith(log)) {
                lives--;  // Reduce lives when hit
                score = Math.max(0, score - 5);
                frog.reset();
                reachedTop = false;

                if (lives <= 0) {
                    JOptionPane.showMessageDialog(null, "Game Over! You lost all your lives.");
                }
                break;
            }
        }

        // Display score and lives
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, 10, 40);
    }
}
