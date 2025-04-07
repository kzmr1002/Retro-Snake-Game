import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Menu extends JPanel {

    private JLabel difficultyLabel;

    public Menu(MainApp app) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // title
        JLabel titleLabel = new JLabel("Snake Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.green);
        add(titleLabel, BorderLayout.NORTH);

        // difficulty text
        difficultyLabel = new JLabel();
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        difficultyLabel.setForeground(Color.green);
        difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        setDifficulty(app);
        add(difficultyLabel, BorderLayout.SOUTH);

        // buttonPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setPreferredSize(new Dimension(200, 150));

        // buttons
        JButton playButton = new JButton("Play");
        JButton difficultyButton = new JButton("Difficulty");
        JButton scoresButton = new JButton("Scores");
        JButton quitButton = new JButton("Quit");

        // customize buttons
        customizeButton(playButton, Color.GREEN);
        customizeButton(difficultyButton, Color.GREEN);
        customizeButton(scoresButton, Color.GREEN);
        customizeButton(quitButton, Color.GREEN);

        // button actions
        playButton.addActionListener(e -> app.switchToEnterNamePanel());
        scoresButton.addActionListener(e -> app.switchToScoresPanel());
        quitButton.addActionListener(e -> System.exit(0));
        difficultyButton.addActionListener(e -> app.switchToDifficultyPanel());

        // add buttons to buttonPanel
        buttonPanel.add(playButton);
        buttonPanel.add(difficultyButton);
        buttonPanel.add(scoresButton);
        buttonPanel.add(quitButton);

        // panel for buttonPanel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.BLACK);
        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    // customize button function
    private void customizeButton(JButton button, Color textColor) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(textColor);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.GREEN));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void setDifficulty(MainApp app) {
        switch (app.getDifficulty()) {
            case 1:
                app.setDifficultyText("Easy");
                break;
            case 2:
                app.setDifficultyText("Medium");
                break;
            case 3:
                app.setDifficultyText("Hard");
                break;
            default:
                app.setDifficultyText("Medium");
        }

        difficultyLabel.setText("Difficulty: " + app.getDifficultyText());
    }

}
