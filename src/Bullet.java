import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Bullet {

    private double x;
    private double y;
    private double dx;
    private double dy;
    private final long creationTime;
    private final Shape shape;
    private final Color color = Color.BLACK;
    private final double size;
    private final TankType owner;

    public Bullet (double x, double y, double angle, double size, double speed, TankType type){
        x += Tank.tankLength / 2 - (size / 2);
        y += Tank.tankWidth / 2 - (size / 2);
        owner = type;
        this.x = x;
        this.y = y;
        this.size = size;
        this.shape = new Ellipse2D.Double(0, 0, size, size);
        this.dx = Math.cos(Math.toRadians(angle)) * speed;
        this.dy = Math.sin(Math.toRadians(angle)) * speed;
        this.creationTime = System.currentTimeMillis();
    }

    public void update(Tile[][] mazeGrid, int cellSize, int wallThickness) {
        double nextX = x + dx;
        double nextY = y + dy;

        int cellX = (int)((nextX + size / 2) / cellSize);
        int cellY = (int)((nextY + size / 2) / cellSize);

        if (cellX < 0 || cellY < 0 || cellY >= mazeGrid.length || cellX >= mazeGrid[0].length) {
            return;
        }

        Tile cell = mazeGrid[cellY][cellX];
        double localX = (nextX + size / 2) % cellSize;
        double localY = (nextY + size / 2) % cellSize;

        if ((localX <= wallThickness && cell.isLeftWall()) ||
                (localX >= cellSize - wallThickness && cell.isRightWall())) {
            dx = -dx;
        }

        if ((localY <= wallThickness && cell.isTopWall()) ||
                (localY >= cellSize - wallThickness && cell.isBottomWall())) {
            dy = -dy;
        }

        x += dx;
        y += dy;
    }

    public void draw(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        g2d.setColor(color);
        g2d.translate(x, y);
        g2d.fill(shape);
        g2d.setTransform(oldTransform);
    }

    public boolean check() {
        return (System.currentTimeMillis() - creationTime) < 10_000;
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

    public long getCreationTime() {
        return creationTime;
    }

    public double getSize() {
        return size;
    }

    public TankType getOwner() {
        return owner;
    }
}