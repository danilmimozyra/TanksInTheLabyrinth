import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class MazePanel extends JPanel {

    private Graphics2D g2d;
    private BufferedImage image;
    private int width;
    private int height;
    private final Tile[][] mazeGrid;
    private final int side;
    private final int cellWidth;
    private final int wallWidth;
    private final boolean running = true;

    private final int FPS = 60;
    private final int loopTime = 1000000000 / FPS;
    private final Tank[] tanks;
    private final List<Bullet> allBullets;


    public MazePanel(int side, int width, int pixelWidth, int amount) {
        this.side = side;
        this.cellWidth = width;
        this.wallWidth = pixelWidth;

        mazeGrid = new Tile[side][side];
        tanks = new Tank[amount];
        allBullets = new ArrayList<>();

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
        Thread thread = new Thread(() -> {
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
        createObjects();
        setKeyBinds();
        startBulletThread();
        thread.start();
    }

    private void createObjects() {
        Tank redTank = new Tank("Resources/redTank.png", TankType.Red, 87, 83, 65, 68, 81);
        redTank.changeLocation(17, 21);
        tanks[0] = redTank;
        if (tanks.length >= 2){
            Tank greenTank = new Tank("Resources/greenTank.png", TankType.Green, 104, 101, 100, 102, 96);
            greenTank.changeLocation((side - 1) * cellWidth + 17, (side - 1) * cellWidth + 21);
            greenTank.changeAngle(180);
            tanks[1] = greenTank;
            if (tanks.length >= 3) {
                Tank blueTank = new Tank("Resources/blueTank.png", TankType.Blue, 38, 40, 37, 39, 17);
                blueTank.changeLocation(17, (side - 1) * cellWidth + 21);
                blueTank.changeAngle(270);
                tanks[2] = blueTank;
                if (tanks.length == 4) {
                    Tank yellowTank = new Tank("Resources/yellowTank.png", TankType.Yellow, 73, 75, 74, 76, 32);
                    yellowTank.changeLocation((side - 1) * cellWidth + 17, 21);
                    yellowTank.changeAngle(90);
                    tanks[3] = yellowTank;
                }
            }
        }
    }

    private void startBulletThread() {
        Thread bulletThread = new Thread(() -> {
            while (running) {
                for (Tank tank : tanks) {
                    List<Bullet> bullets = tank.getBullets();

                    synchronized (bullets) {
                        Iterator<Bullet> iter = bullets.iterator();
                        while (iter.hasNext()) {
                            Bullet bullet = iter.next();
                            bullet.update(mazeGrid, cellWidth, wallWidth);

                            if (!bullet.check()) {
                                iter.remove();
                            }
                        }
                    }
                }
                sleep(4);
            }
        });
        bulletThread.start();
    }

    private void setKeyBinds() {
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                for (Tank tank : tanks) {
                    Key key = tank.getKey();
                    if (code == key.getLeftKey()) {
                        key.setLeft(true);
                        break;
                    }
                    if (code == key.getRightKey()) {
                        key.setRight(true);
                        break;
                    }
                    if (code == key.getForthKey()) {
                        key.setForth(true);
                        break;
                    }
                    if (code == key.getBackKey()) {
                        key.setBack(true);
                        break;
                    }
                    if (code == key.getShootKey()) {
                        key.setShoot(true);
                        break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                for (Tank tank : tanks) {
                    Key key = tank.getKey();
                    if (code == key.getLeftKey()) {
                        key.setLeft(false);
                    }
                    if (code == key.getRightKey()) {
                        key.setRight(false);
                    }
                    if (code == key.getForthKey()) {
                        key.setForth(false);
                    }
                    if (code == key.getBackKey()) {
                        key.setBack(false);
                    }
                    if (code == key.getShootKey()) {
                        key.setShoot(false);
                    }
                    tank.setKey(key);
                }
            }
        });
        Thread keyThread = new Thread(() -> {
                while (running) {
                    for (Tank tank : tanks) {
                    if (tank.getKey().isLeft()) {
                        tank.turnLeft();
                    }
                    if (tank.getKey().isRight()) {
                        tank.turnRight();
                    }
                    if (tank.getKey().isForth()) {
                        tank.goForth(mazeGrid, cellWidth, wallWidth);
                    }
                    if (tank.getKey().isBack()) {
                        tank.goBack(mazeGrid, cellWidth, wallWidth);
                    }
                    if (tank.getKey().isShoot()) {
                        if (tank.isAlive() && tank.getShotDelay() == 0 && tank.getBulletsSize() < 7) {
                            tank.addBullet(0, new Bullet(tank.getX(), tank.getY(), tank.getAngle(), 7, 2, tank.getColor()));
                        }
                        tank.increaseDelay();
                        if (tank.getShotDelay() == 20) {
                            tank.setShotDelay(0);
                        }
                    } else {
                        tank.setShotDelay(0);
                    }
                }
                sleep(5);
            }
        });
        keyThread.start();
    }

    private void drawBackground() {
        g2d.setPaint(Color.CYAN);
        g2d.fillRect(0, 0, width, height);

        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(wallWidth));
        for (int y = 0; y < side; y++) {
            for (int x = 0; x < side; x++) {
                if (mazeGrid[y][x].isTopWall()) {
                    g2d.drawLine(x * cellWidth, y * cellWidth, cellWidth + x * cellWidth, y * cellWidth);
                }
                if (mazeGrid[y][x].isRightWall()) {
                    g2d.drawLine(cellWidth - wallWidth + x * cellWidth, y * cellWidth, cellWidth - wallWidth + x * cellWidth, cellWidth + y * cellWidth);
                }
                if (mazeGrid[y][x].isBottomWall()) {
                    g2d.drawLine(x * cellWidth, cellWidth - wallWidth + y * cellWidth, cellWidth + x * cellWidth, cellWidth - wallWidth + y * cellWidth);
                }
                if (mazeGrid[y][x].isLeftWall()) {
                    g2d.drawLine(x * cellWidth, y * cellWidth, x * cellWidth, cellWidth + y * cellWidth);
                }
            }
        }
    }

    private void drawGame() {
        allBullets.clear();
        for (Tank tank : tanks) {
            allBullets.addAll(tank.getBullets());
        }

        long now = System.currentTimeMillis();
        long iframeDuration = 80;
        for (Tank tank : tanks) {
            for (Bullet bullet : allBullets) {
                if (bullet != null && tank.isAlive()) {
                    if (isBulletHittingTank(bullet, tank)) {
                        boolean recentlyShot = (now - bullet.getCreationTime()) < iframeDuration;

                        if (!recentlyShot) {
                            tank.setAlive(false);
                            findOwner(bullet.getOwner()).removeBullet(bullet);
                        }
                    }
                }
            }
        }

        for (Bullet bullet : allBullets) {
            if (bullet != null) {
                bullet.draw(g2d);
            }
        }

        for (Tank tank : tanks) {
            if (tank.isAlive()) {
                tank.draw(g2d);
            }
        }
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

    public boolean isBulletHittingTank(Bullet bullet, Tank tank) {
        double dx = bullet.getCenterX() - tank.getCenterX();
        double dy = bullet.getCenterY() - tank.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        double bulletRadius = bullet.getSize() / 2.0;
        double tankRadius = tank.getRadius();

        return tank.isAlive() && distance < bulletRadius + tankRadius;
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

    public Tank findOwner(TankType type){
        for (Tank tank : tanks) {
            if (tank.getColor() == type) {
                return tank;
            }
        }
        return null;
    }
}