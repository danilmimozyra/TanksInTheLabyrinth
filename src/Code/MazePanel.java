package Code;

import Code.Bullet;
import Code.Direction;
import Code.GamePanel;
import Code.Key;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * This panel is the main panel of the game. It contains the maze and the Tanks
 */
public class MazePanel extends JPanel {

    private final GamePanel parentPanel;
    private Graphics2D g2d;
    private BufferedImage image;
    private int width;
    private int height;
    private final Tile[][] mazeGrid;
    private final int side;
    private final int cellWidth;
    private final int wallWidth;
    private boolean running;

    private final int FPS = 60;
    private final int loopTime = 1000000000 / FPS;
    private final Tank[] tanks;
    private final List<Bullet> allBullets;

    private boolean roundOver = false;
    private long roundEndTime = 0;
    private static final long ROUND_DELAY = 2500;


    public MazePanel(int side, int width, int pixelWidth, int amount, GamePanel panel) {
        this.side = side;
        this.cellWidth = width;
        this.wallWidth = pixelWidth;
        parentPanel = panel;

        mazeGrid = new Tile[side][side];
        allBullets = new ArrayList<>();
        tanks = new Tank[amount];

        this.setBounds(350, 0, width * side, width * side);
        this.setOpaque(true);
        this.setLayout(null);
    }

    /**
     * This is the main method of this class which is rendering all objects
     */
    public void start() {
        newMaze();
        running = true;
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        createObjects();
        setKeyBinds();
        startBulletThread();
        Thread thread = new Thread(() -> {
            long startTime;
            long time;
            long sleep;
            while (running) {
                startTime = System.nanoTime();

                drawBackground();
                drawGame();
                render();
                checkRoundEnd();
                if (roundOver && System.currentTimeMillis() - roundEndTime >= ROUND_DELAY) {
                    roundOver = false;
                    running = false;
                    sleep(30);
                    start();
                    return;
                }

                time = System.nanoTime() - startTime;
                if (time < loopTime) {
                    sleep = (loopTime - time) / 1000000;
                    sleep(sleep);
                }
            }
        });
        thread.start();
    }

