package lifegame.board;

import lifegame.LifeGameApp;
import lifegame.card.ActionCard;
import lifegame.card.CareerCard;
import lifegame.card.CollegeCareerCard;
import lifegame.card.HouseCard;
import lifegame.career.Family;
import lifegame.career.StartPath;
import lifegame.player.Player;
import lifegame.utils.Utils;

import java.util.*;

public enum Event {
    START,
    ACTION,
    PAYDAY,
    GRADUATION_STOP,
    HOLIDAY,
    SPIN2WIN,
    GET_MARRIED_STOP,
    HOUSE,
    NIGHT_SCHOOL_STOP,
    TWINS,
    FAMILY_STOP,
    BABY,
    BABY_STOP,
    HOLIDAY_STOP,
    GET200K,
    GET300K,
    PAY100K,
    RETIREMENT_STOP;

    //possible events
    public int doEvent(Player player, String option) {
        switch (this) {
            case START:
                return startEvent(player);
            case ACTION:
                return actionEvent(player);
            case PAYDAY:
                return paydayEvent(player, option);
            case GRADUATION_STOP:
                return graduationEvent(player);
            case HOLIDAY:
                return holidayEvent(player);
            case SPIN2WIN:
                return spin2winEvent(player);
            case GET_MARRIED_STOP:
                return getMarriedEvent(player);
            case HOUSE:
                return houseEvent(player);
            case NIGHT_SCHOOL_STOP:
                return nightSchoolEvent(player);
            case TWINS:
                return babyEvent(player, 2);
            case FAMILY_STOP:
                return familyEvent(player);
            case BABY:
                return babyEvent(player, 1);
            case BABY_STOP:
                return babySpinEvent(player);
            case HOLIDAY_STOP:
                return holidayEvent(player);
            case GET200K:
                return getBalanceEvent(player, 200);
            case GET300K:
                return getBalanceEvent(player, 300);
            case PAY100K:
                return lossBalanceEvent(player);
            case RETIREMENT_STOP:
                return retirementEvent(player);
            default:
                System.out.println("[ERROR] No Such Event");
                return -1;
        }

    }

    //player chooses start path; College or Career
    private int startEvent(Player player) {
        while (true) {
            System.out.println(Utils.split);
            System.out.println("[INFO] Start Event");
            System.out.println("(1) " + StartPath.CAREER_PATH + "\n(2) " + StartPath.COLLEGE_PATH);
            System.out.print("[INFO] Please choose your start path: ");
            int i = Utils.inputNumChoices(2);
            switch (i) {
                case 1: //Career - set career
                    player.setStartPath(StartPath.CAREER_PATH);
                    System.out.println("[INFO] Player<" + player.getPlayerName() + "> selected start path -> " + StartPath.CAREER_PATH);
                    player.setCareer(selectCareer());
                    return 0;
                case 2: //College - subtract tuition
                    player.setStartPath(StartPath.COLLEGE_PATH);
                    System.out.println("[INFO] Player<" + player.getPlayerName() + "> selected start path -> " + StartPath.COLLEGE_PATH);
                    System.out.println("[INFO] Pay tuition 100K");
                    player.decreaseBalance(100);
                    return 1;
                default:
                    System.out.println("[ERROR] option does not exists");
                    break;
            }
        }
    }

    //player chooses career, remove career from career list, add to player info
    private CareerCard selectCareer() {
        List<CareerCard> careerCardList = LifeGameApp.getCareerCardList();
        System.out.println(Utils.split);
        System.out.println("[INFO] Select career");
        System.out.println("(1)" + careerCardList.get(0).toString() + "\n(2)" + careerCardList.get(1).toString());
        CareerCard careerCard;
        while (true) {
            System.out.print("[INFO] Please choose your career:");
            int optionNum = Utils.inputNumChoices(2);
            switch (optionNum) {
                case 1:
                    careerCard = careerCardList.get(0);
                    careerCardList.add(careerCardList.remove(1));
                    careerCardList.remove(0);
                    System.out.println("[INFO] You have selected career: " + careerCard.toString());
                    System.out.println(Utils.split);
                    return careerCard;
                case 2:
                    careerCard = careerCardList.get(1);
                    careerCardList.remove(1);
                    careerCardList.add(careerCardList.remove(0));
                    System.out.println("[INFO] You have selected career: " + careerCard.toString());
                    System.out.println(Utils.split);
                    return careerCard;
                default:
                    System.out.println("[ERROR] option is illegal");
                    System.out.println(Utils.split);
                    break;
            }
        }
    }

