package shared.event;

import shared.response.Response;

public class ClickOnBoardEvent extends Event {
    private final int x;
    private final int y;

    public ClickOnBoardEvent(int x, int y)
    {
        this.x = x;
        this.y = y;
    }


    @Override
    public Response visit(EventVisitor eventVisitor) {
        return eventVisitor.clickOnBoard(x, y, authToken);
    }
}
