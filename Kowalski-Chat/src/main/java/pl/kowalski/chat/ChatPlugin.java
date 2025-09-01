package pl.kowalski.chat;

import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry;
import com.eternalcode.multification.notice.resolver.actionbar.ActionbarResolver;
import com.eternalcode.multification.notice.resolver.bossbar.BossBarResolver;
import com.eternalcode.multification.notice.resolver.bossbar.BossBarService;
import com.eternalcode.multification.notice.resolver.chat.ChatResolver;
import com.eternalcode.multification.notice.resolver.sound.SoundAdventureResolver;
import com.eternalcode.multification.notice.resolver.title.*;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import pl.kowalski.chat.configuration.ConfigService;
import pl.kowalski.chat.configuration.PluginConfiguration;
import pl.kowalski.chat.controller.ChatController;
import pl.kowalski.chat.multification.ChatMultification;

import java.io.File;

public final class ChatPlugin extends JavaPlugin {

    private LiteCommands<org.bukkit.command.CommandSender> liteCommands;
    private ConfigService configService;
    private PluginConfiguration configuration;
    private ChatMultification multification;
    private ChatService chatService;

    @Override
    public void onEnable() {
        File dataFolder = this.getDataFolder();

        this.configService = new ConfigService(new NoticeResolverRegistry()
                .registerResolver(new ChatResolver())
                .registerResolver(new TitleResolver())
                .registerResolver(new SubtitleResolver())
                .registerResolver(new TitleWithEmptySubtitleResolver())
                .registerResolver(new SubtitleWithEmptyTitleResolver())
                .registerResolver(new TimesResolver())
                .registerResolver(new TitleHideResolver())
                .registerResolver(new SoundAdventureResolver())
                .registerResolver(new ActionbarResolver())
                .registerResolver(new BossBarResolver(new BossBarService()))
        );
        this.configuration = this.configService.create(PluginConfiguration.class, new File(dataFolder, "config.yml"));
        this.multification = new ChatMultification(configuration, MiniMessage.miniMessage());
        this.chatService = new ChatService();

        getServer().getPluginManager().registerEvents(
                new ChatController(this.configuration, this.multification, this.chatService),
                this
        );

        this.liteCommands = LiteBukkitFactory.builder("chat-plugin", this)
                .commands(new ChatCommand(this.configuration, this.multification, this.chatService)).build();
    }

    @Override
    public void onDisable() {
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
    }
}