    //carry out action from action card from top of deck
    private int actionEvent(Player player) {
        ActionCard actionCard = LifeGameApp.getActionCardList().remove(0);
        System.out.println(Utils.split);
        System.out.println("[INFO] You get an ActionCard:" + actionCard);
        player.addActionCard(actionCard);
        System.out.println("[INFO] Use action card: " + actionCard);
        actionCard.doAction(player);
        System.out.println(Utils.split);
        return 0;
    }

    //player balance increases based on career
    private int paydayEvent(Player player, String option) {
        if (player.getCareer() == null) return 0;

        boolean isStop = "stop".equalsIgnoreCase(option);
        int payment;
        if (isStop) {
            payment = 100;
            System.out.println(Utils.split);
            System.out.println("[INFO] PayDay for you, bonus 100K");
            System.out.println(Utils.split);
        } else {
            payment = player.getCareer().getSalary();
            System.out.println(Utils.split);
            System.out.println("[INFO] PayDay for you, salary: " + payment + "K");
            System.out.println(Utils.split);
        }
        player.increaseBalance(payment);
        return 0;
    }

    //player graduates, chooses college career
    private int graduationEvent(Player player) {
        System.out.println(Utils.split);
        System.out.println("[INFO] Congratulations on graduation！");
        player.setCareer(selectCollegeCareer());
        return 0;
    }

    //player picks college career between top 2 from deck
    private CollegeCareerCard selectCollegeCareer() {
        List<CollegeCareerCard> collegeCareerCardList = LifeGameApp.getCollegeCareerCardList();
        System.out.println(Utils.split);
        System.out.println("[INFO] Select career");
        System.out.println("(1)" + collegeCareerCardList.get(0).toString() + "\n(2)" + collegeCareerCardList.get(1).toString());
        CollegeCareerCard collegeCareerCard;
        while (true) {
            System.out.println("[INFO] Please choose your career:");
            int optionNum = Utils.inputNumChoices(2);
            switch (optionNum) {
                case 1:
                    collegeCareerCard = collegeCareerCardList.get(0);
                    collegeCareerCardList.add(collegeCareerCardList.remove(1));
                    collegeCareerCardList.remove(0);
                    System.out.println("[INFO] You have selected career: " + collegeCareerCard.toString());
                    System.out.println(Utils.split);
                    return collegeCareerCard;
                case 2:
                    collegeCareerCard = collegeCareerCardList.get(1);
                    collegeCareerCardList.remove(1);
                    collegeCareerCardList.add(collegeCareerCardList.remove(0));
                    System.out.println("[INFO] You have selected career: " + collegeCareerCard.toString());
                    System.out.println(Utils.split);
                    return collegeCareerCard;
                default:
                    System.out.println("[ERROR] option is illegal");
                    break;
            }
        }
    }

    //player stops
    private int holidayEvent(Player player) {
        System.out.println(Utils.split);
        System.out.println("[INFO] Player<" + player.getPlayerName() + "> is on holiday ...");
        System.out.println(Utils.split);
        return 0;
    }

