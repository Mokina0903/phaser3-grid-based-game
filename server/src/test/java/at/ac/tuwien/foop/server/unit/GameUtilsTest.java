package at.ac.tuwien.foop.server.unit;

import at.ac.tuwien.foop.server.game.GameUtils;
import at.ac.tuwien.foop.server.game.Position;
import org.junit.Assert;
import org.junit.Test;

public class GameUtilsTest {

    @Test
    public void calculateDistanceCorrectly() {
        Position position1 = Position
                .builder()
                .xPosition(1)
                .yPosition(1)
                .build();

        Position position2 = Position
                .builder()
                .xPosition(2)
                .yPosition(5)
                .build();

        Position position3 = Position
                .builder()
                .xPosition(-8)
                .yPosition(10)
                .build();

        Position position4 = Position
                .builder()
                .xPosition(11)
                .yPosition(0)
                .build();

        // check order has no impact on result
        Assert.assertEquals(GameUtils.getTotalDistance(position2, position1), GameUtils.getTotalDistance(position1, position2));
        Assert.assertEquals(GameUtils.getTotalDistance(position3, position1), GameUtils.getTotalDistance(position1, position3));
        Assert.assertEquals(GameUtils.getTotalDistance(position4, position1), GameUtils.getTotalDistance(position1, position4));

        Assert.assertEquals(GameUtils.getTotalDistance(position3, position2), GameUtils.getTotalDistance(position2, position3));
        Assert.assertEquals(GameUtils.getTotalDistance(position4, position2), GameUtils.getTotalDistance(position2, position4));

        Assert.assertEquals(GameUtils.getTotalDistance(position4, position3), GameUtils.getTotalDistance(position3, position4));

        // check distances
        Assert.assertEquals(0, GameUtils.getTotalDistance(position1, position1));
        Assert.assertEquals(5, GameUtils.getTotalDistance(position1, position2));
        Assert.assertEquals(18, GameUtils.getTotalDistance(position1, position3));
        Assert.assertEquals(11, GameUtils.getTotalDistance(position1, position4));

        Assert.assertEquals(0, GameUtils.getTotalDistance(position2, position2));
        Assert.assertEquals(15, GameUtils.getTotalDistance(position2, position3));
        Assert.assertEquals(14, GameUtils.getTotalDistance(position2, position4));

        Assert.assertEquals(0, GameUtils.getTotalDistance(position3, position3));
        Assert.assertEquals(29, GameUtils.getTotalDistance(position3, position4));

        Assert.assertEquals(0, GameUtils.getTotalDistance(position4, position4));
    }
}
