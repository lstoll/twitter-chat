package models;

import com.google.appengine.api.datastore.DatastoreService;
import java.util.ArrayList;
import java.util.List;
import siena.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Table("Watch")
public class Watch extends Model {

    @Id
    public Long id;

    @Column("term")
    @Max(200) @NotNull
    public String term;

    /*
     * Instance Methods
     */

    public Watch(String term) {
        this.term = term;
    }

    public List<User> users() {
        List users = new ArrayList();
        List<UserWatch> userWatches = Model.all(UserWatch.class).
                filter("watchId", this.id).fetch();
        for (UserWatch uw : userWatches) {
            users.add(Model.all(User.class).filter("id", uw.userId).get());
        }
        return users;
    }

    /*
     * Finders
     */

    
    public static Watch findOrCreate(String term) {
        Watch w = byTerm(term);
        if (w == null) {
            w = new Watch(term);
            w.insert();
        }
        return w;
    }

    public static Watch byTerm(String term) {
        return Model.all(Watch.class).filter("term", term).get();
    }
}
