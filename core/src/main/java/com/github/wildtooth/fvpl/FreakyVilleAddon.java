package com.github.wildtooth.fvpl;

import com.github.wildtooth.fvpl.listener.ItemStackTooltipListener;
import com.github.wildtooth.fvpl.storage.InformationConnector;
import com.github.wildtooth.fvpl.storage.InformationReceiver;
import com.github.wildtooth.fvpl.storage.InformationStorage;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import java.io.IOException;
import java.net.URL;

@AddonMain
public class FreakyVilleAddon extends LabyAddon<FreakyVilleAddonConfiguration> {
  @Override
  protected void enable() {
    this.registerSettingCategory();

    InformationStorage informationStorage = new InformationStorage();
    InformationConnector informationConnector = new InformationConnector();
    InformationReceiver informationReceiver = new InformationReceiver(informationStorage);
    try {
      informationReceiver.catchConnection(informationConnector.getInformationConnection(
          new URL("https://raw.githubusercontent.com/WildTooth/FreakyVille-General-Data/main/items/fvtp_vv")
      ));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.registerListener(new ItemStackTooltipListener(this));
  }

  @Override
  protected Class<? extends FreakyVilleAddonConfiguration> configurationClass() {
    return FreakyVilleAddonConfiguration.class;
  }

  public static String messageKey() {
    return "fvpl.messages.";
  }
}
