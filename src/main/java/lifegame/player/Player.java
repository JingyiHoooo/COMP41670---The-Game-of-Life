package lifegame.player;

import lifegame.LifeGameApp;
import lifegame.board.Board;
import lifegame.board.Spinner;
import lifegame.card.*;
import lifegame.career.College;
import lifegame.career.Family;
import lifegame.career.StartPath;
import lifegame.carpawn.CarPawn;
import lifegame.utils.Colour;

import java.util.ArrayList;
import java.util.List;

public class Player {

    //player variables at beginning of game
    private String playerName; //name will be string
    private int balance = 0; //balance = 0
    private int loan = 0; //no loans
    private CarPawn carPawn = null; //no car pawn
    private List<ActionCard> actionCards = new ArrayList<>(); //new array list to hold Action cards
    private StartPath StartPath = null; //no start path chosen
    private Family family = null; //no family
    private CareerCard career = null; //no career
    private College college = null; //no college career
    private int kidNum = 0; //no kids
    private List<HouseCard> houses = new ArrayList<>(); //new array list to hold House cards
    private int position = 0; //position of player
    private int nextPosition = 0; //next position of player
    private boolean isRetired = false; //not retired

    public Player(String playerName) {
        this.playerName = playerName;
        this.balance = 200; //starting balance of 200k
    }

    //gets Player Name
    public String getPlayerName() {
        return playerName;
    }

    //gets Player Balance
    public int getBalance() {
        return balance;
    }

    //adds to Balance
    public void increaseBalance(int increment) {
        this.balance += increment;
    }

    //subtracts from Balance
    public void decreaseBalance(double decrement) {
        this.balance -= decrement;
    }

    //gets Loan
    public int getLoan() {
        return loan;
    }

    //if balance is greater than 0, you cannot take out a loan
    public void loan(double loanNum) {
        if (this.balance == 0) {
            this.loan += loanNum * 50;
        } else {
            System.out.println("Your balance is greater than 0, you cannot get a loan");
        }
    }

    //pay back Loan
    public void payLoan(double payNum) {
        this.loan -= payNum * 50;
    }

    //gets car pawn information
    public CarPawn getCarPawn() {
        return carPawn;
    }

    //sets car pawn
    public void setCarPawn(CarPawn carPawn) {
        this.carPawn = carPawn;
    }

    //gets list of Action Cards
    public List<ActionCard> getActionCards() {
        return actionCards;
    }

    public void showCards() {
        StringBuilder sb = new StringBuilder("[INFO] Your cards: \n");
        int index = 1;
        for (ActionCard card : actionCards) {
            sb.append("[").append(index).append("] : ").append(card.getName()).append("\n");
            index++;
        }
        System.out.println(sb.toString());
    }

    //adds Action Card to array list
    public void addActionCard(ActionCard actionCard) {
        actionCards.add(actionCard);
    }

    //gets Start Path i.e. College or Career
    public StartPath getStartPath() {
        return StartPath;
    }

    //sets Start Path
    public void setStartPath(StartPath startPath) {
        StartPath = startPath;
    }

    //gets Family information
    public Family getFamily() {
        return family;
    }

    //sets Family information
    public void setFamily(Family family) {
        this.family = family;
    }

    //gets Career Card information
    public CareerCard getCareer() {
        return career;
    }

    //sets Career Card information
    public void setCareer(CareerCard career) {
        this.career = career;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    //gets number of Kids
    public int getKidNum() {
        return kidNum;
    }

    //sets number of Kids
    public void setKidNum(int kidNum) {
        this.kidNum = kidNum;
    }

    //adds House to array list of houses
    public List<HouseCard> getHouses() {
        return houses;
    }

    //gets retirement information
    public boolean isRetired() {
        return isRetired;
    }

    //sets retirement to true
    public void retire() {
        isRetired = true;
    }

    //buys house, subtract house price from balance, adds house to list
    public boolean buyHouse(HouseCard house) {
        double housePrice = house.getPurchasePrice();
        if (housePrice > balance) {
            System.out.println("You can't afford this!");
            return false;
        } else {
            balance -= housePrice;
            houses.add(house);
            return true;
        }
    }

    //sells house, spin for red or black, add red or black price accordingly to Player balance
    public void saleHouse(HouseCard house, boolean checkBonus) {
        System.out.println("[INFO] spin number is: " + Spinner.spin());
        int housePrice = Spinner.getSpinColour() == Colour.RED ? house.getRedPrice() : house.getBlackPrice();
        System.out.println("[INFO] sale price is: " + housePrice);
        houses.remove(house);
        LifeGameApp.getHouseCardList().add(house);
        increaseBalance(housePrice);
    }

    //gets player position number
    public int getPosition() {
        return position;
    }

    //sets next position for player to move to according to spin
    public void setNextPosition(int nextPosition) {
        this.nextPosition = nextPosition;
    }

    public void move(Board board) {
        while (true) {
            boolean isEndMove = false;
            int steps = Spinner.spin();
            System.out.println("[INFO] Spin steps: " + steps);

            //checks if player has a bonus number equal to the number spun
            Spinner.checkBonus();

            while (steps > 0) {
                position = nextPosition;
                int i = board.checkSpace(this, false);
                if (i == -1) {
                    break;
                } else if (i == 1) {
                    isEndMove = true;
                    break;
                }
                steps--;
            }
            if(isRetired) //if Player retires stop moving
                break;

            if (isEndMove || steps == 0) {
                if (steps == 0) {
                    board.checkSpace(this, true);
                }
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", balance=" + balance +
                '}';
    }
}