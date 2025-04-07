import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class DifficultyPanel extends JPanel {

    public DifficultyPanel(MainApp app){
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // buttonPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,1,10,10));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setPreferredSize(new Dimension(200, 150));

        // buttons
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");

        // customize buttons
        customizeButton(easyButton, Color.GREEN);
        customizeButton(mediumButton,Color.GREEN);
        customizeButton(hardButton, Color.GREEN);


        // button actions
        easyButton.addActionListener(e -> {
            app.setDifficulty(1);
            app.switchToMenuPanel();
        });
        mediumButton.addActionListener(e -> {
            app.setDifficulty(2);
            app.switchToMenuPanel();
        });
        hardButton.addActionListener(e ->{
            app.setDifficulty(3);
            app.switchToMenuPanel();
        });

        // add buttons to buttonPanel
        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);

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


}
