import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Bullet {

    private double x;
    private double y;
    private final Shape shape;
    private final Color color = Color.BLACK;
    private final double angle;
    private double size;
    private double speed;

    public Bullet (double x, double y, double angle, double size, double speed){
        x += Tank.tankLength / 2 - (size / 2);
        y += Tank.tankWidth / 2 - (size / 2);
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.size = size;
        this.speed = speed;
        shape = new Ellipse2D.Double(0, 0, size, size);
    }

    public void update(){
        x += Math.cos(Math.toRadians(angle)) * speed;
        y += Math.sin(Math.toRadians(angle)) * speed;
    }

    public void draw(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        g2d.setColor(color);
        g2d.translate(x, y);
        g2d.fill(shape);
        g2d.setTransform(oldTransform);
    }

    public boolean check(int width, int height){
        return !(x <= -size) && !(y <= -size) && !(x > width) && !(y > height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getCenterX(){
        return x + size / 2;
    }

    public double getCenterY(){
        return y + size / 2;
    }
}