    //spin2win, each player spins to win money
    private int spin2winEvent(Player player) {
        Map<Integer, Player> map = new HashMap<>();
        System.out.println(Utils.split);
        System.out.println("[INFO] Spin2Win! Player<" + player.getPlayerName() + "> please choose two different numbers for 1 to 10:");
        int count = 1;
        while (count <= 2) {
            System.out.print("[INFO] please choose number: ");
            int i = Utils.inputNumChoices(10);
            if (map.containsKey(i)) {
                System.out.println("[ERROR] You have already chosen the number (" + i + "), please choose again");
            } else if (i < 1 || i > 10) {
                System.out.println("[ERROR] number: " + i + " is less than 1 or greater than 10, please choose again");
            } else {
                map.put(i, player);
                count++;
            }
        }

        for (Player player1 : LifeGameApp.getPlayerList()) {
            if (!player1.equals(player)) {
                while (true) {
                    System.out.print("[INFO] Player <" + player1.getPlayerName() + ">please choose number from 1 to 10: ");
                    int i = Utils.inputNumChoices(10);
                    if (map.containsKey(i)) {
                        System.out.println("[ERROR] You have chosen the number (" + i + "). This has been chosen by another player, please choose again");
                        continue;
                    }
                    else if (i < 1 || i > 10) {
                        System.out.println("[ERROR] Number: " + i + " is less than 1 or greater than 10, please choose again");
                        continue;
                    }
                    System.out.println("[INFO] Player<" + player1.getPlayerName() + "> chose number: " + i);
                    map.put(i, player1);
                    break;
                }
            }
        }

        while (true) {
            int i = Spinner.spin();
            System.out.println(Utils.split);
            System.out.println("[INFO] Spin number: " + i);
            if (map.containsKey(i)) {
                Player player1 = map.get(i);
                System.out.println("[INFO] Player<" + player1.getPlayerName() + "> wins, gets a bonus of 200K");
                System.out.println(Utils.split);
                player1.increaseBalance(200);
                break;
            }
            System.out.println("[INFO] No one wins, spin again");
            System.out.println(Utils.split);
        }

        return 0;
    }

    //player gets married, sets marital status to true
    private int getMarriedEvent(Player player) {
        player.getCarPawn().getMarried(player);
        return 0;
    }

    //player choose between top 2 houses in deck
    private int houseEvent(Player player) {
        System.out.println(Utils.split);
        System.out.println("[INFO] houseEvent\n(1) Buy house\n(2) Sell house\n(3) Do Nothing");
        System.out.print("[INFO] enter your option: ");
        int i = Utils.inputNumChoices(3);
        switch (i) {
            case 1:
                System.out.println("[INFO] you choose to buy house");
                for (int j = 0; j < 2; j++) {
                    System.out.println("(" + (j + 1) + ") " + LifeGameApp.getHouseCardList().get(j));
                }
                System.out.print("[INFO] choose house to buy: ");
                int buyOpt = Utils.inputNumChoices(2);
                HouseCard buyCard = LifeGameApp.getHouseCardList().get(buyOpt - 1);
                boolean buyHouse = player.buyHouse(buyCard);
                if (buyHouse) {
                    System.out.println("[INFO] You get a new house: " + buyCard.toString());
                    System.out.println(Utils.split);
                    switch (buyOpt) {
                        case 1:
                            LifeGameApp.getHouseCardList().add(LifeGameApp.getHouseCardList().remove(1));
                            break;
                        case 2:
                            LifeGameApp.getHouseCardList().add(LifeGameApp.getHouseCardList().remove(0));
                            break;
                        default:
                            break;
                    }
                    LifeGameApp.getHouseCardList().remove(buyCard);
                }
                break;
            case 2:
                if(player.getHouses().size() > 0) {
                    System.out.println("[INFO] you choose to sell house");
                    for (int j = 0; j < player.getHouses().size(); j++) {
                        System.out.println("(" + (j + 1) + ") " + player.getHouses().get(j));
                    }
                    int saleOpt = Utils.inputNumChoices(player.getHouses().size());
                    HouseCard saleCard = player.getHouses().get(saleOpt);
                    player.saleHouse(saleCard, true);
                    System.out.println("[INFO] You sell house<" + saleCard.getName() + ">");
                    System.out.println(Utils.split);
                }
                else
                    System.out.println("[INFO] You have no house to sell.");
                break;
            default:
                break;
        }
        return 0;
    }

