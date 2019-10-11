package lifegame.board;

import lifegame.LifeGameApp;
import lifegame.card.CareerCard;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class SpinnerTest {
    @Before
    public void setup() {
        System.setIn(new ByteArrayInputStream("1 kT red\n".getBytes()));

        LifeGameApp.getInstance().init("config.properties");
    }

    @Test
    public void checkBonusNoCareer() {
        LifeGameApp.getPlayerList().get(0).setCareer(null);

        int balance = LifeGameApp.getPlayerList().get(0).getBalance();

        Spinner.checkBonus();

        assertEquals("Cash should not have changed", balance, LifeGameApp.getPlayerList().get(0).getBalance());
    }

    @Test
    public void checkBonusWithCareer() {
        int balance = LifeGameApp.getPlayerList().get(0).getBalance();
        int SPIN_BONUS = 30;

        LifeGameApp.getPlayerList().get(0).setCareer(new CareerCard("TestCareer", 30, 3, SPIN_BONUS));

        int spun = 0;
        while(spun != 3)
            spun = Spinner.spin();

        Spinner.checkBonus();

        assertEquals(String.format("Cash should be plus %dk", SPIN_BONUS), balance + SPIN_BONUS, LifeGameApp.getPlayerList().get(0).getBalance());
    }
}