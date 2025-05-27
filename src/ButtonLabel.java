import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class is a JLabel the user can interact with
 */
public class ButtonLabel extends JLabel implements MouseListener {

    public ButtonLabel(){
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setFont(new Font("SansSerif", Font.BOLD, 38));
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setFont(new Font("SansSerif", Font.BOLD, 48));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setFont(new Font("SansSerif", Font.BOLD, 38));
    }
}
