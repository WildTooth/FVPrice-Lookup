package com.github.wildtooth.fvtp.util

class FormattingUtil {
  init {
    throw AssertionError("Please do not instantiate this class.")
  }

  companion object {
    fun formatPriceRange(lowestVal: Int, highestVal: Int): String {
      return "${dbsToStacks(lowestVal)} - ${dbsToStacks(highestVal)}"
    }

    fun dbsToStacks(dbs: Int): String {
      val remainingDbs = dbs % 64
      if (remainingDbs == dbs) {
        return "$dbs DBS"
      }
      var stacks = "${dbs / 64} stacks"
      stacks += if (remainingDbs != 0) {
        " og $remainingDbs DBS"
      } else " DBS"
      return stacks
    }
  }
}