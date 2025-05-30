package Code;

import javax.swing.*;

/**
 * This class is used to represent a singular cell in the maze
 */
public class Tile extends JLabel {
        private boolean visited;
        private boolean isTopWall;
        private boolean rightWall;
        private boolean bottomWall;
        private boolean leftWall;

    public Tile() {
        visited = false;
        isTopWall = true;
        rightWall = true;
        bottomWall = true;
        leftWall = true;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isTopWall() {
        return isTopWall;
    }

    public void setTopWall(boolean topWall) {
        this.isTopWall = topWall;
    }

    public boolean isRightWall() {
        return rightWall;
    }

    public void setRightWall(boolean rightWall) {
        this.rightWall = rightWall;
    }

    public boolean isBottomWall() {
        return bottomWall;
    }

    public void setBottomWall(boolean bottomWall) {
        this.bottomWall = bottomWall;
    }

    public boolean isLeftWall() {
        return leftWall;
    }

    public void setLeftWall(boolean leftWall) {
        this.leftWall = leftWall;
    }
}
