package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Code.Tank;
import Code.TankType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class TankTest {

    @Test
    void testChangeAngleWrapAround() {
        Tank tank = new Tank("Resources/blueTank.png", TankType.Blue, 0, 0, 0, 0, 0);

        tank.changeAngle(-1);
        assertEquals(359, tank.getAngle(), 0.01);

        tank.changeAngle(360);
        assertEquals(0, tank.getAngle(), 0.01);

        tank.changeAngle(90);
        assertEquals(90, tank.getAngle(), 0.01);
    }
}