package com.github.wildtooth.fvtp.listener

import com.github.wildtooth.fvtp.item.SkullItem
import com.github.wildtooth.fvtp.storage.InformationStorage
import net.labymod.api.client.component.Component
import net.labymod.api.client.component.TextComponent
import net.labymod.api.client.world.item.ItemStack
import net.labymod.api.event.Subscribe
import net.labymod.api.event.client.world.ItemStackTooltipEvent

class ItemStackTooltipListener {
  @Subscribe
  fun onItemStackTooltipEvent(event: ItemStackTooltipEvent) {
    val eventItemStack: ItemStack = event.itemStack()
    val storage: InformationStorage = InformationStorage.instance!!
    if (storage.get((eventItemStack.displayName as TextComponent).text.lowercase()) == null) {
      return
    }
    val fvItem = storage.get((eventItemStack.displayName as TextComponent).text.lowercase())
    if (fvItem is SkullItem) {
      if (!event.itemStack().identifier.path.equals("skull")) {
        return
      }
      if (fvItem.isSellersPrice()) {
        event.tooltipLines.add(Component.text("Værdi: Sælgerspris"))
      } else {
        event.tooltipLines.add(
          Component.text(
            "Værdi: ${fvItem.getMinimumPriceInStacks()} - ${fvItem.getMaximumPriceInStacks()} Stacks DBS"
          )
        )
      }
    }
  }
}