package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Code.Direction;
import Code.MazePanel;
import Code.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class MazePanelTest {
    @Test
    void testIsInBounds() {
        MazePanel mazePanel = new MazePanel(8, 1, 1, 2, null);

        Assertions.assertTrue(mazePanel.isInBounds(0, 0));
        Assertions.assertTrue(mazePanel.isInBounds(7, 7));
        Assertions.assertFalse(mazePanel.isInBounds(8, 0));
        Assertions.assertFalse(mazePanel.isInBounds(0, 8));
        Assertions.assertFalse(mazePanel.isInBounds(-1, 0));
    }

    @Test
    void testRemoveWallRight() {
        Tile current = new Tile();
        Tile next = new Tile();
        MazePanel mazePanel = new MazePanel(8, 1, 1, 2, null);

        mazePanel.removeWall(current, next, Direction.RIGHT);

        Assertions.assertFalse(current.isRightWall());
        Assertions.assertFalse(next.isLeftWall());
    }
}