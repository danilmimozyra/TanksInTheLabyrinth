package Code;

import Code.ButtonLabel;
import Code.ControlsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class is a panel which contains the maze, score and information about controls
 */
public class GamePanel extends JPanel implements MouseListener {

    private final MyFrame parentFrame;
    private final MazePanel mazePanel;
    private final JPanel controls;
    private final ControlsPanel controlsPanel;
    private final ButtonLabel controlsButton;
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
        setLayout(null);
        setSize(side * width + 350, side * width + 72);
        setOpaque(true);
        setBackground(Color.white);

        mazePanel = new MazePanel(side, width, pixelWidth, amount, this);

        controls = new JPanel();
        controls.setLayout(null);
        controls.setBounds(0,0,350,670);
        controls.setOpaque(true);
        controls.setBackground(Color.white);

        controlsPanel = new ControlsPanel(amount);
        controlsPanel.setLocation(0,150);

        controlsButton = new ButtonLabel();
        controlsButton.setText("Controls");
        controlsButton.setBounds(0, 0, 350, 150);
        controlsButton.addMouseListener(this);


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
        winsPanel.setBounds(350, this.getHeight() - 72, side * width, 72);
        winsPanel.setLayout(null);
        winsPanel.setOpaque(true);
        winsPanel.setBackground(Color.white);
        setWinsPanel();
        updateWins(null);

        controls.add(controlsButton);
        this.add(mazePanel);
        this.add(winsPanel);
        this.add(controls);

        this.setVisible(true);
    }

    /**
     * This method updates the panel which shows the score
     * @param tank is the winning tank. It's score should be increased
     */
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

    /**
     * This method is used to create the score panel determined by the amount of player
     */
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * This method controls if any buttons were pressed
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == controlsButton) {
            for (Component component : controls.getComponents()) {
                if (component instanceof JPanel) {
                    if (component == controlsPanel) {
                        controls.remove(controlsPanel);
                        controls.repaint();
                        return;
                    }
                }
            }
            controls.add(controlsPanel);
            controls.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}