import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements ActionListener {

    private final JButton players2;
    private final JButton players3;
    private final JButton players4;
    private final JLabel gameIcon;
    private final MyFrame childPanel;
    private final JPanel buttonsPanel;

    public MenuPanel(MyFrame panel){
        this.setLayout(null);
        this.setBounds(0,0,1500, 800);

        childPanel = panel;

        gameIcon = new JLabel();
        gameIcon.setBounds(0,0, 1500, 640);
        gameIcon.setIcon(new ImageIcon("Resources/gameIcon.png"));

        players2 = new JButton();
        players2.setBounds(100,0,300, 160);
        players2.setFont(new Font("SansSerif", Font.BOLD, 48));
        players2.setText("2 Players");
        players2.addActionListener(this);

        players3 = new JButton();
        players3.setBounds(600,0,300, 160);
        players3.setFont(new Font("SansSerif", Font.BOLD, 48));
        players3.setText("3 Players");
        players3.addActionListener(this);

        players4 = new JButton();
        players4.setBounds(1100,0,300, 160);
        players4.setFont(new Font("SansSerif", Font.BOLD, 48));
        players4.setText("4 Players");
        players4.addActionListener(this);

        buttonsPanel = new JPanel();
        buttonsPanel.setBounds(0, 640, 1500, 160);
        buttonsPanel.setLayout(null);
        buttonsPanel.add(players2);
        buttonsPanel.add(players3);
        buttonsPanel.add(players4);

        this.add(gameIcon);
        this.add(buttonsPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == players2) {
            childPanel.startGame(2);
        } else if (e.getSource() == players3) {
            childPanel.startGame(3);
        } else if (e.getSource() == players4) {
            childPanel.startGame(4);
        }
    }
}