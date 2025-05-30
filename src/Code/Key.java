package Code;

/**
 * This class is used to represent controls of each tank
 */
public class Key {
    private final int forthKey;
    private final int backKey;
    private final int leftKey;
    private final int rightKey;
    private final int shootKey;
    private boolean forth;
    private boolean back;
    private boolean right;
    private boolean left;
    private boolean shoot;

    public Key(int forthKey, int backKey, int leftKey, int rightKey, int shootKey) {
        this.forthKey = forthKey;
        this.backKey = backKey;
        this.rightKey = rightKey;
        this.leftKey = leftKey;
        this.shootKey = shootKey;
    }

    public boolean isForth() {
        return forth;
    }

    public void setForth(boolean forth) {
        this.forth = forth;
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isShoot() {
        return shoot;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public int getForthKey() {
        return forthKey;
    }

    public int getBackKey() {
        return backKey;
    }

    public int getRightKey() {
        return rightKey;
    }

    public int getLeftKey() {
        return leftKey;
    }

    public int getShootKey() {
        return shootKey;
    }
}