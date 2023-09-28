package com.github.wildtooth.fvtp.listener

import com.github.wildtooth.fvtp.item.SkullItem
import com.github.wildtooth.fvtp.storage.InformationStorage
import net.labymod.api.client.component.Component
import net.labymod.api.client.component.format.NamedTextColor
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
    if (fvItem is SkullItem) {
      if (!eventItemStack.isSkull()) {
        return
      }
      when (fvItem.rarity) {
        SkullItem.Rarity.UNIQUE -> {
          event.tooltipLines.add(1, Component.text("Værdi: Sælgers pris"))
        }
        SkullItem.Rarity.MULTIPLE_RARITIES -> {
          event.tooltipLines.add(1, Component.text(
            "Der findes flere items med dette navn.", NamedTextColor.GRAY
          ))
          event.tooltipLines.add(2, Component.text(
            "Derfor kan værdien ikke forudsiges.", NamedTextColor.GRAY
          ))
        }
        else -> {
          event.tooltipLines.add(1, Component.text(
              "Værdi: ${fvItem.getMinimumPriceInStacks()} - ${fvItem.getMaximumPriceInStacks()} Stacks DBS"
            )
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