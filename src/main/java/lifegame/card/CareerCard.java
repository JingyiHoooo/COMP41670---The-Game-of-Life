package lifegame.card;

import lifegame.player.Player;

public class CareerCard extends Card {

    private int salary;
    private int bonusNum;
    private int bonus;

    //constructor
    public CareerCard(String name, int salary, int bonusNum, int bonus) {
        super(name);
        this.salary = salary;
        this.bonusNum = bonusNum;
        this.bonus = bonus;
    }

    //get salary
    public int getSalary() {
        return salary;
    }

    //get bonus number
    public int getBonusNum() {
        return bonusNum;
    }

    //get bonus money when spun
    int getBonus() {
        return bonus;
    }

    //get bonus money when spun
    public void getBonus(Player bonusPlayer) {
        bonusPlayer.increaseBalance(bonus);
        System.out.println(String.format("Player <%s> has received %dk for their bonus number!", bonusPlayer.getPlayerName(), bonus));
    }

    @Override
    public String toString() {
        return "CareerCard{" +
                "name=" + getName() +
                ", salary=" + salary +
                ", bonusNum=" + bonusNum +
                ", bonus=" + bonus +
                '}';
    }
}
