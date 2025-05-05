import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tank {

    public final static double tankWidth = 44;
    public final static double tankLength = 52;
    public final static double forthSpeed = 1;
    public final static double backSpeed = 0.7;
    private double x;
    private double y;
    private double angle;
    private final ImageIcon image;


    public Tank (String imagePath) {
        x = 0;
        y = 0;
        image = new ImageIcon(imagePath);
    }

    public void draw(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        g2d.translate(x, y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle), tankLength / 2, tankWidth / 2);
        g2d.drawImage(image.getImage(), tran, null);

        g2d.setTransform(oldTransform);
    }

    public void goForth() {
        x += Math.cos(Math.toRadians(angle)) * forthSpeed;
        y += Math.sin(Math.toRadians(angle)) * forthSpeed;
    }

    public void goBack() {
        x -= Math.cos(Math.toRadians(angle)) * backSpeed;
        y -= Math.sin(Math.toRadians(angle)) * backSpeed;
    }

    public void changeLocation(double nx, double ny){
        x = nx;
        y = ny;
    }

    public void changeAngle(double angle){
        if (angle < 0) {
            angle = 359;
        } else if (angle > 359) {
            angle = 0;
        }
        this.angle = angle;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
