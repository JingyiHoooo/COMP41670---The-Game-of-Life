package lifegame.carpawn;

import lifegame.LifeGameApp;
import lifegame.board.Spinner;
import lifegame.player.Player;
import lifegame.utils.Colour;
import lifegame.utils.Utils;

import java.util.List;

//will contain car pawn colour and marital status of player
public class CarPawn {

    private Colour colour;
    private boolean married;

    //sets colour to car pawn
    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    public boolean hasMarried() {
        return married;
    }

    //if player has been married, other players will gift according to spiin, odd = 100k otherwise 50k
    public void getMarried(Player player) {
        if (married) {
            System.out.println("Player<" + player.getPlayerName() + "> has been married!");
        } else {
            System.out.println(Utils.split);
            System.out.println("[INFO] Player<" + player.getPlayerName() + "> get married");
            List<Player> playerList = LifeGameApp.getPlayerList();

            //cycles through player list
            for (Player player1 : playerList) {
                if (!player.equals(player1)) {
                    int spin = Spinner.spin();
                    System.out.println("[INFO] Player<" + player1.getPlayerName() + "> spins -> " + spin);
                    int gift = spin % 2 == 0 ? 50 : 100;
                    player.increaseBalance(gift);
                    player1.decreaseBalance(gift);
                    System.out.println("[INFO] Player<" + player1.getPlayerName() + "> send " + gift + "K gift to Player<" + player.getPlayerName() + ">");
                }
            }
            System.out.println(Utils.split);
            //changes marital status to true
            this.married = true;
        }
    }

    @Override
    public String toString() {
        return "CarPawn{" +
                "colour=" + colour +
                ", married=" + married +
                '}';
    }
}