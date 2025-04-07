import java.awt.*;
import javax.swing.*;

public class MainApp {
    private JFrame frame;
    private CardLayout cardLayout;
    private String playerName;
    private int difficulty; // 1 easy 2 medium 3 hard
    private String difficultyText;
    private GamePanel gamePanel;
    private Menu menu;
    private ScoresPanel scoresPanel;
    private EnterNamePanel enterNamePanel;
    private DifficultyPanel difficultyPanel;

    public MainApp() {
        frame = new JFrame("Snake Game");
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setFavicon(frame);
        difficulty = 2;

        // cardLayout
        cardLayout = new CardLayout();
        frame.getContentPane().setLayout(cardLayout);

        // menu, gamepanel, enternamepanel, scorespanel
        menu = new Menu(this);
        enterNamePanel = new EnterNamePanel(this);
        gamePanel = new GamePanel(this);
        scoresPanel = new ScoresPanel(this);
        difficultyPanel = new DifficultyPanel(this);

        frame.getContentPane().add(menu, "Menu");
        frame.getContentPane().add(gamePanel, "Game");
        frame.getContentPane().add(enterNamePanel, "EnterName");
        frame.getContentPane().add(scoresPanel, "Scores");
        frame.getContentPane().add(difficultyPanel, "Difficulty");

        // pack
        frame.pack();

        // first view: menu
        cardLayout.show(frame.getContentPane(), "Menu");

        frame.setVisible(true);
    }

    // favicon
    private void setFavicon(JFrame frame) {
        ImageIcon icon = new ImageIcon("clips/favicon.png");
        frame.setIconImage(icon.getImage());
    }

    // view change to gamePanel
    public void switchToGamePanel() {
        if (gamePanel == null) {
            gamePanel = new GamePanel(this);
            frame.getContentPane().add(gamePanel, "Game");
        }

        cardLayout.show(frame.getContentPane(), "Game");
        GamePanel gamePanel = (GamePanel) frame.getContentPane().getComponent(1);
        gamePanel.startGame(this);
        gamePanel.requestFocusInWindow();
    }

    // view change to menuPanel
    public void switchToMenuPanel() {
        cardLayout.show(frame.getContentPane(), "Menu");
        GamePanel gamePanel = (GamePanel) frame.getContentPane().getComponent(1);
        menu.setDifficulty(this);
    }

    // view change to enterName
    public void switchToEnterNamePanel() {
        cardLayout.show(frame.getContentPane(), "EnterName");
    }

    // view change to scores
    public void switchToScoresPanel() {
        scoresPanel.loadScores();
        cardLayout.show(frame.getContentPane(), "Scores");

    }

    // view change to difficulty
    public void switchToDifficultyPanel() {
        cardLayout.show(frame.getContentPane(), "Difficulty");
    }

    // getters setters
    public static void main(String[] args) {
        new MainApp();
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getDifficultyText() {
        return difficultyText;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setDifficultyText(String difficultyText) {
        this.difficultyText = difficultyText;
    }
}
