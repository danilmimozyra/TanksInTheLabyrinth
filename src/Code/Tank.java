package Code;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create a tank
 */
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
    private final Key key;
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

    /**
     * This method is used to draw a tank onto the image
     * @param g2d is a Graphics object which draws the tank onto the image
     */
    public void draw(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        g2d.translate(x, y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle), tankWidth / 2, tankWidth / 2);
        g2d.drawImage(image.getImage(), tran, null);

        g2d.setTransform(oldTransform);
    }

    /**
     * This method is used to set location of the tank
     * @param nx is the new x position
     * @param ny is the new y position
     */
    public void changeLocation(double nx, double ny) {
        x = nx;
        y = ny;
    }

    /**
     * This method is used to move forward
     * @param mazeGrid is the maze in which the tank moves
     * @param cellSize is the length of the single tile
     * @param wallThickness is the thickness of the wall
     */
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

    /**
     * This method is used to move backwards
     * @param mazeGrid is the maze in which the tank moves
     * @param cellSize is the length of the single tile
     * @param wallThickness is the thickness of the wall
     */
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

    /**
     * This method is used to determine if the tank can move to the new position
     * @param nextX is the new x position
     * @param nextY is the new y position
     * @param mazeGrid is the maze in which the tank moves
     * @param cellSize is the length of the single tile
     * @param wallThickness is the thickness of the wall
     * @return true if the tank can move to the next position
     */
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

    /**
     * This method checks if there is a wall at the new coordinates
     * @param x is the x position that is being checked
     * @param y is the y position that is being checked
     * @param mazeGrid is the maze in which the tank moves
     * @param cellSize is the length of the single tile
     * @param wallThickness is the thickness of the wall
     * @return true if there is a wall at the new coordinates
     */
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

    /**
     * This method is used to change tank's angle
     * @param angle is a new angle
     */
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