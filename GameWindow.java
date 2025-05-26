import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame 
                implements ActionListener, KeyListener, MouseListener {
    
    private Container c;
    private JPanel mainPanel;
    private GamePanel gamePanel;
    private JLabel titleLabel;
    private JLabel timerLabel;
    private JButton startButton;  // Start Game button
    private JButton exitButton;   // Exit Game button
    private JButton playAgainButton; // Play Again button
    private int timeRemaining = 30;
    private Timer gameTimer;  
    private boolean gameStarted = false; // Track game state

    public GameWindow() {
        setTitle("Frog Dash");
        setSize(500, 550);

        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        titleLabel = new JLabel("Frog Dash");
        titleLabel.setFont(new Font("Cooper Black", Font.BOLD, 24));
        titleLabel.setForeground(new Color(173, 216, 230));

        timerLabel = new JLabel("Time Left: " + timeRemaining + "s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setVisible(false); // Hide timer until game starts

        // Start Game Button
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Cooper Black", Font.BOLD, 16));
        startButton.addActionListener(this);

        // Exit Game Button
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Cooper Black", Font.BOLD, 16));
        exitButton.addActionListener(this);

        // Play Again Button (initially invisible)
        playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Cooper Black", Font.BOLD, 16));
        playAgainButton.addActionListener(this);
        playAgainButton.setVisible(false);  // Hidden until game is over

        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(400, 400));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(34, 139, 34));
        titlePanel.add(titleLabel);
        titlePanel.add(timerLabel);

        mainPanel.add(titlePanel);
        mainPanel.add(gamePanel);
        mainPanel.add(startButton); // Add the Start Button
        mainPanel.add(exitButton);  // Add the Exit Button
        mainPanel.add(playAgainButton); // Add Play Again button
        mainPanel.setBackground(new Color(34, 139, 34));

        gamePanel.addMouseListener(this);
        mainPanel.addKeyListener(this);
        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();

        c = getContentPane();
        c.add(mainPanel);

        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Timer setup (starts when "START" button is clicked)
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;  
                timerLabel.setText("Time Left: " + timeRemaining + "s");
                
                if (gamePanel.getLives() <= 0) {
                  gameTimer.stop(); // Stop the timer
                  JOptionPane.showMessageDialog(null, "Game Over! You lost all your lives.");
                  showPlayAgainButton();
                } else if (timeRemaining <= 0) {
                  gameTimer.stop(); // Stop the timer
                  JOptionPane.showMessageDialog(null, "Time's Up! Game Over!");
                  showPlayAgainButton(); // Show the Play Again button when time is up
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startGame();
        } else if (e.getSource() == exitButton) {
            System.exit(0);  // Exit the application when the Exit button is pressed
        } else if (e.getSource() == playAgainButton) {
            playAgain(); // Restart the game when Play Again is clicked
        }
    }

    private void startGame() {
        gameStarted = true;
        startButton.setVisible(false); // Hide the start button
        timerLabel.setVisible(true); // Show the timer
        gameTimer.start(); // Start the countdown
        gamePanel.resetFrogPosition(); // Reset Frog Position
        gamePanel.setScore(0);  // Reset Score
        gamePanel.setLives(3);
        mainPanel.requestFocusInWindow(); // Ensure key input works
    }

    private void showPlayAgainButton() {
        playAgainButton.setVisible(true); // Show Play Again button
        startButton.setVisible(false);    // Hide the start button
    }

    private void playAgain() {
        timeRemaining = 30;  // Reset the timer
        timerLabel.setText("Time Left: " + timeRemaining + "s");
        timerLabel.setVisible(true);  // Show timer
        gamePanel.repaint();  // Reset the game panel
        playAgainButton.setVisible(false); // Hide the Play Again button
        startButton.setVisible(true);  // Show the start button again
        gameStarted = false;  // Reset the game state
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameStarted) return; // Ignore key presses before game starts

        int key = e.getKeyCode();
        int step = 34;

        if (key == KeyEvent.VK_UP) {
            gamePanel.moveFrog(0, -step);
        } else if (key == KeyEvent.VK_DOWN) {
            gamePanel.moveFrog(0, step);
        } else if (key == KeyEvent.VK_LEFT) {
            gamePanel.moveFrog(-step, 0);
        } else if (key == KeyEvent.VK_RIGHT) {
            gamePanel.moveFrog(step, 0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    public static void main(String[] args) {
        new GameWindow();
    }
}
