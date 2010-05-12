package models

import java.util.Date
import reflect.BeanProperty
import siena._

@Table("User")
class User() extends Model {

  @Id
  @BeanProperty
  var id:Long = -1

  @Column(Array("xmpp_id"))
  @Max(200) @NotNull
  @BeanProperty
  var xmppID:String = null

  @Column(Array("twitter_token"))
  @BeanProperty
  var twitterToken:String = null

  @Column(Array("twitter_token_secret"))
  @BeanProperty
  var twitterTokenSecret:String = null

  @Column(Array("last_reminded"))
  @BeanProperty
  var lastReminded = new Date(new Date().getTime() - (61 * 60 * 1000))

  @Column(Array("disabled"))
  @BeanProperty
  var disabled = false


  def isActive():Boolean = {
    xmppID != null && twitterToken != null &&
      twitterTokenSecret != null && !disabled;
  }

  def addWatch(term:String) {
    // Need to find or create the watch.
    var w = Watch.findOrCreate(term);

    // Need to create a new assoc object.
    var uw = new UserWatch();
    uw.userId = this.id;
    uw.watchId = w.id;
    uw.insert();
  }

  def removeWatch(term:String) {
    var w = Watch.byTerm(term);
    if (w != null) {
      UserWatch.byUserAndWatch(this, w).delete();
    }
    // Now we need to check if the watch has any users left
    if (UserWatch.byWatch(w).size == 0) {
      w.delete();
    }
  }
}

object User extends Model {
  /*
     * Finder Methods
     */

  def byXmppId(xmppID:String):User = {
    Model.all(classOf[User]).filter("xmppID", xmppID).get
  }

  def needsReminder():java.util.List[User] = {
    var hourAgo = new Date(new Date().getTime() - (60 * 60 * 1000))
    Model.all(classOf[User]).filter("lastReminded <", hourAgo).fetch
  }
}