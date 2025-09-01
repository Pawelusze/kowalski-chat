package pl.kowalski.chat.controller;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.kowalski.chat.ChatService;
import pl.kowalski.chat.ChatStatus;
import pl.kowalski.chat.configuration.PluginConfiguration;
import pl.kowalski.chat.multification.ChatMultification;
import pl.kowalski.chat.util.DurationUtil;

import java.time.Duration;
import java.util.UUID;

public class ChatController implements Listener {
    static final String ADMIN_PERMISSION = "chat.admin";

    private final PluginConfiguration configuration;
    private final ChatMultification multification;
    private final ChatService chatService;
    public ChatController(PluginConfiguration configuration, ChatMultification multification, ChatService chatService) {
        this.configuration = configuration;
        this.multification = multification;
        this.chatService = chatService;
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        if (player.hasPermission(ADMIN_PERMISSION)) {
            return;
        }

        if (this.configuration.chatStatus == ChatStatus.DISABLED) {
            event.setCancelled(true);
            this.multification.viewer(player, msg -> msg.messages.chatIsDisabled);
            return;
        }

        if (this.configuration.chatStatus == ChatStatus.PREMIUM && !player.hasPermission("chat.premium")) {
            event.setCancelled(true);
            this.multification.viewer(player, msg -> msg.messages.chatOnlyPremium);
        }

        if (this.chatService.hasSlowedChat(uniqueId) && !player.hasPermission(ADMIN_PERMISSION)) {
            if (!event.isCancelled()) {
                Duration remainingDuration = this.chatService.getRemainingTime(uniqueId);

                this.multification.create().player(uniqueId)
                        .notice(msg -> msg.messages.slowModeCooldown)
                        .player(uniqueId)
                        .placeholder("{DURATION}", DurationUtil.format(remainingDuration, true))
                        .send();


                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void markUseChat(AsyncChatEvent event) {
        this.chatService.markUseChat(event.getPlayer().getUniqueId());
    }
}
