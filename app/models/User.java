package models;

import java.util.Date;
import java.util.List;
import siena.*;

@Table("User")
public class User extends Model {

    @Id
    public Long id;

    @Column("xmpp_id")
    @Max(200) @NotNull
    public String xmppID;

    @Column("twitter_token")
    @Max(400)
    public String twitterToken;

    @Column("twitter_token_secret")
    @Max(400)
    public String twitterTokenSecret;

    @Column("last_reminded")
    public Date lastReminded;

    /*
     * Instance Methods
     */

    public User(String xmppID) {
        this.xmppID = xmppID;
        // Default time should be 1 hr, 1 minute ago to force update.
        this.lastReminded = new Date(new Date().getTime() - (61 * 60 * 1000));
    }
    
    public boolean isActive() {
        return xmppID != null && twitterToken != null && twitterTokenSecret != null;
    }


    /*
     * Finder Methods
     */

    public static User byOpenID(String openID) {
        return Model.all(User.class).filter("xmppID", openID).get();
    }

    public static List needsReminder() {
        Date hourAgo = new Date(new Date().getTime() - (60 * 60 * 1000));
        return all(User.class).filter("lastReminded <", hourAgo).fetch();
    }

    public void addWatch(String term) {
        // Need to find or create the watch.
        Watch w = Watch.findOrCreate(term);

        // Need to create a new assoc object.
        UserWatch uw = new UserWatch(this, w);
        uw.insert();
    }

    public void removeWatch(String term) {
        Watch w = Watch.byTerm(term);
        if (w != null) {
            UserWatch.byUserAndWatch(this, w).delete();
        }
        // Now we need to check if the watch has any users left
        if (UserWatch.byWatch(w).size() == 0) {
            w.delete();
        }
    }
}
