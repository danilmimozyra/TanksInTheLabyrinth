
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Tank {

    public final static double tankWidth = 38;
    public final static double forthSpeed = 0.9;
    public final static double backSpeed = 0.7;
    private double x;
    private double y;
    private double angle;
    private final ImageIcon image;
    private boolean alive;
    private final List<Bullet> bullets;
    private int shotDelay;
    private Key key;
    private final TankType color;


    public Tank(String imagePath, TankType type, int forthKey, int backKey, int leftKey, int rightKey, int shootKey) {
        x = 0;
        y = 0;
        image = new ImageIcon(imagePath);
        alive = true;
        bullets = new ArrayList<>();
        color = type;
        key = new Key(forthKey, backKey, leftKey, rightKey, shootKey);
    }

    public void draw(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        g2d.translate(x, y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle), tankWidth / 2, tankWidth / 2);
        g2d.drawImage(image.getImage(), tran, null);

        g2d.setTransform(oldTransform);
    }

    public void changeLocation(double nx, double ny) {
        x = nx;
        y = ny;
    }

    public void goForth(Tile[][] mazeGrid, int cellSize, int wallThickness) {
        double nextX = x + Math.cos(Math.toRadians(angle)) * forthSpeed;
        double nextY = y + Math.sin(Math.toRadians(angle)) * forthSpeed;
        boolean goX = false;
        boolean goY = false;

        if (canMove(nextX, y, mazeGrid, cellSize, wallThickness)) {
            goX = true;
        }
        if (canMove(x, nextY, mazeGrid, cellSize, wallThickness)) {
            goY = true;
        }

        if (goX && goY) {
            x = nextX;
            y = nextY;
        } else if (goX) {
            x += (nextX - x) * 0.4;
        } else if (goY) {
            y += (nextY - y) * 0.4;
        }
    }

    public void goBack(Tile[][] mazeGrid, int cellSize, int wallThickness) {
        double nextX = x - Math.cos(Math.toRadians(angle)) * backSpeed;
        double nextY = y - Math.sin(Math.toRadians(angle)) * backSpeed;
        boolean goX = false;
        boolean goY = false;

        if (canMove(nextX, y, mazeGrid, cellSize, wallThickness)) {
            goX = true;
        }
        if (canMove(x, nextY, mazeGrid, cellSize, wallThickness)) {
            goY = true;
        }

        if (goX && goY) {
            x = nextX;
            y = nextY;
        } else if (goX) {
            x += (nextX - x) * 0.4;
        } else if (goY) {
            y += (nextY - y) * 0.4;
        }
    }

    public boolean canMove(double nextX, double nextY, Tile[][] mazeGrid, int cellSize, int wallThickness) {
        int step = 3;
        double checkX, checkY;

        for (int i = 0; i <= tankWidth; i += step) {
            checkX = nextX + i;
            checkY = nextY;
            if (isWallAt(checkX, checkY, mazeGrid, cellSize, wallThickness)) return false;
        }

        for (int i = 0; i <= tankWidth; i += step) {
            checkX = nextX + i;
            checkY = nextY + tankWidth;
            if (isWallAt(checkX, checkY, mazeGrid, cellSize, wallThickness)) return false;
        }

        for (int i = 0; i <= tankWidth; i += step) {
            checkX = nextX;
            checkY = nextY + i;
            if (isWallAt(checkX, checkY, mazeGrid, cellSize, wallThickness)) return false;
        }

        for (int i = 0; i <= tankWidth; i += step) {
            checkX = nextX + tankWidth;
            checkY = nextY + i;
            if (isWallAt(checkX, checkY, mazeGrid, cellSize, wallThickness)) return false;
        }

        return true;
    }

    private boolean isWallAt(double x, double y, Tile[][] mazeGrid, int cellSize, int wallThickness) {
        int cellX = (int) (x / cellSize);
        int cellY = (int) (y / cellSize);

        if (cellX < 0 || cellY < 0 || cellY >= mazeGrid.length || cellX >= mazeGrid[0].length)
            return true;

        Tile cell = mazeGrid[cellY][cellX];
        double localX = x % cellSize;
        double localY = y % cellSize;

        return (localX <= wallThickness && cell.isLeftWall()) ||
                (localX >= cellSize - wallThickness && cell.isRightWall()) ||
                (localY <= wallThickness && cell.isTopWall()) ||
                (localY >= cellSize - wallThickness && cell.isBottomWall());
    }

    public void changeAngle(double angle) {
        if (angle < 0) {
            angle = 359;
        } else if (angle > 359) {
            angle = 0;
        }
        this.angle = angle;
    }

    public void turnLeft() {
        changeAngle(angle - 1);
    }

    public void turnRight() {
        changeAngle(angle + 1);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getCenterX() {
        return x + tankWidth / 2;
    }

    public double getCenterY() {
        return y + tankWidth / 2;
    }

    public double getAngle() {
        return angle;
    }

    public double getRadius() {
        return tankWidth / 2;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void addBullet(int index, Bullet bullet) {
        bullets.add(index, bullet);
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    public int getBulletsSize() {
        return bullets.size();
    }

    public int getShotDelay() {
        return shotDelay;
    }

    public void increaseDelay() {
        shotDelay += 1;
    }

    ;

    public void setShotDelay(int shotDelay) {
        this.shotDelay = shotDelay;
    }

    public Key getKey() {
        return key;
    }

    public TankType getColor() {
        return color;
    }
}