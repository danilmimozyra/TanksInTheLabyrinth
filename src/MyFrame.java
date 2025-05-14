import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyFrame extends JFrame{

    private final MazePanel mazePanel;
    private final JLabel wins;
    private int redWins = 0;
    private int greenWins = 0;
    private int blueWins = 0;
    private int yellowWins = 0;
    private int amount;

    public MyFrame(){
        amount = 2;

        mazePanel = new MazePanel(8, 86, 2, amount, this);

        wins = new JLabel();
        wins.setBounds(1000, 300, 100, 100);
        updateWins(null);

        this.add(mazePanel);
        this.add(wins);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Tanks in the Labyrinth");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                mazePanel.start();
            }
        });
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(1200, 900);
        this.setVisible(true);

    }

    public void updateWins(Tank tank) {
        if (tank != null) {
            switch (tank.getColor()){
                case Red -> redWins++;
                case Green -> greenWins++;
                case Blue -> blueWins++;
                case Yellow -> yellowWins++;
            }
        }
        if (amount >= 2) {
            StringBuilder stringBuilder;
            stringBuilder = new StringBuilder();
            stringBuilder.append("<html>");
            stringBuilder.append("Wins:");
            stringBuilder.append("<br>Red: ").append(redWins);
            stringBuilder.append("<br>Green: ").append(greenWins);
            if (amount >= 3) {
                stringBuilder.append("<br>Blue: ").append(blueWins);
                if (amount == 4) {
                    stringBuilder.append("<br>Yellow: ").append(yellowWins);
                }
            }
            stringBuilder.append("</html>");
            wins.setText(stringBuilder.toString());
        }
    }
}