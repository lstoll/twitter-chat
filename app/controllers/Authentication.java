package controllers;

import models.User;
import play.mvc.*;
import play.libs.OpenID;
import play.libs.OpenID.*;

public class Authentication extends Controller {
    public static void login() {
        render();
    }

    public static void logout() {
        session.clear();
        redirect("/");
    }
    
    public static void authenticate(String user) {
        if(OpenID.isAuthenticationResponse()) {
            UserInfo verifiedUser = OpenID.getVerifiedID();
            if(verifiedUser == null) {
                flash.put("error", "Oops. Authentication has failed");
                login();
            } 
            session.put("user", verifiedUser.id);
            User u = new User();
            u.openID = verifiedUser.id;
            u.insert();
            // TODO - we should complete registration here
            redirect("/");
        } else {
            OpenID.id(user).verify(); // will redirect the user
        }
    }

}