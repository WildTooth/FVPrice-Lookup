package com.github.wildtooth.fvpl.listener

import com.github.wildtooth.fvpl.FreakyVilleAddon
import net.labymod.api.Laby
import net.labymod.api.client.component.Component
import net.labymod.api.client.gui.icon.Icon
import net.labymod.api.event.Subscribe
import net.labymod.api.event.client.network.server.ServerDisconnectEvent
import net.labymod.api.event.client.network.server.ServerJoinEvent
import net.labymod.api.notification.Notification
import net.labymod.api.util.I18n
import java.util.*

class ServerNavigationListener(private val addonMain: FreakyVilleAddon) {
  private var messageKey: String = FreakyVilleAddon.messageKey()

  @Subscribe
  fun onServerJoin(event: ServerJoinEvent) {
    if (!isFreakyVilleServer(event.serverData().address().host.lowercase())) {
      this.addonMain.isOnlineOnFreakyVille = false
    }
    this.addonMain.isOnlineOnFreakyVille = true
    createAndPushNotification(
      title = I18n.translate(this.messageKey + "notifications.title"),
      text = I18n.translate(this.messageKey + "notifications.onFreakyVille.text"),
    )
  }

  @Subscribe
  fun onServerLeave(event: ServerDisconnectEvent) {
    if (this.addonMain.isOnlineOnFreakyVille) {
      this.addonMain.isOnlineOnFreakyVille = false
      createAndPushNotification(
        title = I18n.translate(this.messageKey + "notifications.title"),
        text = I18n.translate(this.messageKey + "notifications.leavingFreakyVille.text"),
      )
    }
  }

  private fun isFreakyVilleServer(serverAddress: String): Boolean {
    return (serverAddress.lowercase().endsWith("mc.freakyville.net")
        || serverAddress.lowercase().endsWith("mc.freakyville.dk"))
  }

  private fun createAndPushNotification(
    title: String,
    text: String,
    icon: Icon = Icon.head(UUID.fromString("d3c47f6f-ae3a-45c1-ad7c-e2c762b03ae6")), // MHF_Exclamation (from Marc's Head Format)
    type: Notification.Type = Notification.Type.ADVANCEMENT
  ) {
    val builder = Notification.builder()
      .title(Component.text(title))
      .text(Component.text(text))
      .icon(icon)
      .type(type)
    Laby.labyAPI().notificationController().push(builder.build())
  }
}