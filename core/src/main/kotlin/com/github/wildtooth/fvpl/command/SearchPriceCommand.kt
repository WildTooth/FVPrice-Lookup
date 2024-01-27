package com.github.wildtooth.fvpl.command

import com.github.wildtooth.fvpl.FreakyVilleAddon
import com.github.wildtooth.fvpl.item.FVItem
import com.github.wildtooth.fvpl.item.PricedItem
import com.github.wildtooth.fvpl.item.SkullItem
import com.github.wildtooth.fvpl.storage.InformationStorage
import net.labymod.api.client.chat.command.Command
import net.labymod.api.client.component.Component
import net.labymod.api.client.component.format.NamedTextColor
import net.labymod.api.util.I18n

class SearchPriceCommand : Command("search") {
  override fun execute(prefix: String, arguments: Array<String>): Boolean {
    if (arguments.isEmpty()) {
      return false
    }
    val searchFor: String = arguments.joinToString(" ") { it }
    val storage: InformationStorage = InformationStorage.instance!!
    val item = storage.get(searchFor.lowercase())
    if (item == null) {
      displayTranslatable(
        FreakyVilleAddon.messageKey() + "commands.search.noItem",
        NamedTextColor.YELLOW,
        searchFor
      )
      return false
    }
    replyPrettily(searchFor, item)
    return true
  }

  private fun replyPrettily(searchFor: String, item: FVItem) {
    val messageKey = FreakyVilleAddon.messageKey()
    val stacks = I18n.translate(messageKey + "general.stacks")
    val dbs = I18n.translate(messageKey + "general.diamondBlocksAbbreviation")
    when(item) {
      is PricedItem -> {
        val returnString = I18n.translate(messageKey + "commands.search.final",
          searchFor,
          item.expectedPriceDBS,
          if (item.stackPrice) "$stacks $dbs" else dbs
        )
        displayMessage(Component.text(returnString, NamedTextColor.YELLOW))
      }
      is SkullItem -> {
        whenSkullItem(messageKey, stacks, dbs, searchFor, item)
      }
    }
  }

  // @TODO This should be redone since it's a bit of a mess
  // @TODO It also should be changed to displaying the correct prices when in stacks and not.

  private fun whenSkullItem(messageKey: String, stacks: String, dbs: String, searchFor: String, item: SkullItem) {
    val args: Array<String> = arrayOf("average", "minimum", "maximum")
    for (arg in args) {
      val returnString = I18n.translate(messageKey + "commands.search.$arg",
        searchFor,
        when(arg) {
          "average" -> item.getAveragePrice()
          "minimum" -> item.getMinimumPrice()
          "maximum" -> item.getMaximumPrice()
          else -> 0.0
        },
        if (item.isSellersPrice()) {
          "(${I18n.translate(messageKey + "general.sellersPrice")})"
        } else {
          if (item.getAveragePrice() > 128) "$stacks $dbs" else dbs
        }
      )
      displayMessage(Component.text(returnString, NamedTextColor.YELLOW))
    }

  }
}