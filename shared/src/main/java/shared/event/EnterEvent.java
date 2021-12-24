package shared.event;

import shared.response.Response;

public class EnterEvent extends Event {
    String username;

    public EnterEvent(String username)
    {
        this.username = username;
    }


    @Override
    public Response visit(EventVisitor eventVisitor) {
        return eventVisitor.Enter(username);
    }
}
