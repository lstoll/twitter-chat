package models

import java.util.Date
import play.modules.objectify.ObjectifyModel
import play.data.validation.Required
import play.modules.objectify.Datastore
import play.modules.objectify.ObjectifyService
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query
import javax.persistence.Id


class User extends ObjectifyModel {

  @Id
  var id:Long = -1

  @Required
  var xmppID:String = null

  var twitterToken:String = null

  var twitterTokenSecret:String = null

  var lastReminded = new Date(new Date().getTime() - (61 * 60 * 1000))

  var disabled = false


  def isActive():Boolean = {
    xmppID != null && twitterToken != null &&
      twitterTokenSecret != null && !disabled;
  }

  def save():Key[User] = {
    ObjectifyService.put(this)
  }

  def delete() {
    ObjectifyService.delete(this)
  }
  
  /*def addWatch(term:String) {
    // Need to find or create the watch.
    var w = Watch.findOrCreate(term);

    // Need to create a new assoc object.
    var uw = new UserWatch
    uw.user = new Key[User](classOf[User], this.id);
    uw.watch = new Key[User](classOf[User], uw.ID);
    uw.save
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
  }*/
}

class UserQueries {
  def byXmppId(xmppID:String):User = {
    ObjectifyService.query(classOf[User]).filter("xmppID", xmppID).get
  }

  def needsReminder():Query[User] = {
    var hourAgo = new Date(new Date().getTime() - (60 * 60 * 1000))
    ObjectifyService.query(classOf[User]).filter("lastReminded <", hourAgo)
  }
}

object User {
  var queryObj = new UserQueries
  def resetQueryObj = queryObj = new UserQueries

  def query = queryObj
}
