import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class EnterNamePanel extends JPanel {

    public EnterNamePanel(MainApp app) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // formpanel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 1, 10, 10));
        formPanel.setBackground(Color.BLACK);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setPreferredSize(new Dimension(300, 100));

        // enter name label
        JLabel enterNameLabel = new JLabel("Enter your name:");
        enterNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        enterNameLabel.setForeground(Color.GREEN);
        enterNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(enterNameLabel);

        // name textfield
        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 18));
        formPanel.add(nameField);

        // startbutton
        JButton startGameButton = new JButton("Start Game");
        customizeButton(startGameButton, Color.GREEN);
        startGameButton.addActionListener(e -> {
            String playerName = nameField.getText().trim();
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                app.setPlayerName(playerName);
                app.switchToGamePanel();
            }
        });
        formPanel.add(startGameButton);

        // center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.BLACK);
        centerPanel.add(formPanel);
        add(centerPanel, BorderLayout.CENTER);


    }

    // customizebutton
    private void customizeButton(JButton button, Color textColor) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(textColor);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.GREEN));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
