import org.junit.*;
import play.test.*;
import play.mvc.Http.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lstoll
 */
public class RegistrationProcessTest extends FunctionalTest {

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset("utf-8", response);
    }

    // Test that a user gets a welcome XMPP page with a link to the oauth page

    // Test that when OAUTH returns, user is sent to the usage page

}
