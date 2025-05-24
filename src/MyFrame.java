import javax.swing.*;

public class MyFrame extends JFrame {
    private final MenuPanel menuPanel;
    private GamePanel gamePanel;

    public MyFrame() {
        setTitle("Tanks Game");
        setSize(1500, 850);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        menuPanel = new MenuPanel(this);
        add(menuPanel);
        menuPanel.setLocation((this.getWidth() - menuPanel.getWidth()) / 2, (this.getHeight() - menuPanel.getHeight()) / 2);
        setVisible(true);
    }

    public void startGame(int amountOfPlayers) {
        remove(menuPanel);
        if (gamePanel != null) {
            remove(gamePanel);
        }

        gamePanel = new GamePanel(8, 86, 2, amountOfPlayers, this);
        add(gamePanel);
        gamePanel.setLocation((this.getWidth() - gamePanel.getWidth()) / 2, (this.getHeight() - gamePanel.getHeight()) / 2);
        gamePanel.getMazePanel().start();
        revalidate();
        repaint();
    }

    public void backToMenu() {
        if (gamePanel != null) {
            remove(gamePanel);
            gamePanel = null;
        }
        add(menuPanel);
        menuPanel.setLocation((this.getWidth() - menuPanel.getWidth()) / 2, (this.getHeight() - menuPanel.getHeight()) / 2);
        revalidate();
        repaint();
    }
}