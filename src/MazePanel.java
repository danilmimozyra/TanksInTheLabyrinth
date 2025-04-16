import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MazePanel extends JPanel {

    private final Tile[][] mazeGrid;
    private final int side;
    private final int cellWidth;


    public MazePanel(int side, int width) {
        this.side = side;
        this.cellWidth = width;
        mazeGrid = new Tile[side][side];
        newMaze();

        this.setBounds(50, 50, width * side, width * side);
    }

    public void newMaze(){
        for (int y = 0; y < side; y++) {
            for (int x = 0; x < side; x++) {
                mazeGrid[y][x] = new Tile();
            }
        }
        setMapGrid(mazeGrid, 0, 0);
    }

    private void setMapGrid(Tile[][] maze, int x, int y) {
        maze[y][x].setVisited(true);

        ArrayList<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
        Collections.shuffle(directions);

        for (Direction dir : directions) {
            int nx = x + dir.dx;
            int ny = y + dir.dy;
            if (isInBounds(nx, ny) && !maze[ny][nx].isVisited()) {
                removeWall(maze[y][x], maze[ny][nx], dir);
                setMapGrid(maze, nx, ny);
            }
        }
    }

    //Canvas

    public void removeWall(Tile current, Tile next, Direction dir) {
        switch (dir) {
            case UP:
                current.setTopWall(false);
                next.setBottomWall(false);
                break;
            case RIGHT:
                current.setRightWall(false);
                next.setLeftWall(false);
                break;
            case DOWN:
                current.setBottomWall(false);
                next.setTopWall(false);
                break;
            case LEFT:
                current.setLeftWall(false);
                next.setRightWall(false);
                break;
        }
    }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < side && y >= 0 && y < side;
    }

    public void printMaze() {
        for (int y = 0; y < side; y++) {
            for (int x = 0; x < side; x++) {
                System.out.print(mazeGrid[y][x].isTopWall() ? "+---" : "+   ");
            }
            System.out.println("+");
            for (int x = 0; x < side; x++) {
                System.out.print(mazeGrid[y][x].isLeftWall() ? "|   " : "    ");
            }
            System.out.println("|");
        }
        for (int x = 0; x < side; x++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.GREEN);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        g2D.setPaint(Color.BLACK);
        int pixelWidth = 3;
        g2D.setStroke(new BasicStroke(pixelWidth));
        for (int y = 0; y < side; y++) {
            for (int x = 0; x < side; x++) {
                if (mazeGrid[y][x].isTopWall()) {
                    g2D.drawLine(x * cellWidth, y * cellWidth, cellWidth + x * cellWidth, y * cellWidth);
                }
                if (mazeGrid[y][x].isRightWall()) {
                    g2D.drawLine(cellWidth - pixelWidth + x * cellWidth, y * cellWidth, cellWidth - pixelWidth + x * cellWidth, cellWidth + y * cellWidth);
                }
                if (mazeGrid[y][x].isBottomWall()) {
                    g2D.drawLine(x * cellWidth, cellWidth - pixelWidth + y * cellWidth, cellWidth + x * cellWidth, cellWidth - pixelWidth + y * cellWidth);
                }
                if (mazeGrid[y][x].isLeftWall()) {
                    g2D.drawLine(x * cellWidth, y * cellWidth, x * cellWidth, cellWidth + y * cellWidth);
                }
            }
        }
    }
}