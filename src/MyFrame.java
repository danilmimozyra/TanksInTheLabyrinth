import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyFrame extends JFrame{

    private final MazePanel mazePanel;

    public MyFrame(){
        mazePanel = new MazePanel(9, 86);

        this.add(mazePanel);

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
        this.setSize(900, 900);
        //this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setVisible(true);

    }

}