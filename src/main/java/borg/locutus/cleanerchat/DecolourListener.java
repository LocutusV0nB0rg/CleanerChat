package borg.locutus.cleanerchat;

import com.google.common.collect.Lists;
import net.labymod.api.events.MessageModifyChatEvent;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class DecolourListener implements MessageModifyChatEvent {
    private final List<String> specialRanks = Lists.newArrayList(
            "Builder",
            "Designer",
            "Supporter",
            "Moderator",
            "Content",
            "Developer",
            "Admin",
            "Owner",
            "Streamer",
            "YouTuber",
            "Freund");

    @Override
    public Object onModifyChatMessage(Object o) {
        if (!(o instanceof ChatComponentText)) {
            System.out.println("o.getClass().getSimpleName() = " + o.getClass().getSimpleName());
            throw new RuntimeException("This should not happen?");
        }

        ChatComponentText chatComponents = (ChatComponentText) o;
        String textWithoutColours = chatComponents.getUnformattedText();

        ChatComponentText returnValue;

        if (isStatusMessage(textWithoutColours)) {
            returnValue = new ChatComponentText("");
        } else if (isChatMessageWithClanTag(textWithoutColours)) {
            returnValue = new ChatComponentText(convertChatMessage(chatComponents));
        } else if (isChatMessageWithoutClanTag(textWithoutColours)) {
            returnValue = new ChatComponentText(convertChatMessage(chatComponents));
        } else {
            returnValue = chatComponents;
        }

        return returnValue;
    }

    private String convertChatMessage(ChatComponentText chatComponents) {
        String unformattedText = chatComponents.getUnformattedText();

        String playername;
        boolean isSpecialMessage;

        if (isChatMessageWithClanTag(chatComponents.getUnformattedText())) {
            playername = unformattedText.split(" ")[3];
            isSpecialMessage = specialRanks.stream().anyMatch(value -> value.toLowerCase().contains(unformattedText.split(" ")[1].toLowerCase()));
        } else {
            playername = unformattedText.split(" ")[2];

            isSpecialMessage = specialRanks.stream().anyMatch(value -> unformattedText.split(" ")[0].toLowerCase().contains(value.toLowerCase()));
        }

        String colourForMessage = isSpecialMessage ? "§6" : "§7";
        String message = colourForMessage + unformattedText.substring(unformattedText.indexOf("»") + 1);

        return "§r§f" + playername + " §f»" + message;
    }

    private boolean isStatusMessage(String unformattedMessage) {
        return unformattedMessage.contains("┃") && !unformattedMessage.contains("»");
    }

    private boolean isChatMessageByPlayer(String unformattedMessage) {
        return unformattedMessage.contains("┃") && unformattedMessage.contains("»");
    }

    private boolean isChatMessageWithClanTag(String unformattedMessage) {
        if (!isChatMessageByPlayer(unformattedMessage)) return false;

        String[] splitMessage = unformattedMessage.split(" ");
        if (splitMessage.length < 2) return false;

        String firstWord = splitMessage[0];
        return firstWord.startsWith("[") && firstWord.endsWith("]");
    }

    private boolean isChatMessageWithoutClanTag(String unformattedMessage) {
        return isChatMessageByPlayer(unformattedMessage) && !isChatMessageWithClanTag(unformattedMessage);
    }
}