    //player stops, goes to night school and can change career to a college career
    private int nightSchoolEvent(Player player) {
        System.out.println(Utils.split);
        System.out.println("[INFO] Night School\n(1) Keep Current Career\n(2) Change Career");
        System.out.print("[INFO] please enter your option: ");
        int i = Utils.inputNumChoices(2);
        switch (i) {
            case 2:
                for (int j = 0; j < 2; j++) {
                    System.out.println("(" + (j + 1) + ") " + LifeGameApp.getCollegeCareerCardList().get(j));
                }
                System.out.print("[INFO] choose career to change: ");
                int opt = Utils.inputNumChoices(2);
                CollegeCareerCard collegeCareerCard = LifeGameApp.getCollegeCareerCardList().get(opt);

                if (player.getCareer() != null) {
                    if(player.getCareer() instanceof CollegeCareerCard)
                        LifeGameApp.getCollegeCareerCardList().add((CollegeCareerCard) player.getCareer());
                    else
                        LifeGameApp.getCareerCardList().add(player.getCareer());
                }

                player.setCareer(collegeCareerCard);

                System.out.println("[INFO] You changed your career to " + collegeCareerCard);
                System.out.println(Utils.split);
                return 1;
            default:
                break;
        }
        return 0;
    }

    //sets number of kids
    private int babyEvent(Player player, int babyNum) {
        if (player.getFamily() == null || player.getFamily().equals(Family.FAMILY_PATH)) {
            player.setKidNum(babyNum);
        }
        return 0;
    }

    //player stops, chooses path; family or life
    private int familyEvent(Player player) {
        System.out.println(Utils.split);
        System.out.println("[INFO] Please select family path: \n(1) LIFE_PATH\n(2) FAMILY_PATH");
        System.out.print("[INFO] Please enter your option: ");
        int i = Utils.inputNumChoices(2);
        switch (i) {
            case 1:
                System.out.println("[INFO] You selected Life Path");
                System.out.println(Utils.split);
                player.setFamily(Family.LIFE_PATH);
                break;
            case 2:
                System.out.println("[INFO] You selected Family Path");
                System.out.println(Utils.split);
                player.setFamily(Family.FAMILY_PATH);
                break;
            default:
                break;
        }
        return 0;
    }

    //player spins for number of kids
    private int babySpinEvent(Player player) {
        int spin = Spinner.spin();
        int babyNum = 0;
        if (spin >= 4 && spin <= 6) {
            babyNum = 1;
        } else if (spin >= 7 && spin <= 8) {
            babyNum = 2;
        } else if (spin >= 9 && spin <= 10) {
            babyNum = 3;
        }
        System.out.println(Utils.split);
        System.out.println("[INFO] Spin number: " + spin + ", you get " + babyNum + "babies");
        System.out.println(Utils.split);
        return babyEvent(player, babyNum);
    }

    //player earns money
    private int getBalanceEvent(Player player, int bonus) {
        System.out.println(Utils.split);
        System.out.println("[INFO] Player<" + player.getPlayerName() + "> gets bonus of " + bonus + "K");
        System.out.println(Utils.split);
        player.increaseBalance(bonus);
        return 0;
    }

    //player loses money
    private int lossBalanceEvent(Player player) {
        System.out.println(Utils.split);
        System.out.println("[INFO] Player<" + player.getPlayerName() + "> loss balance of " + (double) 100 + "K");
        System.out.println(Utils.split);
        player.decreaseBalance((double) 100);
        return 0;
    }

    //player retires, get balance of all assets
    private int retirementEvent(Player player) {
        System.out.println(Utils.split);
        int balance = player.getBalance();
        int loan = player.getLoan();
        List<HouseCard> houses = new ArrayList<>(player.getHouses());
        System.out.println("[INFO] Now your balance=" + balance + ", loan=" + loan + ", houses=" + houses);
        System.out.println("[INFO] Beginning to calculate your property");
        for (HouseCard house : houses) {
            player.saleHouse(house, false);
        }
        player.payLoan(loan);
        System.out.println("[INFO] All your property cast to balance and balance=" + player.getBalance());
        System.out.println(Utils.split);
        player.retire();
        return 0;
    }

    //just for test
    public static void main(String[] args) {
        Player player = new Player("charmyhoo");
        START.doEvent(player, "");
        System.out.println(player.getStartPath());
    }

}