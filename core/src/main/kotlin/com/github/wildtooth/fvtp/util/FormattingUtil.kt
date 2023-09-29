package com.github.wildtooth.fvtp.util

import com.github.wildtooth.fvtp.FreakyVilleAddon
import net.labymod.api.util.I18n

class FormattingUtil {
  init {
    throw AssertionError("Please do not instantiate this class.")
  }

  companion object {
    fun formatPriceRange(lowestVal: Int, highestVal: Int): String {
      return "${dbsToStacks(lowestVal)} - ${dbsToStacks(highestVal)}"
    }

    fun dbsToStacks(dbs: Int): String {
      val messageKey = FreakyVilleAddon.messageKey()
      val remainingDbs = dbs % 64
      if (remainingDbs == dbs) {
        return "$dbs ${I18n.translate(messageKey + "general.diamondBlocksAbbreviation")}"
      }
      var stacks = "${dbs / 64} ${I18n.translate(messageKey + "general.stacks")}"
      stacks += if (remainingDbs != 0) {
        " ${I18n.translate(messageKey + "general.and")} $remainingDbs ${I18n.translate(messageKey + "general.diamondBlocksAbbreviation")}"
      } else " ${I18n.translate(messageKey + "general.diamondBlocksAbbreviation")}"
      return stacks
    }
  }
}