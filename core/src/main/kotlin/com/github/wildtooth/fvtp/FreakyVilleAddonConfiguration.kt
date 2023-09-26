package com.github.wildtooth.fvtp

import net.labymod.api.addon.AddonConfig
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting
import net.labymod.api.configuration.loader.annotation.ConfigName
import net.labymod.api.configuration.loader.property.ConfigProperty


@ConfigName("settings")
class FreakyVilleAddonConfiguration : AddonConfig() {
  @SwitchSetting
  private val enabled = ConfigProperty(true)
  override fun enabled(): ConfigProperty<Boolean> {
    return enabled
  }
}