package com.github.wildtooth.fvpl;

import com.github.wildtooth.fvpl.listener.ItemStackTooltipListener;
import com.github.wildtooth.fvpl.listener.ServerNavigationListener;
import com.github.wildtooth.fvpl.storage.InformationConnector;
import com.github.wildtooth.fvpl.storage.InformationReceiver;
import com.github.wildtooth.fvpl.storage.InformationStorage;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import java.io.IOException;
import java.net.URL;

@AddonMain
public class FreakyVilleAddon extends LabyAddon<FreakyVilleAddonConfiguration> {

  private boolean onlineOnFreakyVille;

  @Override
  protected void enable() {
    this.registerSettingCategory();

    InformationStorage informationStorage = new InformationStorage();
    InformationConnector informationConnector = new InformationConnector();
    InformationReceiver informationReceiver = new InformationReceiver(informationStorage);

    this.onlineOnFreakyVille = false;

    try {
      informationReceiver.catchConnection(informationConnector.getInformationConnection(
          new URL("https://raw.githubusercontent.com/WildTooth/FreakyVille-General-Data/main/items/fvtp_vv")
      ));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.registerListener(new ItemStackTooltipListener(this));
    this.registerListener(new ServerNavigationListener(this));
  }

  @Override
  protected Class<? extends FreakyVilleAddonConfiguration> configurationClass() {
    return FreakyVilleAddonConfiguration.class;
  }

  public static String messageKey() {
    return "fvpl.messages.";
  }

  public boolean isOnlineOnFreakyVille() {
    return this.onlineOnFreakyVille;
  }

  public void setOnlineOnFreakyVille(boolean onlineOnFreakyVille) {
    this.onlineOnFreakyVille = onlineOnFreakyVille;
  }
}
