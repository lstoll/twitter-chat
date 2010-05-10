package models;

import java.util.List;
import siena.*;

@Table("UserWatch")
public class UserWatch extends Model {

    @Id
    public Long id;

    @Column("user_id")
    @Max(200) @NotNull
    public Long userId;

    @Column("watch_id")
    @Max(200) @NotNull
    public Long watchId;

    /*
     * Instance Methods
     */
    public UserWatch(User u, Watch w) {
        this.userId = u.id;
        this.watchId = w.id;
    }

    public static UserWatch byUserAndWatch(User u, Watch w) {
        return Model.all(UserWatch.class).filter("userId", u.id).
                                          filter("watchId", w.id).get();
    }

    public static List<UserWatch> byWatch(Watch w) {
        return Model.all(UserWatch.class).filter("watchId", w.id).fetch();
    }
}
