package lifegame.carpawn;

import lifegame.LifeGameApp;
import lifegame.player.Player;
import lifegame.utils.Colour;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//allows player to choose a car pawn colour for play
public class CarPawnSelector {

    private static Map<Colour, Boolean> colourMap;

    static {
        colourMap = new HashMap<>();
        colourMap.put(Colour.RED, false);
        colourMap.put(Colour.BLUE, false);
        colourMap.put(Colour.GREEN, false);
        colourMap.put(Colour.YELLOW, false);
    }

    public static void selectColour(Player player) {

        while (true) {

            CarPawn carPawn = new CarPawn();    //creates new car pawn

            Colour colourSelected = null;   //colour not yet selected for car pawn

            //while colour is not selected yet
            while(colourSelected == null) {
                try {
                    System.out.print("[INFO] Please select your CarPawn colour -> [RED, BLUE, GREEN, YELLOW]: ");
                    colourSelected = Colour.valueOf(LifeGameApp.getScanner().next().toUpperCase());
                }
                catch (Exception ex) {
                    colourSelected = null;
                }
            }

            boolean hasSelected = colourMap.get(colourSelected);    //sets colour to car pawn

            if (hasSelected) {  //if colour selected by another player, output message
                System.out.println("[INFO] Colour: " + colourSelected.name() + " has been selected, please select again");
            } else {    //if valid colour selected, output message
                carPawn.setColour(colourSelected); //set car pawn colour to chosen colour
                colourMap.put(colourSelected, true); //colour has been set to chosen
                player.setCarPawn(carPawn); //set car pawn to player
                System.out.println("[INFO] Player<" + player.getPlayerName() + "> has selected Color: " + colourSelected.name());
                break;
            }

        }

    }

}
