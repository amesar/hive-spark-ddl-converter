package org.amm.spark.sql

import org.testng.Assert._
          
/** TestNG utilities. */

object TestNgUtils {

  def buildDataProviderArray(seq: Seq[AnyRef]) : Array[Array[AnyRef]] = {
    val objs: Array[Array[AnyRef]] = new Array[Array[AnyRef]](seq.size)
    for((elt,j) <- seq.view.zipWithIndex) {
      val eltArray: Array[AnyRef] = new Array[AnyRef](1)
      eltArray(0) = elt
      objs(j) = eltArray
    }
    objs
  }
}
