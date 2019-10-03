package lifegame.card;

//Action: House Card
public class HouseCard extends Card {

    private int purchasePrice;
    private int redPrice;
    private int blackPrice;

    //create house with prices
    public HouseCard(String name, int purchasePrice, int redPrice, int blackPrice) {
        super(name);
        this.purchasePrice = purchasePrice;
        this.redPrice = redPrice;
        this.blackPrice = blackPrice;
    }

    //get purchase price from file
    public int getPurchasePrice() {
        return purchasePrice;
    }

    //get red price from file
    public int getRedPrice() {
        return redPrice;
    }

    //get black price from file
    public int getBlackPrice() {
        return blackPrice;
    }

    @Override
    public String toString() {
        return "HouseCard{" +
                "name=" + getName() +
                ", purchasePrice=" + purchasePrice +
                ", redPrice=" + redPrice +
                ", blackPrice=" + blackPrice +
                '}';
    }
}
