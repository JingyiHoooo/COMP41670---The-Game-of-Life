package lifegame.card;

import lifegame.player.Player;

public class BankCard extends ActionCard {

    private int amount;

    //get amount for pay to bank
    public BankCard(int amount) {
        super(BankCard.class.getSimpleName());
        this.amount = amount;
    }

    //decrease balance of player
    @Override
    public void doAction(Player player) {
        player.decreaseBalance(amount);
        System.out.println("[INFO] Player<" + player.getPlayerName() + "> pay " + amount + "K to Bank");
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "amount=" + amount +
                '}';
    }
}
