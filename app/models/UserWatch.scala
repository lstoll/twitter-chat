package models

import reflect.BeanProperty
import siena._

@Table("UserWatch")
class UserWatch extends Model {

  @Id
  @BeanProperty
  var id: Long = -1

  @Column(Array("user_id"))
  @Max(200) @NotNull
  @BeanProperty
  var userId: Long = -1

  @Column(Array("watch_id"))
  @Max(200) @NotNull
  @BeanProperty
  var watchId: Long = -1
}

object UserWatch extends Model {
  def byUserAndWatch(u:User, w:Watch):UserWatch = {
    Model.all(classOf[UserWatch]).filter("userId", u.getId()).filter("watchId", w.id).get
  }

  def byWatch(w:Watch):java.util.List[UserWatch] = {
    Model.all(classOf[UserWatch]).filter("watchId", w.getId).fetch
  }

  def all = {
    Model.all(classOf[UserWatch])
  }
}
