package com.github.wildtooth.fvtp

import net.labymod.api.addon.LabyAddon

class FreakyVilleAddon: LabyAddon<FreakyVilleAddonConfiguration>() {
  override fun enable() {
    registerSettingCategory()

    logger().info("Enabled the Addon")
  }

  override fun configurationClass(): Class<out FreakyVilleAddonConfiguration> {
    return FreakyVilleAddonConfiguration::class.java
  }
}