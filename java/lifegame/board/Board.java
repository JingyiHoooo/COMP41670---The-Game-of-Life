package lifegame.board;

import lifegame.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

//creates board
public class Board {

    private static Board board = null;
    private static ArrayList<Space> spaces;

    //gets board info from board files
    private Board(String boardFile, int areasMaxNum) {
        spaces = new ArrayList<>();
        initBoard(boardFile, areasMaxNum);
    }

    //initialises board
    public static void initBoard(String boardFile, int areasMaxNum) {

        try {

            InputStream boardFileInputStream = Board.class.getClassLoader().getResourceAsStream(boardFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(boardFileInputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                while (tokenizer.hasMoreTokens()) {
                    int spaceID = Integer.parseInt(tokenizer.nextToken());
                    Event event = Event.valueOf(tokenizer.nextToken());
                    List<Integer> nextSpaceIDs = new ArrayList<>();
                    if (tokenizer.hasMoreTokens()) {
                        String nextSpaceStr = tokenizer.nextToken();
                        Arrays.stream(nextSpaceStr.split(",")).forEach(spaceIDStr -> nextSpaceIDs.add(Integer.valueOf(spaceIDStr)));
                    }
                    spaces.add(spaceID, new Space(event, nextSpaceIDs));
                }
            }

            if (spaces.size() > areasMaxNum) {
                throw new IllegalArgumentException("areas max num is: " + areasMaxNum + ", but now size of areas is: " + spaces.size());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Board getInstance(String boardFile, int areasMaxNum) {
        if (board == null) {
            board = new Board(boardFile, areasMaxNum);
        }
        return board;
    }

    public ArrayList<Space> getSpaces() {
        return spaces;
    }

    public int checkSpace(Player player, boolean isStop) {
        int position = player.getPosition();
        Space space = spaces.get(position);
        Event event = space.getEvent();
        String eventName = event.name();

        int nextIdx;
        if (isStop) {
            nextIdx = event.doEvent(player, "stop");
            player.setNextPosition(space.getNextSpaceIDs().get(nextIdx));
            return 0;
        } else {
            if (eventName.equals("PAYDAY")) {
                nextIdx = event.doEvent(player, "pass");
                player.setNextPosition(space.getNextSpaceIDs().get(nextIdx));
                return 0;
            } else if (eventName.contains("STOP")) {
                System.out.println("[INFO] You will stop at POSITION(" + position + ") with Event[" + eventName + "]");
                if (event == Event.GET_MARRIED_STOP || event == Event.NIGHT_SCHOOL_STOP || event == Event.RETIREMENT_STOP) {
                    nextIdx = event.doEvent(player, "pass");
                    player.setNextPosition(space.getNextSpaceIDs().get(nextIdx));
                    return -1;
                } else {
                    nextIdx = event.doEvent(player, "pass");
                    player.setNextPosition(space.getNextSpaceIDs().get(nextIdx));
                    return 1;
                }
            } else {
                player.setNextPosition(space.getNextSpaceIDs().get(0));
                return 0;
            }
        }

    }

}
