package lifegame.board;

import java.util.List;

public class Space {

    private Event event;
    private List<Integer> nextSpaceIDs;

    public Space(Event event, List<Integer> nextSpaceIDs) {
        this.event = event;
        this.nextSpaceIDs = nextSpaceIDs;
    }

    public Event getEvent() {
        return event;
    }

    public List<Integer> getNextSpaceIDs() {
        return nextSpaceIDs;
    }
}
