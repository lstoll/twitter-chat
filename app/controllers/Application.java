package controllers;

import play.mvc.*;
import play.libs.OpenID;
import play.libs.OpenID.*;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void login() {
        render();
    }
    
    public static void authenticate(String user) {
        if(OpenID.isAuthenticationResponse()) {
            UserInfo verifiedUser = OpenID.getVerifiedID();
            if(verifiedUser == null) {
                flash.put("error", "Oops. Authentication has failed");
                login();
            } 
            session.put("user", verifiedUser.id);
            index();
        } else {
            OpenID.id(user).verify(); // will redirect the user
        }
    }   
}