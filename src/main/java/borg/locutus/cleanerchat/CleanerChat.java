package borg.locutus.cleanerchat;

import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

import java.util.List;

public class CleanerChat extends LabyModAddon {
    @Override
    public void onEnable() {
        getApi().getEventManager().register(new DecolourListener());
        getApi().getEventManager().register(new SpacerRemoveListener());
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }
}
