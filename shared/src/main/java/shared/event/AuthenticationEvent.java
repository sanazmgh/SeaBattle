package shared.event;

import shared.response.Response;


public class AuthenticationEvent extends Event {

    private final String username;
    private final String pass1;
    private final String pass2;
    private final boolean signingUp;

    public AuthenticationEvent (String username, String pass1, String pass2, boolean signingUp)
    {
        this.username = username;
        this.pass1 = pass1;
        this.pass2 = pass2;
        this.signingUp = signingUp;
    }

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return eventVisitor.Authentication(username, pass1, pass2, signingUp);
    }
}
