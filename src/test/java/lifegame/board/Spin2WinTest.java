package lifegame.board;

import lifegame.LifeGameApp;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class Spin2WinTest {

    @Before
    public void setUp(){
        System.setIn(new ByteArrayInputStream("3 kT red Erikah blue Xtina yellow 3 1 6 8".getBytes()));

        LifeGameApp.getInstance().init("config.properties");
    }

    public void tryWin() {
        int kBal = LifeGameApp.getPlayerList().get(0).getBalance();
        int eBal = LifeGameApp.getPlayerList().get(1).getBalance();
        int xBal = LifeGameApp.getPlayerList().get(2).getBalance();

        Event.SPIN2WIN.doEvent(LifeGameApp.getPlayerList().get(0), null);

        switch(Spinner.getSpinNum()) {
            case 3:
            case 1:
                assertEquals("kT Should have won 200k", kBal + 200, LifeGameApp.getPlayerList().get(0).getBalance());
                assertEquals("Erikah should not have won", eBal, LifeGameApp.getPlayerList().get(1).getBalance());
                assertEquals("Xtina should not have won", xBal, LifeGameApp.getPlayerList().get(2).getBalance());
                break;
            case 6:
                assertEquals("Erikah should have won 200k", eBal + 200, LifeGameApp.getPlayerList().get(1).getBalance());
                assertEquals("kT should not have won", kBal, LifeGameApp.getPlayerList().get(0).getBalance());
                assertEquals("Xtina should not have won", xBal, LifeGameApp.getPlayerList().get(2).getBalance());
                break;
            case 8:
                assertEquals("Xtina should have won 200k", xBal + 200, LifeGameApp.getPlayerList().get(2).getBalance());
                assertEquals("Erikah should not have won", eBal, LifeGameApp.getPlayerList().get(1).getBalance());
                assertEquals("kT should not have won", kBal, LifeGameApp.getPlayerList().get(0).getBalance());
        }
    }

    @Test
    public void TestWin1() {
        tryWin();
    }
}