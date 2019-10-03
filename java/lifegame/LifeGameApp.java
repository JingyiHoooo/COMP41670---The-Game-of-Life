//The Game of Life - COMP41670
//Date Submitted: 21/12/2018
//Students: Ciara Hogan, Jingyi Hu, Katie Wong

//main function to play the game

//make sure to add config.properties under "Edit Configurations...>Template>Applications>Program arguments
//make sure to set pom.xml as a Maven project

//Run 'LifeGameApp' with Coverage if run button is not initially highlighted
package lifegame;

import lifegame.board.Board;
import lifegame.card.ActionCard;
import lifegame.card.CareerCard;
import lifegame.card.CollegeCareerCard;
import lifegame.card.HouseCard;
import lifegame.carpawn.CarPawnSelector;
import lifegame.player.Player;
import lifegame.player.PlayerMenu;
import lifegame.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class LifeGameApp {

    private static LifeGameApp instance = null;

    private static int MAX_NUM_AREAS = 0;
    private static int MAX_NUM_PLAYER = 0;

    private static Scanner scanner = new Scanner(System.in);
    private static Board board = null;
    private static List<Player> playerList = null;

    private static List<ActionCard> actionCardList = null;
    private static List<HouseCard> houseCardList = null;
    private static List<CareerCard> careerCardList = null;
    private static List<CollegeCareerCard> collegeCareerCardList = null;

    private LifeGameApp(){}

    public static void main(String[] args) {

        LifeGameApp lifeGameApp = LifeGameApp.getInstance();
        lifeGameApp.init(args[0]);
        lifeGameApp.start();

    }

    //gets properties from file 'config.properties' in 'resources' folder
    public void init(String filePath) {
        try {
            Properties properties = Utils.readConfigProperties(filePath);
            String boardConf = properties.getProperty("board_file");
            MAX_NUM_AREAS = Integer.parseInt(properties.getProperty("MAX_NUM_AREAS"));
            MAX_NUM_PLAYER = Integer.parseInt(properties.getProperty("MAX_NUM_PLAYER"));

            initBoard(boardConf);
            initCards();
            initPlayers();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //initialises cards for use from card classes
    private void initCards() {
        try {
            actionCardList = Utils.initCardList(ActionCard.class);
            houseCardList = Utils.initCardList(HouseCard.class);
            careerCardList = Utils.initCardList(CareerCard.class);
            collegeCareerCardList = Utils.initCardList(CollegeCareerCard.class);
            System.out.println("[INFO] Card Lists initialized.");
        } catch (Exception e) {
            System.out.println("[ERROR] Initialisation of  Card Lists failed, exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(e.hashCode());
        }
    }

    //initialises board for use from config.properties
    private void initBoard(String boardConf) {
        if (board == null) {
            board = Board.getInstance(boardConf, MAX_NUM_AREAS);
            System.out.println("[INFO] Board initialized.");
        }
    }

    //initialises players by setting the number of players and their respective names
    private void initPlayers() {
        if (playerList == null) {
            playerList = new ArrayList<>();
            while (true) {
                System.out.print("[INFO] Please enter number of players: ");
                int p_num = Utils.inputNumChoices(MAX_NUM_PLAYER);
                if (p_num > MAX_NUM_PLAYER) {
                    System.out.println("[ERROR] Player number is greater than MAX_NUM_PLAYER = " + MAX_NUM_PLAYER);
                    continue;
                }
                for (int i = 0; i < p_num; i++) {
                    System.out.print("[INFO] Please enter Player(" + (i + 1) + ")'s name: ");
                    String playerName = scanner.next();
                    Player player = new Player(playerName);
                    CarPawnSelector.selectColour(player);
                    playerList.add(player);
                }
                System.out.println("[INFO] Player initialised.");
                break;
            }
        }
    }

    private void start() {

        ArrayList<Player> retiredPlayerList = new ArrayList<>();
        int turn_num = 1;
        while (playerList.size() != 0) {

            System.out.println(Utils.split);
            System.out.println("[INFO] Round < " + turn_num + " > begin.");

            //create new array list to avoid concurrent modification errors
            for (Player player : new ArrayList<>(playerList)) {

                if (player.isRetired()) {
                    retiredPlayerList.add(player);
                    playerList.remove(player);
                    continue;
                }

                String playerName = player.getPlayerName();
                System.out.println("[INFO] Turn of Player<" + playerName + ">.");

                if (turn_num == 1) {
                    board.checkSpace(player, true);
                }

                PlayerMenu.showMenu(board, player);

            }

            System.out.println("[INFO] Round < " + turn_num + " > end.");
            System.out.println(Utils.split);
            turn_num++;

        }

        //prints out name of winner from Retired Player List when game is over
        Player winner = getWinner(retiredPlayerList);
        System.out.println(Utils.split);
        System.out.println("[INFO] Winner is Player<" + winner.getPlayerName() + ">!");
        System.out.println(Utils.split);

    }

    //starts new instance of game
    public static LifeGameApp getInstance() {

        if (instance == null) {
            instance = new LifeGameApp();
        }

        return instance;
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static int getMaxNumAreas() {
        return MAX_NUM_AREAS;
    }

    //gets list of Players
    public static List<Player> getPlayerList() {
        return playerList;
    }

    //gets list of Action Cards
    public static List<ActionCard> getActionCardList() {
        return actionCardList;
    }

    //gets list of House Cards
    public static List<HouseCard> getHouseCardList() {
        return houseCardList;
    }

    //gets list of Career Cards
    public static List<CareerCard> getCareerCardList() {
        return careerCardList;
    }

    //gets list of College Career Cards
    public static List<CollegeCareerCard> getCollegeCareerCardList() {
        return collegeCareerCardList;
    }

    /**
     * Add career card to relevant list
     * @param card CareerCard or CollegeCareerCard
     */
    //puts old Career Card back in deck
    public static void putCareerCardBack(CareerCard card) {
        if(card instanceof CollegeCareerCard)
            getCollegeCareerCardList().add((CollegeCareerCard) card);
        else
            getCareerCardList().add(card);
    }

    //gets winner from player list
    private static Player getWinner(List<Player> list) {
        Player winner = null;
        for (Player player : list) {
            System.out.println(player);
            if (winner == null) {
                winner = player;
                continue;
            }
            winner = player.getBalance() > winner.getBalance() ? player : winner;
        }
        return winner;
    }
}