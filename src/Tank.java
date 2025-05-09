import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Tank {

    public final static double tankWidth = 44;
    public final static double tankLength = 44;
    public final static double forthSpeed = 1;
    public final static double backSpeed = 0.7;
    private double x;
    private double y;
    private double angle;
    private final ImageIcon image;
    private boolean alive;


    public Tank (String imagePath) {
        x = 0;
        y = 0;
        image = new ImageIcon(imagePath);
        alive = true;
    }

    public void draw(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        g2d.translate(x, y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle), tankLength / 2, tankWidth / 2);
        g2d.drawImage(image.getImage(), tran, null);

        g2d.setTransform(oldTransform);
    }

    public void changeLocation(double nx, double ny){
        x = nx;
        y = ny;
    }

    public void goForth(Tile[][] mazeGrid, int cellSize, int wallThickness) {
        double nextX = x + Math.cos(Math.toRadians(angle)) * forthSpeed;
        double nextY = y + Math.sin(Math.toRadians(angle)) * forthSpeed;

        if (canMove(nextX, nextY, mazeGrid, cellSize,wallThickness)
                && canMoveTo(nextX, nextY, mazeGrid, cellSize, wallThickness)) {
            x = nextX;
            y = nextY;
        }
    }

    public void goBack(Tile[][] mazeGrid, int cellSize, int wallThickness) {
        double nextX = x - Math.cos(Math.toRadians(angle)) * backSpeed;
        double nextY = y - Math.sin(Math.toRadians(angle)) * backSpeed;

        if (canMove(nextX, nextY, mazeGrid, cellSize, wallThickness)
                && canMoveTo(nextX, nextY, mazeGrid, cellSize, wallThickness)) {
            x = nextX;
            y = nextY;
        }
    }

    public boolean canMoveTo(double nextX, double nextY, Tile[][] mazeGrid, int cellSize, int wallThickness) {
        double[][] corners = {
                {nextX, nextY},
                {nextX + tankLength, nextY},
                {nextX, nextY + tankWidth},
                {nextX + tankLength, nextY + tankWidth}
        };

        for (double[] corner : corners) {
            int cellX = (int)(corner[0] / cellSize);
            int cellY = (int)(corner[1] / cellSize);

            if (cellX < 0 || cellY < 0 || cellY >= mazeGrid.length || cellX >= mazeGrid[0].length)
                return false;

            Tile cell = mazeGrid[cellY][cellX];
            double localX = corner[0] % cellSize;
            double localY = corner[1] % cellSize;

            if (localX <= wallThickness && cell.isLeftWall()) return false;
            if (localX >= cellSize - wallThickness && cell.isRightWall()) return false;
            if (localY <= wallThickness && cell.isTopWall()) return false;
            if (localY >= cellSize - wallThickness && cell.isBottomWall()) return false;
        }

        return true;
    }

    public boolean canMove(double nextX, double nextY, Tile[][] mazeGrid, int cellSize, int wallThickness) {
        int step = 3;
        double checkX, checkY;

        for (int i = 0; i <= tankLength; i += step) {
            checkX = nextX + i;
            checkY = nextY;
            if (isWallAt(checkX, checkY, mazeGrid, cellSize, wallThickness)) return false;
        }

        for (int i = 0; i <= tankLength; i += step) {
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
            checkX = nextX + tankLength;
            checkY = nextY + i;
            if (isWallAt(checkX, checkY, mazeGrid, cellSize, wallThickness)) return false;
        }

        return true;
    }

    private boolean isWallAt(double x, double y, Tile[][] mazeGrid, int cellSize, int wallThickness) {
        int cellX = (int)(x / cellSize);
        int cellY = (int)(y / cellSize);

        if (cellX < 0 || cellY < 0 || cellY >= mazeGrid.length || cellX >= mazeGrid[0].length)
            return true;

        Tile cell = mazeGrid[cellY][cellX];
        double localX = x % cellSize;
        double localY = y % cellSize;

        if (localX <= wallThickness && cell.isLeftWall()) return true;
        if (localX >= cellSize - wallThickness && cell.isRightWall()) return true;
        if (localY <= wallThickness && cell.isTopWall()) return true;
        if (localY >= cellSize - wallThickness && cell.isBottomWall()) return true;

        return false;
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

    public double getY() {
        return y;
    }

    public double getCenterX(){
        return x + tankLength / 2;
    }

    public double getCenterY(){
        return y + tankWidth / 2;
    }

    public double getAngle() {
        return angle;
    }

    public double getRadius(){
        return tankWidth / 2;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}