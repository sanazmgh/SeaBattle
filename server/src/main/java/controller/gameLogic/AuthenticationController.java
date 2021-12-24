package controller.gameLogic;

import db.UsersDB;
import shared.model.User;

public class AuthenticationController {

    public static int validAuthentication(String username, String pass1, String pass2, boolean signingUp)
    {
        User currentUser = UsersDB.get(username);

        if(!signingUp)
        {
            if(currentUser == null)
                return 1;

            else if(!currentUser.getPass().equals(pass1))
                return 1;

            else
                return 0;
        }

        else
        {
            if(currentUser != null)
                return 1;

            if(!pass1.equals(pass2))
                return 2;

            return 0;
        }
    }
}
