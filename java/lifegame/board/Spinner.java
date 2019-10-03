package lifegame.board;

import lifegame.LifeGameApp;
import lifegame.card.CareerCard;
import lifegame.player.Player;
import lifegame.utils.Colour;

import java.util.List;
import java.util.Random;

public class Spinner {

    private final static Random random = new Random();
    private static int spinNum = 0;

    //sets spinner limit to 10, prints random number from 1 to 10
    public static int spin() {
        spinNum = random.nextInt(9) + 1;
        System.out.println("[INFO] Spinner -> the number is: " + spinNum);

        return spinNum;
    }

    //checks the bonus number of a player
    public static void checkBonus() {
        List<Player> playerList = LifeGameApp.getPlayerList();
        for (Player p : playerList) {
            CareerCard career = p.getCareer();
            if (career != null) {
                if (career.getBonusNum() == spinNum) {
                    career.getBonus(p);
                }
            }
        }
    }

    public static Colour getSpinColour() {
        return spinNum % 2 == 0 ? Colour.BLACK : Colour.RED;
    }

    public static int getSpinNum() {
        return spinNum;
    }
}