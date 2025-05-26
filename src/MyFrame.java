import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    private final JLabel whiteLabel;
    private final MenuPanel menuPanel;
    private GamePanel gamePanel;

    public MyFrame() {
        setTitle("Tanks Game");
        setSize(1500, 900);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        whiteLabel = new JLabel();
        whiteLabel.setBounds(0,0, getWidth(), getHeight());
        whiteLabel.setOpaque(true);
        whiteLabel.setBackground(Color.white);


        menuPanel = new MenuPanel(this);
        add(menuPanel);
        add(whiteLabel);
        menuPanel.setLocation((this.getWidth() - menuPanel.getWidth()) / 2, 0);
        setVisible(true);
    }

    public void startGame(int amountOfPlayers) {
        remove(menuPanel);
        gamePanel = new GamePanel(8, 86, 2, amountOfPlayers, this);
        add(gamePanel);
        add(whiteLabel);
        gamePanel.setLocation(((this.getWidth() - gamePanel.getWidth() + 350) / 2) - 350, 0);
        gamePanel.getMazePanel().start();
        revalidate();
        repaint();
    }

    public void backToMenu() {
        remove(gamePanel);
        add(menuPanel);
        add(whiteLabel);
        menuPanel.setLocation((this.getWidth() - menuPanel.getWidth()) / 2, 0);
        revalidate();
        repaint();
    }
}