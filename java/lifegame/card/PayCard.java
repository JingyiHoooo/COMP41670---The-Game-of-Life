package lifegame.card;

import lifegame.LifeGameApp;
import lifegame.player.Player;
import lifegame.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Action: Player Pays Card
public class PayCard extends ActionCard {
    public PayCard() {
        super(PayCard.class.getSimpleName());
    }

    @Override
    public void doAction(Player player) {
        List<Player> playerList = LifeGameApp.getPlayerList();
        System.out.println("[INFO] PayPlayers: ");
        int count = 1;
        Map<Integer, Player> payerMap = new HashMap<>();
        for (Player payer : playerList) {
            if (!payer.equals(player)) {
                System.out.println("[INFO] (" + count + ") Player<" + payer.getPlayerName() + ">");
                payerMap.put(count, payer);
                count++;
            }
        }

        while (true) {
            if (payerMap.size() == 0) {
                System.out.println("[ERROR] No Other Player");
                break;
            }
            //chooses player to pay and then increase/decrease balance accordingly
            System.out.print("[INFO] Please enter your option: ");
            int i = Utils.inputNumChoices(payerMap.size());
            if (payerMap.containsKey(i)) {
                Player payer = payerMap.get(i);
                payer.decreaseBalance(20);
                player.increaseBalance(20);
                System.out.println("[INFO] Player<" + payer.getPlayerName() + "> pay 20K to Player<" + player.getPlayerName() + ">");
                break;
            }
            //when an invalid option is chosen
            System.out.println("[ERROR] Option is illegal: " + i);
        }
    }

    @Override
    public String toString() {
        return "PayCard";
    }
}
