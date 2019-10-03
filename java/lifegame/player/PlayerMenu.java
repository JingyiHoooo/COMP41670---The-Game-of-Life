package lifegame.player;

import lifegame.LifeGameApp;
import lifegame.board.Board;
import lifegame.card.ActionCard;
import lifegame.utils.Utils;

import java.util.List;
import java.util.Scanner;

//player menu gives option for player to choose
public enum PlayerMenu {
    BALANCE(1), CAREER(2), ACTION_CARD(3), HOUSE_CARD(4), KID(5), POSITION(6), CAR_PAWN(7), MOVE(8);

    private int index;

    PlayerMenu(int index) {
        this.index = index;
    }

    private static void doMenuAction(Board board, Player player, int index, boolean isMoved) {
        switch (index) {
            //gives player current Cash Balance
            case 1:
                System.out.println(Utils.split);
                System.out.println("[INFO] Player<" + player.getPlayerName() + "> balance=" + player.getBalance() + "K");
                break;

            //gives player chosen Career Info
            case 2:
                System.out.println(Utils.split);
                if (player.getCareer() == null)
                    System.out.println("[INFO] No Career");
                System.out.println("[INFO] Player<" + player.getPlayerName() + "> career=" + player.getCareer());
                break;

            //shows player's obtained Action Cards
            case 3:
                System.out.println(Utils.split);
                System.out.println("[INFO] Player<" + player.getPlayerName() + ">");
                showActionCard(player);
                break;

            //shows player's chosen House Info
            case 4:
                System.out.println(Utils.split);
                System.out.println("[INFO] Player<" + player.getPlayerName() + "> houseCard=" + player.getHouses());
                break;

            //shows player's number of Kids
            case 5:
                System.out.println(Utils.split);
                System.out.println("[INFO] Player<" + player.getPlayerName() + "> kid=" + player.getKidNum());
                break;

            //shows player's position on the board by space number
            case 6:
                System.out.println(Utils.split);
                System.out.println("[INFO] Player<" + player.getPlayerName() + "> position=" + player.getPosition());
                break;

            //shows player's Car Pawn colour and marriage status
            case 7:
                System.out.println(Utils.split);
                System.out.println("[INFO] Player<> carPawn:" + player.getCarPawn());
                break;

            //allows player to spin spinner and move on the board
            case 8:
                System.out.println(Utils.split);
                if (isMoved) { //if the player has already moved, give warning
                    System.out.println("[WARN] You have moved for this turn");
                } else { //otherwise allow player to move
                    player.move(board);
                }
                break;
        }
    }

    //gets list of players action cards
    private static void showActionCard(Player player) {
        List<ActionCard> actionCards = player.getActionCards();
        System.out.println("[INFO] ActionCards: ");
        for (int i = 0; i < actionCards.size(); i++) {
            System.out.println("(" + i + ") " + actionCards.get(i));
        }
    }

    //outputs player option menu
    public static void showMenu(Board board, Player player) {

        Scanner scanner = LifeGameApp.getScanner();
        boolean isMoved = false; //if player has not moved
        while (true) {
            System.out.println(Utils.split);
            System.out.println("[INFO] Player <" + player.getPlayerName() +"> Menu");
            PlayerMenu[] playerMenus = PlayerMenu.values();
            for (PlayerMenu playerMenu : playerMenus){
                System.out.println("(" + playerMenu.index + ") " + playerMenu);
            }
            System.out.println("(0) Exit");
            System.out.print("[INFO] Please enter your option: ");
            int i = Utils.sanitisedInput(0,1,2,3,4,5,6,7,8);
            if (i == 0) {
                if (isMoved) { //if player has moved
                    break; //exit menu
                } else { //if player has not moved yet output warning
                    System.out.println(Utils.split);
                    System.out.println("[WARN] You have not moved for this turn");
                }
            } else {
                doMenuAction(board, player, i, isMoved);
                if (PlayerMenu.MOVE == playerMenus[i-1]) {
                    isMoved = true;
                }
            }
            System.out.println(Utils.split);
        }
    }

}
