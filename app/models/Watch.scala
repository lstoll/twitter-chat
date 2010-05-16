/*package models

import siena._
import reflect.BeanProperty
import java.util.ArrayList
import scala.collection.JavaConversions._

@Table("Watch")
class Watch extends Model {

  @Id
  @BeanProperty
  var id:Long = -1

  @Column(Array("term"))
  @Max(200) @NotNull
  @BeanProperty
  var term: String = null


  def users():java.util.List[User] = {
    var users = new ArrayList[User];
    var userWatches = Model.all(classOf[UserWatch]).filter("watchId", this.id).fetch
    userWatches.foreach { uw =>
      users.add(Model.all(classOf[User]).filter("id", uw.getId).get)
    }
    users
  }
}

object Watch {

  def findOrCreate(term:String):Watch = {
        var w = byTerm(term);
        if (w == null) {
          w = new Watch()
          w.term = term
          w.insert
        }
        return w
    }

  def byTerm(term: String):Watch = {
    Model.all(classOf[Watch]).filter("term", term).get
  }

  def all = {
    Model.all(classOf[Watch])
  }

}
*/
