package lifegame.card;

import lifegame.player.Player;

//Action: Bank Pays Card
public class GetCashCard extends ActionCard {

    private int amount;

    //get amount on card
    public GetCashCard(int amount) {
        super(GetCashCard.class.getSimpleName());
        this.amount = amount;
    }

    @Override //add amount to player balance
    public void doAction(Player player) {
        player.increaseBalance(amount);
        System.out.println("[INFO] Player<" + player.getPlayerName() + "> get " + amount + "K from Bank");
    }

    @Override
    public String toString() {
        return "GetCashCard{" +
                "amount=" + amount +
                '}';
    }
}
