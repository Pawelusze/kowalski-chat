package pl.kowalski.chat.multification;

import com.eternalcode.multification.adventure.AudienceConverter;
import com.eternalcode.multification.bukkit.BukkitMultification;
import com.eternalcode.multification.translation.TranslationProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pl.kowalski.chat.configuration.PluginConfiguration;

public class ChatMultification extends BukkitMultification<PluginConfiguration> {

    private final PluginConfiguration pluginConfiguration;
    private final MiniMessage miniMessage;

    public ChatMultification(PluginConfiguration pluginConfiguration, MiniMessage miniMessage) {
        this.pluginConfiguration = pluginConfiguration;
        this.miniMessage = miniMessage;
    }

    @Override
    protected @NotNull TranslationProvider<PluginConfiguration> translationProvider() {
        return locale -> this.pluginConfiguration;
    }

    @Override
    protected @NotNull ComponentSerializer<Component, Component, String> serializer() {
        return this.miniMessage;
    }

    @Override
    protected @NotNull AudienceConverter<CommandSender> audienceConverter() {
        return commandSender -> commandSender;
    }

}