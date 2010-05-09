package models;

import siena.*;

@Table("User")
public class User extends Model {

    @Id
    public Long id;

    @Column("open_id")
    @Max(200) @NotNull
    public String openID;

    public static User byOpenID(String openID) {
        return Model.all(User.class).filter("openID", openID).get();
    }
}
