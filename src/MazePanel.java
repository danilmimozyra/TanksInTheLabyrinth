import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MazePanel extends JPanel {

    private Graphics2D g2d;
    private BufferedImage image;
    private int width;
    private int height;
    private final Tile[][] mazeGrid;
    private final int side;
    private final int cellWidth;
    private Thread thread;
    private boolean running = true;

    private final int FPS = 60;
    private final int loopTime = 1000000000 / FPS;
    private Tank tank;
    private Key key;
    private List<Bullet> bullets;
    private double shotDelay;


    public MazePanel(int side, int width) {
        this.side = side;
        this.cellWidth = width;

        mazeGrid = new Tile[side][side];

        this.setBounds(100, 20, width * side, width * side);
        this.setOpaque(true);
        this.setLayout(null);
    }

    public void start() {
        newMaze();
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        thread = new Thread(() -> {
            long startTime;
            long time;
            long sleep;
            while (running) {
                startTime = System.nanoTime();
                drawBackground();
                drawGame();
                render();
                time = System.nanoTime() - startTime;
                if (time < loopTime) {
                    sleep = (loopTime - time) / 1000000;
                    sleep(sleep);
                }
            }
        });
        drawObjects();
        setKeyBinds();
        drawBullets();
        thread.start();
    }

    private void drawObjects() {
        tank = new Tank("Resources/blueTank.png");
        tank.changeLocation(17, 21);
    }

    private void drawBullets(){
        bullets = new ArrayList<>();
        new Thread(() -> {
            while (running){
                for (int i = 0; i < bullets.size(); i++) {
                    Bullet bullet = bullets.get(i);
                    if (bullet != null) {
                        bullet.update();
                        if (!bullet.check(width, height)){
                            bullets.remove(bullet);
                        }
                    } else {
                        bullets.remove(null);
                    }
                }
                sleep(6);
            }
        }).start();
    }

    private void setKeyBinds() {
        key = new Key();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        key.setLeft(true);
                        break;
                    case KeyEvent.VK_D:
                        key.setRight(true);
                        break;
                    case KeyEvent.VK_W:
                        key.setForth(true);
                        break;
                    case KeyEvent.VK_S:
                        key.setBack(true);
                        break;
                    case KeyEvent.VK_Q:
                        key.setShoot(true);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        key.setLeft(false);
                        break;
                    case KeyEvent.VK_D:
                        key.setRight(false);
                        break;
                    case KeyEvent.VK_W:
                        key.setForth(false);
                        break;
                    case KeyEvent.VK_S:
                        key.setBack(false);
                        break;
                    case KeyEvent.VK_Q:
                        key.setShoot(false);
                        break;
                }
            }
        });
        new Thread(() -> {
            int turn = 1;
            double angle;
            while (running) {
                angle = tank.getAngle();
                if (key.isLeft()) {
                    angle -= turn;
                }
                if (key.isRight()) {
                    angle += turn;
                }
                if (key.isForth()) {
                    tank.goForth();
                }
                if (key.isBack()) {
                    tank.goBack();
                }
                if (key.isShoot()) {
                    if (shotDelay == 0){
                        bullets.add(0, new Bullet(tank.getX(), tank.getY(), tank.getAngle(), 7, 2));
                    }
                    shotDelay++;
                    if (shotDelay == 20) {
                        shotDelay = 0;
                    }
                } else {
                    shotDelay = 0;
                }
                tank.changeAngle(angle);
                sleep(5);
            }
        }).start();
    }

    private void drawBackground() {
        g2d.setPaint(Color.CYAN);
        g2d.fillRect(0, 0, width, height);
    }

    private void drawGame() {
        g2d.setPaint(Color.BLACK);
        int pixelWidth = 3;
        g2d.setStroke(new BasicStroke(pixelWidth));
        for (int y = 0; y < side; y++) {
            for (int x = 0; x < side; x++) {
                if (mazeGrid[y][x].isTopWall()) {
                    g2d.drawLine(x * cellWidth, y * cellWidth, cellWidth + x * cellWidth, y * cellWidth);
                }
                if (mazeGrid[y][x].isRightWall()) {
                    g2d.drawLine(cellWidth - pixelWidth + x * cellWidth, y * cellWidth, cellWidth - pixelWidth + x * cellWidth, cellWidth + y * cellWidth);
                }
                if (mazeGrid[y][x].isBottomWall()) {
                    g2d.drawLine(x * cellWidth, cellWidth - pixelWidth + y * cellWidth, cellWidth + x * cellWidth, cellWidth - pixelWidth + y * cellWidth);
                }
                if (mazeGrid[y][x].isLeftWall()) {
                    g2d.drawLine(x * cellWidth, y * cellWidth, x * cellWidth, cellWidth + y * cellWidth);
                }
            }
        }

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet != null) {
                bullet.draw(g2d);
            }
        }

        tank.draw(g2d);
    }

    private void render() {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }

    private void sleep(long speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void newMaze() {
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
}