    /**
     * This method is used to initialise tanks based on the amount of players
     */
    private void createObjects() {
        Tank redTank = new Tank("Resources/redTank.png", TankType.Red, 87, 83, 65, 68, 81);
        redTank.changeLocation(17, 21);
        tanks[0] = redTank;
        if (tanks.length >= 2) {
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

    /**
     * This method starts and controls a thread responsible for processing bullets
     */
    private void startBulletThread() {
        Thread bulletThread = new Thread(() -> {
            while (running) {
                for (Tank tank : tanks) {
                    List<Bullet> bullets = tank.getBullets();

                    synchronized (bullets) {
                        List<Bullet> toRemove = new ArrayList<>();

                        for (Bullet bullet : new ArrayList<>(bullets)) {
                            bullet.update(mazeGrid, cellWidth, wallWidth);
                            if (!bullet.check()) {
                                toRemove.add(bullet);
                            }
                        }

                        bullets.removeAll(toRemove);
                    }
                }

                sleep(5);
            }
        });
        bulletThread.start();
    }

    /**
     * This method starts and controls a thread responsible for the controls of the tanks
     */
    private void setKeyBinds() {
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                for (Tank tank : tanks) {
                    if (tank.isAlive()) {
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
                if (code == 27) {
                    parentPanel.backToMenu();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                for (Tank tank : tanks) {
                    if (tank.isAlive()) {
                        Key key = tank.getKey();
                        if (code == key.getLeftKey()) {
                            key.setLeft(false);
                            break;
                        }
                        if (code == key.getRightKey()) {
                            key.setRight(false);
                            break;
                        }
                        if (code == key.getForthKey()) {
                            key.setForth(false);
                            break;
                        }
                        if (code == key.getBackKey()) {
                            key.setBack(false);
                            break;
                        }
                        if (code == key.getShootKey()) {
                            key.setShoot(false);
                            break;
                        }
                    }
                }
            }
        });
        Thread keyThread = new Thread(() -> {
            while (running) {
                for (Tank tank : tanks) {
                    if (tank.isAlive()) {
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
                }
                sleep(5);
            }
        });
        keyThread.start();
    }

    /**
     * This method draws the maze and sets background color
     */
    private void drawBackground() {
        g2d.setPaint(new Color(0xdddddd));
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

    /**
     * This method draws all tanks and their bullets
     */
    private void drawGame() {
        allBullets.clear();
        for (Tank tank : tanks) {
            allBullets.addAll(tank.getBullets());
        }

        long now = System.currentTimeMillis();
        long iframeDuration = 120;
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

    /**
     * This method is used for drawing single frames of the game
     */
    private void render() {
        Graphics g = getGraphics();
        if (g != null) {
            g.drawImage(image, 0, 0, null);
            g.dispose();
        }
    }

    /**
     * This method checks if the round has ended and determines the winner
     */
    private void checkRoundEnd() {
        List<Tank> alive = new ArrayList<>();
        for (Tank tank : tanks) {
            if (tank.isAlive()) {
                alive.add(tank);
            }
        }

        if (!roundOver && alive.size() <= 1 && tanks.length > 1) {
            Tank winner = alive.isEmpty() ? null : alive.get(0);

            if (parentPanel != null) {
                parentPanel.updateWins(winner);
            }

            roundOver = true;
            roundEndTime = System.currentTimeMillis();
        }
    }

    /**
     * This method is used to stop a thread for a period of time
     * @param time is the amount of time the thread should stop for
     */
    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to checking if a bullet has hit a tank
     * @param bullet is the bullet that is being checked
     * @param tank is the tank that is being checked
     * @return true if the bullet has hit a tank
     */
    public boolean isBulletHittingTank(Bullet bullet, Tank tank) {
        double dx = bullet.getCenterX() - tank.getCenterX();
        double dy = bullet.getCenterY() - tank.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        double bulletRadius = bullet.getSize() / 2.0;
        double tankRadius = tank.getRadius();

        return tank.isAlive() && distance < bulletRadius + tankRadius;
    }

    /**
     * This method is used to initialise maze
     */
    public void newMaze() {
        for (int y = 0; y < side; y++) {
            for (int x = 0; x < side; x++) {
                mazeGrid[y][x] = new Tile();
            }
        }
        setMapGrid(mazeGrid, 0, 0);
        breakRandomWalls(6);
    }

    /**
     * This is a recursive method for creating a maze
     * @param maze is a maze that should be created
     * @param x is an x position of a single tile in the maze
     * @param y is a y position of a single tile in the maze
     */
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

    /**
     * This method is used to make new paths in the maze
     * @param count is a number of walls that should be broken
     */
    private void breakRandomWalls(int count) {
        Random random = new Random();

        int broken = 0;
        while (broken < count) {
            int x = random.nextInt(side);
            int y = random.nextInt(side);

            List<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
            Collections.shuffle(directions);

            for (Direction dir : directions) {
                int nx = x + dir.dx;
                int ny = y + dir.dy;

                if (isInBounds(nx, ny)) {
                    Tile current = mazeGrid[y][x];
                    Tile neighbor = mazeGrid[ny][nx];

                    if (hasWallBetween(current, neighbor, dir)) {
                        removeWall(current, neighbor, dir);
                        broken++;
                        break;
                    }
                }
            }
        }
    }

    /**
     * This method checks if there is indeed a wall between two tiles that should be broken
     * @param current is a current tile
     * @param neighbor is an adjacent
     * @param dir is a direction in which the wall should be broken
     * @return true if there is a wall
     */
    private boolean hasWallBetween(Tile current, Tile neighbor, Direction dir) {
        return switch (dir) {
            case UP -> current.isTopWall() && neighbor.isBottomWall();
            case RIGHT -> current.isRightWall() && neighbor.isLeftWall();
            case DOWN -> current.isBottomWall() && neighbor.isTopWall();
            case LEFT -> current.isLeftWall() && neighbor.isRightWall();
        };
    }

    /**
     * This method is used to remove walls between tiles
     * @param current is a current tile
     * @param next is an adjacent
     * @param dir is a direction in which the wall should be broken
     */
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

    /**
     * This method checks if the coordinates of the tile is in the maze bounds
     * @param x is an x position of the tile
     * @param y is a y position of the tile
     * @return true if is in the bounds
     */
    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < side && y >= 0 && y < side;
    }

    public Tank findOwner(TankType type) {
        for (Tank tank : tanks) {
            if (tank.getColor() == type) {
                return tank;
            }
        }
        return null;
    }
}