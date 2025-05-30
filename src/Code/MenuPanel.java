package Code;

import Code.ButtonLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This panel is used to create a menu
 */
public class MenuPanel extends JPanel implements MouseListener {

    private final JLabel players2;
    private final JLabel players3;
    private final JLabel players4;
    private final MyFrame childPanel;

    public MenuPanel(MyFrame panel){
        this.setLayout(null);
        this.setBounds(0,0,1500, 800);

        childPanel = panel;

        JLabel gameIcon = new JLabel();
        gameIcon.setBounds(0,0, 1500, 640);
        gameIcon.setIcon(new ImageIcon("Resources/gameIcon.png"));

        players2 = new ButtonLabel();
        players2.setBounds(100,0,300, 160);
        players2.setText("2 Players");
        players2.addMouseListener(this);

        players3 = new ButtonLabel();
        players3.setBounds(600,0,300, 160);
        players3.setText("3 Players");
        players3.addMouseListener(this);

        players4 = new ButtonLabel();
        players4.setBounds(1100,0,300, 160);
        players4.setText("4 Players");
        players4.addMouseListener(this);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(0, 640, 1500, 160);
        buttonsPanel.setLayout(null);
        buttonsPanel.setOpaque(true);
        buttonsPanel.setBackground(Color.white);
        buttonsPanel.add(players2);
        buttonsPanel.add(players3);
        buttonsPanel.add(players4);

        this.add(gameIcon);
        this.add(buttonsPanel);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * This method sets the amount of players
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == players2) {
            childPanel.startGame(2);
            players2.setFont(new Font("SansSerif", Font.BOLD, 38));
        } else if (e.getSource() == players3) {
            childPanel.startGame(3);
            players3.setFont(new Font("SansSerif", Font.BOLD, 38));
        } else if (e.getSource() == players4) {
            childPanel.startGame(4);
            players4.setFont(new Font("SansSerif", Font.BOLD, 38));
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