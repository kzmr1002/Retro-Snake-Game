import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.stream.IntStream;
import javax.sound.sampled.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    private int DELAY;

    private String playerName;
    private Apple apple;
    private Snake snake;
    private int applesEaten;
    private char direction;
    private boolean running = false;
    private boolean gameOverTrue = false;
    private Timer timer;
    private Random random;
    private Clip backgroundSound;
    private Clip gameoverSound;
    private Clip appleSound;
    private JButton restartButton;
    private JButton backToMenuButton;

    // konstruktor
    public GamePanel(MainApp app) {

        snake = new Snake();
        apple = new Apple();

        // restart button
        restartButton = new JButton();
        restartButton.setBounds(200, 400, 300, 300);
        restartButton.addActionListener(this);
        restartButton.setText("Restart");
        restartButton.setBackground(Color.GREEN);
        restartButton.setVisible(false);

        // backtoMenu button
        backToMenuButton = new JButton();
        backToMenuButton.setBounds(400, 400, 300, 300);
        backToMenuButton.addActionListener(e -> app.switchToMenuPanel());
        backToMenuButton.setText("Back to Menu");
        backToMenuButton.setBackground(Color.GREEN);
        backToMenuButton.setVisible(false);

        random = new Random();

        // gamePanel
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.GREEN));
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        add(restartButton);
        add(backToMenuButton);

    }

    // setdifficulty
    public void setDifficulty(MainApp app) {
        switch (app.getDifficulty()) {
            case 1:
                DELAY = 100;
                break;
            case 2:
                DELAY = 75;
                break;
            case 3:
                DELAY = 50;
                break;
            default:
                DELAY = 75;
        }
    }

    // start Game
    public void startGame(MainApp app) {
        setDifficulty(app);
        gameOverTrue = false;
        playerName = app.getPlayerName();
        applesEaten = 0;
        snake.bodyparts = 6;
        direction = 'R';
        restartButton.setVisible(false);
        backToMenuButton.setVisible(false);

        // reset snake position
        for (int i = 0; i < snake.x.length; i++) {
            snake.x[i] = 0;
            snake.y[i] = 0;
        }
        // reset snake head
        snake.x[0] = SCREEN_WIDTH / 2;
        snake.y[0] = SCREEN_HEIGHT / 2;

        playMusic();
        apple = new Apple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    // draw
    public void draw(Graphics g) {

        if (running) {
            // draw apple
            g.setColor(Color.RED);
            g.fillOval(apple.getX(), apple.getY(), UNIT_SIZE, UNIT_SIZE);

            // draw snake
            for (int i = 0; i < snake.bodyparts; i++) {
                g.setColor(Color.GREEN);
                g.fillRect(snake.x[i], snake.y[i], UNIT_SIZE, UNIT_SIZE);
            }

            // score text
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                    g.getFont().getSize());

        } else {
            gameOver(g);
        }
    }

    // move
    public void move() {
        // move snake body
        for (int i = snake.bodyparts; i > 0; i--) {
            snake.x[i] = snake.x[i - 1];
            snake.y[i] = snake.y[i - 1];
        }

        // directions and snake head
        switch (direction) {
            case 'U':
                snake.y[0] = snake.y[0] - UNIT_SIZE;
                break;
            case 'D':
                snake.y[0] = snake.y[0] + UNIT_SIZE;
                break;
            case 'L':
                snake.x[0] = snake.x[0] - UNIT_SIZE;
                break;
            case 'R':
                snake.x[0] = snake.x[0] + UNIT_SIZE;
        }

    }

    // check Apple
    public void checkApple() {
        if (snake.x[0] == apple.getX() && snake.y[0] == apple.getY()) {
            snake.bodyparts++;
            applesEaten++;
            playAppleSound();
            apple = new Apple();

        }
    }

   

    // collisions streamekkel
    public void checkCollisions() {
        // body collision
        boolean collidedWithSelf = IntStream.range(1, snake.bodyparts)
                .anyMatch(i -> snake.x[0] == snake.x[i] && snake.y[0] == snake.y[i]);

        // border collision
        boolean collidedWithBorder = snake.x[0] < 0 || snake.x[0] >= SCREEN_WIDTH || snake.y[0] < 0
                || snake.y[0] >= SCREEN_HEIGHT;

        if (collidedWithSelf || collidedWithBorder) {
            running = false;
            timer.stop();
        }
    }

    // play background music-----
    public void playMusic() {
        try {
            File file = new File("clips/background.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            backgroundSound = AudioSystem.getClip();
            backgroundSound.open(audioIn);
            backgroundSound.start();
            backgroundSound.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // play apple sound----------
    public void playAppleSound() {
        try {
            File file = new File("clips/apple.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            appleSound = AudioSystem.getClip();
            appleSound.open(audioIn);
            appleSound.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // play gameover sound------
    public void playGameoverSound() {
        try {
            File file = new File("clips/gameover.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            gameoverSound = AudioSystem.getClip();
            gameoverSound.open(audioIn);
            gameoverSound.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // write score and name to scores.txt------------
    public void appendToScoreFile(String playername, int score) {
        try (FileWriter writer = new FileWriter("scores.txt", true)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTime = LocalDateTime.now().format(dtf);

            String formattedLine = String.format("%-20s %-10s %s%n", playername, "Score: " + score, dateTime);

            writer.write(formattedLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // GAME OVER--------
    public void gameOver(Graphics g) {

        // append scores to text file
        if (!gameOverTrue) {
            appendToScoreFile(playerName, applesEaten);
            gameOverTrue = true;
        }

        // buttons
        restartButton.setVisible(true);
        backToMenuButton.setVisible(true);

        // sound
        backgroundSound.stop();
        playGameoverSound();

        // paint snake gray
        for (int i = 0; i < snake.bodyparts; i++) {
            g.setColor(Color.GRAY);
            g.fillRect(snake.x[i], snake.y[i], UNIT_SIZE, UNIT_SIZE);
        }

        // score text
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2,
                SCREEN_HEIGHT / 2 + 3 * UNIT_SIZE);

        // gameover text
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

    }

    // RESTART
    public void restartGame() {

        applesEaten = 0;
        snake.bodyparts = 6;
        direction = 'R';

        for (int i = 0; i < snake.x.length; i++) {
            snake.x[i] = 0;
            snake.y[i] = 0;
        }

        // place snake in middle
        snake.x[0] = SCREEN_WIDTH / 2 - UNIT_SIZE * (snake.bodyparts % 2 == 0 ? 1 : 0);
        snake.y[0] = SCREEN_HEIGHT / 2 - UNIT_SIZE * (snake.bodyparts % 2 == 0 ? 1 : 0);

        // hide buttons
        restartButton.setVisible(false);
        backToMenuButton.setVisible(false);

        apple = new Apple();
        playMusic();
        gameoverSound.stop();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        gameOverTrue = false;

    }

    // actionperformed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

        if (e.getSource() == restartButton) {
            restartGame();
        }

    }

    // key controls
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;

            }
        }
    }

}
