package lifegame.card;

//Action: College Career Card
public class CollegeCareerCard extends CareerCard {
    public CollegeCareerCard(String name, int salary, int bonusNum, int bonus) {
        super(name, salary, bonusNum, bonus);
    }

    @Override
    public String toString() {
        return "CollegeCareerCard{" +
                "name=" + getName() +
                ", salary=" + getSalary() +
                ", bonusNum=" + getBonusNum() +
                ", bonus=" + getBonus() +
                '}';
    }
}
