package lifegame.card;

import lifegame.LifeGameApp;
import lifegame.player.Player;
import lifegame.utils.Utils;

import java.util.List;
import java.util.Scanner;

//Action: Career Change Card
public class CareerChangeCard extends ActionCard {

    public CareerChangeCard() {
        super(CareerChangeCard.class.getSimpleName());
    }

    @Override
    public void doAction(Player player) {
        //if player has a career
        if (player.getCareer() != null) {
            //list of anything that extends CareerCard
            List<? extends CareerCard> careerCardList = player.getCareer() instanceof CollegeCareerCard ?
                    LifeGameApp.getCollegeCareerCardList() : LifeGameApp.getCareerCardList();

            //gets old career
            CareerCard oldCareer = player.getCareer();
            //chooses top 2 career cards
            System.out.println("[INFO] Change career\n(1) " + careerCardList.get(0) + "\n(2) " + careerCardList.get(1));
            Scanner scanner = LifeGameApp.getScanner();
            while (true) {
                System.out.println("[INFO] Please enter your option: ");
                int i = Utils.inputNumChoices(2);
                CareerCard newCareer = null;
                switch (i) {
                    case 1:
                        LifeGameApp.putCareerCardBack(oldCareer);
                        LifeGameApp.putCareerCardBack(careerCardList.remove(1));
                        newCareer = careerCardList.remove(0);
                        player.setCareer(newCareer);
                        System.out.println("[INFO] You changed your career to " + newCareer);
                        break;
                    case 2:
                        LifeGameApp.putCareerCardBack(oldCareer);
                        newCareer = careerCardList.remove(1);
                        player.setCareer(newCareer);
                        System.out.println("[INFO] You changed your career to " + newCareer);
                        LifeGameApp.putCareerCardBack(careerCardList.remove(0));
                        break;
                    default:
                        System.out.println("[ERROR] Option is illegal: " + i);
                        break;
                }
                if (newCareer != null)
                    break;
            }
        } else {
            System.out.println("[ERROR] You don't have a CareerCard or a CollegeCareerCard");
        }
    }

    @Override
    public String toString() {
        return "CareerChangeCard";
    }
}
