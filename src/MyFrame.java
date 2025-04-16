import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    private final MazePanel mazePanel;

    public MyFrame(){
        mazePanel = new MazePanel(9, 80);

        this.add(mazePanel);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Tanks in the Labyrinth");
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(900, 900);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setVisible(true);

    }
}