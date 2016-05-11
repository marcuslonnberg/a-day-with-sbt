package se.marcuslonnberg.adaywithsbt

import org.scalatest.{FlatSpec, Matchers}

class SorterSpec extends FlatSpec with Matchers {
  "Sorter" should "sort a short list" in {
    val in = Seq(3, 2, 1)
    val out = Sorter(in)

    out should contain theSameElementsInOrderAs Seq(1, 2, 3)
  }

  it should "sort a long list" in {
    val in = Seq(10, 9, 8, 1, 2, 3)
    val out = Sorter(in)

    out should contain theSameElementsInOrderAs Seq(1, 2, 3, 8, 9, 10)
  }

  it should "handle an empty list" in {
    val in = Seq()
    val out = Sorter(in)

    out shouldBe empty
  }
}
