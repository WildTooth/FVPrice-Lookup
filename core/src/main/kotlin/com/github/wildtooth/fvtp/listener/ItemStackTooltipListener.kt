package com.github.wildtooth.fvtp.listener

import com.github.wildtooth.fvtp.item.SkullItem
import com.github.wildtooth.fvtp.storage.InformationStorage
import com.github.wildtooth.fvtp.util.DisplayUtil
import net.labymod.api.client.component.Component
import net.labymod.api.client.component.format.NamedTextColor
import net.labymod.api.client.component.format.TextColor
import net.labymod.api.client.component.serializer.plain.PlainTextComponentSerializer
import net.labymod.api.client.world.item.ItemStack
import net.labymod.api.event.Subscribe
import net.labymod.api.event.client.world.ItemStackTooltipEvent

class ItemStackTooltipListener {
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
      when (fvItem.rarity) {
        SkullItem.Rarity.UNIQUE -> {
          DisplayUtil.displayPrettily(tooltipLines, "Værdi: Sælgers pris")
        }
        SkullItem.Rarity.MULTIPLE_RARITIES -> {
          DisplayUtil.displayPrettily(
            tooltipLines,
            "Der findes flere items med dette navn.",
            "Derfor kan værdien ikke forudsiges."
          )
        }
        else -> {
          DisplayUtil.displayPrettily(
            tooltipLines,
            NamedTextColor.GREEN,
            "Værdi: ${fvItem.getMinimumPriceInStacks()} - ${fvItem.getMaximumPriceInStacks()} Stacks DBS"
          )
        }
      }
    }
  }

  private fun ItemStack.isSkull(): Boolean {
    return this.identifier.path.equals("skull") or
      this.identifier.path.contains("_head") or
      this.identifier.path.contains("_skull")
  }
}