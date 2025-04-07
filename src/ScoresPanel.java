import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.*;
import javax.swing.*;

public class ScoresPanel extends JPanel {
    private JTextArea scoresTextArea;
    private JButton clearScoresButton;
    private JButton backToMenuButton;

    public ScoresPanel(MainApp app) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // textarea content
        scoresTextArea = new JTextArea();
        scoresTextArea.setEditable(false);
        scoresTextArea.setFont(new Font("Arial", Font.BOLD, 14));
        scoresTextArea.setForeground(Color.GREEN);
        scoresTextArea.setBackground(Color.BLACK);
        scoresTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // scrollpane
        JScrollPane scrollPane = new JScrollPane(scoresTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // clear scores button
        clearScoresButton = new JButton();
        clearScoresButton.setText("Clear Scores");
        clearScoresButton.setBackground(Color.GREEN);
        clearScoresButton.addActionListener(e -> {
            clearScoresFile();
            loadScores();
        });

        // back to menu button
        backToMenuButton = new JButton();
        backToMenuButton.addActionListener(e -> app.switchToMenuPanel());
        backToMenuButton.setText("Back to Menu");
        backToMenuButton.setBackground(Color.GREEN);

        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(clearScoresButton);
        buttonPanel.add(backToMenuButton);

        // add button panel to panel
        add(buttonPanel, BorderLayout.SOUTH);
        loadScores();
    }

    // sort scores
    public void sortScoresInDescendingOrder() {
        try {
            // reading lines
            List<String> sortedLines = Files.lines(Paths.get("scores.txt"))
                    .sorted((line1, line2) -> {
                        // read scores from lines
                        int score1 = Integer.parseInt(line1.split("Score:")[1].trim().split(" ")[0]);
                        int score2 = Integer.parseInt(line2.split("Score:")[1].trim().split(" ")[0]);
                        return Integer.compare(score2, score1);
                    })
                    .collect(Collectors.toList());

            // write in file
            Files.write(Paths.get("scores.txt"), sortedLines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load scores
    public void loadScores() {
        try {
            sortScoresInDescendingOrder();
            String content = Files.readString(Paths.get("scores.txt"));
            scoresTextArea.setText(content.isEmpty() ? "No scores available." : content);
        } catch (IOException e) {
            scoresTextArea.setText("Unable to load scores.");
            e.printStackTrace();
        }
    }

    // clear scores
    public void clearScoresFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("scores.txt"))) {
            writer.print("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to clear scores.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
