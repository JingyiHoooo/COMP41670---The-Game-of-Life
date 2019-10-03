package lifegame.card;

import lifegame.player.Player;

public abstract class ActionCard extends Card {

    public ActionCard(String name) {
        super(name);
    }

    public abstract void doAction(Player player);
}

