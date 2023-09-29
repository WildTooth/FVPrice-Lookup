package com.github.wildtooth.fvtp

import net.labymod.api.addon.AddonConfig
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting
import net.labymod.api.configuration.loader.annotation.ConfigName
import net.labymod.api.configuration.loader.property.ConfigProperty
import net.labymod.api.configuration.settings.annotation.SettingSection


@ConfigName("settings")
class FreakyVilleAddonConfiguration : AddonConfig() {
  @SwitchSetting
  @SettingSection("general")
  private val enabled = ConfigProperty(true)
  @SwitchSetting
  @SettingSection("miscellaneous")
  private val debug = ConfigProperty(false)
  override fun enabled(): ConfigProperty<Boolean> {
    return enabled
  }
  fun debug(): ConfigProperty<Boolean> {
    return debug
  }
}