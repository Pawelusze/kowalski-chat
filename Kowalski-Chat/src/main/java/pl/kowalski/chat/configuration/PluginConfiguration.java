package pl.kowalski.chat.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import pl.kowalski.chat.ChatStatus;

public class PluginConfiguration extends OkaeriConfig {

    @Comment("# Status ktory bedzie posiadac chat")
    public ChatStatus chatStatus = ChatStatus.ENABLED;

    @Comment("# Sekcja odpowiadająca za wiadomosci")
    public MessageConfiguration messages = new MessageConfiguration();
}
