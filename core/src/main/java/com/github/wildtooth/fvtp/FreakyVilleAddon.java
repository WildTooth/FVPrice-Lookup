package com.github.wildtooth.fvtp;

import com.github.wildtooth.fvtp.storage.InformationConnector;
import com.github.wildtooth.fvtp.storage.InformationReceiver;
import com.github.wildtooth.fvtp.storage.InformationStorage;
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
  }

  @Override
  protected Class<? extends FreakyVilleAddonConfiguration> configurationClass() {
    return FreakyVilleAddonConfiguration.class;
  }
}
