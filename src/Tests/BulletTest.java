package Tests;

import Code.Bullet;
import Code.Tank;
import Code.TankType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BulletTest {

    @Test
    void testCheckReturnsTrueWhenRecent() {
        Bullet bullet = new Bullet(0, 0, 0, 5, 1, TankType.Red);
        assertTrue(bullet.check());
    }

    @Test
    void testGetOwner() {
        Bullet bullet = new Bullet(0, 0, 0, 5, 1, TankType.Yellow);
        assertEquals(TankType.Yellow, bullet.getOwner());
    }
}