import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ControlsPanel extends JPanel {

    public ControlsPanel(int amount) {
        setSize(350, 520);
        setLayout(null);
        setOpaque(true);
        setBackground(Color.white);

        JPanel redControls = new JPanel();
        redControls.setBounds(0,0,350, 100);
        redControls.setLayout(null);
        redControls.setOpaque(true);
        redControls.setBackground(Color.white);

        JLabel redIcon = new JLabel();
        redIcon.setBounds(0,0,125, 72);
        redIcon.setIcon(new ImageIcon("Resources/winRedTank.png"));

        JLabel shootRed = new JLabel();
        shootRed.setBounds(150, 0, 50, 50);
        shootRed.setFont(new Font("SansSerif", Font.BOLD, 20));
        shootRed.setText("Q");
        shootRed.setOpaque(true);
        shootRed.setBackground(Color.RED);
        shootRed.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel upRed = new JLabel();
        upRed.setBounds(250, 0, 50, 50);
        upRed.setFont(new Font("SansSerif", Font.BOLD, 20));
        upRed.setText("W");
        upRed.setOpaque(true);
        upRed.setBackground(Color.RED);
        upRed.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel downRed = new JLabel();
        downRed.setBounds(250, 50, 50, 50);
        downRed.setFont(new Font("SansSerif", Font.BOLD, 20));
        downRed.setText("S");
        downRed.setOpaque(true);
        downRed.setBackground(Color.RED);
        downRed.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel leftRed = new JLabel();
        leftRed.setBounds(200, 50, 50, 50);
        leftRed.setFont(new Font("SansSerif", Font.BOLD, 20));
        leftRed.setText("A");
        leftRed.setOpaque(true);
        leftRed.setBackground(Color.RED);
        leftRed.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel rightRed = new JLabel();
        rightRed.setBounds(300, 50, 50, 50);
        rightRed.setFont(new Font("SansSerif", Font.BOLD, 20));
        rightRed.setText("D");
        rightRed.setOpaque(true);
        rightRed.setBackground(Color.RED);
        rightRed.setBorder(new LineBorder(Color.BLACK, 2));

        redControls.add(redIcon);
        redControls.add(shootRed);
        redControls.add(upRed);
        redControls.add(downRed);
        redControls.add(leftRed);
        redControls.add(rightRed);

        JPanel greenControls = new JPanel();
        greenControls.setBounds(0,140,350, 100);
        greenControls.setLayout(null);
        greenControls.setOpaque(true);
        greenControls.setBackground(Color.white);

        JLabel greenIcon = new JLabel();
        greenIcon.setBounds(0,0,125, 72);
        greenIcon.setIcon(new ImageIcon("Resources/winGreenTankRotated.png"));

        JLabel shootGreen = new JLabel();
        shootGreen.setBounds(150, 0, 50, 50);
        shootGreen.setFont(new Font("SansSerif", Font.BOLD, 20));
        shootGreen.setText("0");
        shootGreen.setOpaque(true);
        shootGreen.setBackground(Color.green);
        shootGreen.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel upGreen = new JLabel();
        upGreen.setBounds(250, 0, 50, 50);
        upGreen.setFont(new Font("SansSerif", Font.BOLD, 20));
        upGreen.setText("8");
        upGreen.setOpaque(true);
        upGreen.setBackground(Color.green);
        upGreen.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel downGreen = new JLabel();
        downGreen.setBounds(250, 50, 50, 50);
        downGreen.setFont(new Font("SansSerif", Font.BOLD, 20));
        downGreen.setText("5");
        downGreen.setOpaque(true);
        downGreen.setBackground(Color.green);
        downGreen.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel leftGreen = new JLabel();
        leftGreen.setBounds(200, 50, 50, 50);
        leftGreen.setFont(new Font("SansSerif", Font.BOLD, 20));
        leftGreen.setText("4");
        leftGreen.setOpaque(true);
        leftGreen.setBackground(Color.green);
        leftGreen.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel rightGreen = new JLabel();
        rightGreen.setBounds(300, 50, 50, 50);
        rightGreen.setFont(new Font("SansSerif", Font.BOLD, 20));
        rightGreen.setText("6");
        rightGreen.setOpaque(true);
        rightGreen.setBackground(Color.GREEN);
        rightGreen.setBorder(new LineBorder(Color.BLACK, 2));

        greenControls.add(greenIcon);
        greenControls.add(shootGreen);
        greenControls.add(upGreen);
        greenControls.add(downGreen);
        greenControls.add(leftGreen);
        greenControls.add(rightGreen);

        if (amount >= 3) {
            JPanel blueControls = new JPanel();
            blueControls.setBounds(0, 280, 350, 100);
            blueControls.setLayout(null);
            blueControls.setOpaque(true);
            blueControls.setBackground(Color.white);

            JLabel blueIcon = new JLabel();
            blueIcon.setBounds(0, 0, 125, 72);
            blueIcon.setIcon(new ImageIcon("Resources/winBlueTankRotated.png"));

            JLabel shootBlue = new JLabel();
            shootBlue.setBounds(150, 0, 50, 50);
            shootBlue.setFont(new Font("SansSerif", Font.BOLD, 20));
            shootBlue.setText("Ctrl");
            shootBlue.setOpaque(true);
            shootBlue.setBackground(Color.blue);
            shootBlue.setBorder(new LineBorder(Color.BLACK, 2));

            JLabel upBlue = new JLabel();
            upBlue.setBounds(250, 0, 50, 50);
            upBlue.setFont(new Font("SansSerif", Font.BOLD, 20));
            upBlue.setText("↑");
            upBlue.setOpaque(true);
            upBlue.setBackground(Color.blue);
            upBlue.setBorder(new LineBorder(Color.BLACK, 2));

            JLabel downBlue = new JLabel();
            downBlue.setBounds(250, 50, 50, 50);
            downBlue.setFont(new Font("SansSerif", Font.BOLD, 20));
            downBlue.setText("↓");
            downBlue.setOpaque(true);
            downBlue.setBackground(Color.blue);
            downBlue.setBorder(new LineBorder(Color.BLACK, 2));

            JLabel leftBlue = new JLabel();
            leftBlue.setBounds(200, 50, 50, 50);
            leftBlue.setFont(new Font("SansSerif", Font.BOLD, 20));
            leftBlue.setText("←");
            leftBlue.setOpaque(true);
            leftBlue.setBackground(Color.blue);
            leftBlue.setBorder(new LineBorder(Color.BLACK, 2));

            JLabel rightBlue = new JLabel();
            rightBlue.setBounds(300, 50, 50, 50);
            rightBlue.setFont(new Font("SansSerif", Font.BOLD, 20));
            rightBlue.setText("→");
            rightBlue.setOpaque(true);
            rightBlue.setBackground(Color.blue);
            rightBlue.setBorder(new LineBorder(Color.BLACK, 2));

            blueControls.add(blueIcon);
            blueControls.add(shootBlue);
            blueControls.add(upBlue);
            blueControls.add(downBlue);
            blueControls.add(leftBlue);
            blueControls.add(rightBlue);
            add(blueControls);
            if (amount == 4) {
                JPanel yellowControls = new JPanel();
                yellowControls.setBounds(0,420,350, 100);
                yellowControls.setLayout(null);
                yellowControls.setOpaque(true);
                yellowControls.setBackground(Color.white);

                JLabel yellowIcon = new JLabel();
                yellowIcon.setBounds(0,0,125, 72);
                yellowIcon.setIcon(new ImageIcon("Resources/winYellowTank.png"));

                JLabel shootYellow = new JLabel();
                shootYellow.setBounds(150, 0, 50, 50);
                shootYellow.setFont(new Font("SansSerif", Font.BOLD, 16));
                shootYellow.setText("Space");
                shootYellow.setOpaque(true);
                shootYellow.setBackground(Color.yellow);
                shootYellow.setBorder(new LineBorder(Color.BLACK, 2));

                JLabel upYellow = new JLabel();
                upYellow.setBounds(250, 0, 50, 50);
                upYellow.setFont(new Font("SansSerif", Font.BOLD, 20));
                upYellow.setText("I");
                upYellow.setOpaque(true);
                upYellow.setBackground(Color.yellow);
                upYellow.setBorder(new LineBorder(Color.BLACK, 2));

                JLabel downYellow = new JLabel();
                downYellow.setBounds(250, 50, 50, 50);
                downYellow.setFont(new Font("SansSerif", Font.BOLD, 20));
                downYellow.setText("K");
                downYellow.setOpaque(true);
                downYellow.setBackground(Color.yellow);
                downYellow.setBorder(new LineBorder(Color.BLACK, 2));

                JLabel leftYellow = new JLabel();
                leftYellow.setBounds(200, 50, 50, 50);
                leftYellow.setFont(new Font("SansSerif", Font.BOLD, 20));
                leftYellow.setText("J");
                leftYellow.setOpaque(true);
                leftYellow.setBackground(Color.yellow);
                leftYellow.setBorder(new LineBorder(Color.BLACK, 2));

                JLabel rightYellow = new JLabel();
                rightYellow.setBounds(300, 50, 50, 50);
                rightYellow.setFont(new Font("SansSerif", Font.BOLD, 20));
                rightYellow.setText("L");
                rightYellow.setOpaque(true);
                rightYellow.setBackground(Color.yellow);
                rightYellow.setBorder(new LineBorder(Color.BLACK, 2));

                yellowControls.add(yellowIcon);
                yellowControls.add(shootYellow);
                yellowControls.add(upYellow);
                yellowControls.add(downYellow);
                yellowControls.add(rightYellow);
                yellowControls.add(leftYellow);
                add(yellowControls);
            }
        }

        add(redControls);
        add(greenControls);
    }
}
