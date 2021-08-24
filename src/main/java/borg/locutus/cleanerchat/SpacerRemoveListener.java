package borg.locutus.cleanerchat;

import net.labymod.api.events.MessageReceiveEvent;

public class SpacerRemoveListener implements MessageReceiveEvent {
    @Override
    public boolean onReceive(String message, String unformattedMessage) {
        if (unformattedMessage.startsWith("»")) return true;

        return false;
    }
}
