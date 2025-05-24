import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final MyFrame parentFrame;
    private final MazePanel mazePanel;
    private int redWins = 0;
    private final JLabel winRedTank;
    private int greenWins = 0;
    private final JLabel winGreenTank;
    private int blueWins = 0;
    private final JLabel winBlueTank;
    private int yellowWins = 0;
    private final JLabel winYellowTank;
    private final JPanel winsPanel;
    private final int amount;

    public GamePanel(int side, int width, int pixelWidth, int amount, MyFrame frame) {
        this.setLayout(null);
        this.setBounds(0, 0, side * width, side * width + 72);

        mazePanel = new MazePanel(side, width, pixelWidth, amount, this);
        parentFrame = frame;
        this.amount = amount;

        winRedTank = new JLabel();
        winRedTank.setIcon(new ImageIcon("Resources/winRedTank.png"));
        winRedTank.setSize(150, 72);
        winRedTank.setFont(new Font("SansSerif", Font.BOLD, 16));
        winRedTank.setHorizontalTextPosition(SwingConstants.RIGHT);

        winGreenTank = new JLabel();
        winGreenTank.setIcon(new ImageIcon("Resources/winGreenTank.png"));
        winGreenTank.setSize(150, 72);
        winGreenTank.setFont(new Font("SansSerif", Font.BOLD, 16));
        winGreenTank.setHorizontalTextPosition(SwingConstants.RIGHT);

        winBlueTank = new JLabel();
        winBlueTank.setIcon(new ImageIcon("Resources/winBlueTank.png"));
        winBlueTank.setSize(150, 72);
        winBlueTank.setFont(new Font("SansSerif", Font.BOLD, 16));
        winBlueTank.setHorizontalTextPosition(SwingConstants.RIGHT);

        winYellowTank = new JLabel();
        winYellowTank.setIcon(new ImageIcon("Resources/winYellowTank.png"));
        winYellowTank.setSize(150, 72);
        winYellowTank.setFont(new Font("SansSerif", Font.BOLD, 16));
        winYellowTank.setHorizontalTextPosition(SwingConstants.RIGHT);

        winsPanel = new JPanel();
        winsPanel.setBounds(0, this.getHeight() - 72, side * width, 72);
        winsPanel.setLayout(null);
        setWinsPanel();
        updateWins(null);

        this.add(mazePanel);
        this.add(winsPanel);

        this.setVisible(true);
    }

    public void updateWins(Tank tank) {
        if (tank != null) {
            switch (tank.getColor()) {
                case Red -> redWins++;
                case Green -> greenWins++;
                case Blue -> blueWins++;
                case Yellow -> yellowWins++;
            }
        }
        winRedTank.setText(String.valueOf(redWins));
        winGreenTank.setText(String.valueOf(greenWins));
        winBlueTank.setText(String.valueOf(blueWins));
        winYellowTank.setText(String.valueOf(yellowWins));
    }

    private void setWinsPanel() {
        winsPanel.removeAll();
        if (amount == 2) {
            winRedTank.setLocation(97, 0);
            winGreenTank.setLocation(441, 0);
            winsPanel.add(winRedTank);
            winsPanel.add(winGreenTank);
        } else if (amount == 3) {
            winRedTank.setLocation(39, 0);
            winGreenTank.setLocation(268, 0);
            winBlueTank.setLocation(497, 0);
            winsPanel.add(winRedTank);
            winsPanel.add(winGreenTank);
            winsPanel.add(winBlueTank);
        } else if (amount == 4) {
            winYellowTank.setLocation(11, 0);
            winRedTank.setLocation(183, 0);
            winGreenTank.setLocation(355, 0);
            winBlueTank.setLocation(527, 0);
            winsPanel.add(winRedTank);
            winsPanel.add(winGreenTank);
            winsPanel.add(winBlueTank);
            winsPanel.add(winYellowTank);
        }
    }

    public void backToMenu(){
        parentFrame.backToMenu();
    }

    public MazePanel getMazePanel() {
        return mazePanel;
    }
}