package se.marcuslonnberg.adaywithsbt

object Sorter {
  def apply[T](coll: Seq[T])(implicit ordering: Ordering[T]): Seq[T] = {
    coll.reverse
  }
}
