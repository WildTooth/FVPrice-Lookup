package com.github.wildtooth.fvtp.listener

import com.github.wildtooth.fvtp.FreakyVilleAddon
import com.github.wildtooth.fvtp.item.SkullItem
import com.github.wildtooth.fvtp.storage.InformationStorage
import com.github.wildtooth.fvtp.util.DisplayUtil
import com.github.wildtooth.fvtp.util.FormattingUtil
import net.labymod.api.client.component.Component
import net.labymod.api.client.component.format.NamedTextColor
import net.labymod.api.client.component.format.TextColor
import net.labymod.api.client.component.serializer.plain.PlainTextComponentSerializer
import net.labymod.api.client.world.item.ItemStack
import net.labymod.api.event.Subscribe
import net.labymod.api.event.client.world.ItemStackTooltipEvent
import net.labymod.api.util.I18n

class ItemStackTooltipListener(private val addonMain: FreakyVilleAddon) {
  @Subscribe
  fun onItemStackTooltipEvent(event: ItemStackTooltipEvent) {
    val eventItemStack: ItemStack = event.itemStack()
    val storage: InformationStorage = InformationStorage.instance!!
    if (storage.get(
        PlainTextComponentSerializer.plainText().serialize(eventItemStack.displayName).lowercase()
    ) == null) {
      return
    }
    val fvItem = storage.get(
      PlainTextComponentSerializer.plainText().serialize(eventItemStack.displayName).lowercase()
    )
    val tooltipLines = event.tooltipLines
    if (fvItem is SkullItem) {
      if (!eventItemStack.isSkull()) {
        return
      }
      val messageKey = FreakyVilleAddon.messageKey()
      when (fvItem.rarity) {
        SkullItem.Rarity.UNIQUE -> {
          DisplayUtil.displayPrettily(tooltipLines, NamedTextColor.GREEN,
            I18n.translate(messageKey + "items.sellersPrice"))
        }
        SkullItem.Rarity.MULTIPLE_RARITIES -> {
          DisplayUtil.displayPrettily(
            tooltipLines,
            I18n.translate(messageKey + "items.duplicatedName"),
            I18n.translate(messageKey + "items.impossiblePrediction")
          )
        }
        else -> {
          val min = fvItem.getMinimumPrice().toInt()
          val max = fvItem.getMaximumPrice().toInt()
          DisplayUtil.displayPrettily(
            tooltipLines,
            index = 1,
            I18n.translate(messageKey + "formatting.price",
              FormattingUtil.formatPriceRange(min, max)),
            NamedTextColor.GREEN
          )
        }
      }
      if (addonMain.configuration().debug().get()) {
        val diamondBlocksAbbreviation = I18n.translate(messageKey + "general.diamondBlocksAbbreviation")
        DisplayUtil.displayPrettily(
          tooltipLines,
          index = tooltipLines.size,
          NamedTextColor.YELLOW,
          "",
          "[ ${I18n.translate(messageKey + "debugging.debug").uppercase()} ]",
          "",
          I18n.translate(messageKey + "debugging.minimum", fvItem.getMinimumPrice(), diamondBlocksAbbreviation),
          I18n.translate(messageKey + "debugging.maximum", fvItem.getMaximumPrice(), diamondBlocksAbbreviation),
          I18n.translate(messageKey + "debugging.average", fvItem.getAveragePrice(), diamondBlocksAbbreviation),
          "",
          I18n.translate(messageKey + "debugging.sellersPrice", fvItem.isSellersPrice()),
          "",
          I18n.translate(messageKey + "debugging.rarity", fvItem.rarity.toString()),
          I18n.translate(messageKey + "debugging.modifiers", fvItem.modifiers.contentToString()),
        )
      }
    }
  }

  private fun ItemStack.isSkull(): Boolean {
    return this.identifier.path.equals("skull") or
      this.identifier.path.contains("_head") or
      this.identifier.path.contains("_skull")
  }
}