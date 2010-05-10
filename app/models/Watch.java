package models;

import siena.*;

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

    /*
     * Finders
     */

    
    public static Watch findOrCreate(String term) {
        Watch w = Model.all(Watch.class).filter("term", term).get();
        if (w == null) {
            w = new Watch(term);
            w.insert();
        }
        return w;
    }
}
