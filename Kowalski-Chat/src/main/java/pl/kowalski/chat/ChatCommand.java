package pl.kowalski.chat;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import pl.kowalski.chat.configuration.PluginConfiguration;
import pl.kowalski.chat.multification.ChatMultification;
import pl.kowalski.chat.util.DurationUtil;

import java.time.Duration;


@Command(name = "chat")
@Permission("chat.admin")
public class ChatCommand {

    private final PluginConfiguration configuration;
    private final ChatMultification multification;
    private final ChatService chatService;

    public ChatCommand(PluginConfiguration configuration, ChatMultification multification, ChatService chatService) {
        this.configuration = configuration;
        this.multification = multification;
        this.chatService = chatService;
    }

    @Execute(name = "on")
    @Description("Enables chat")
    void enableChat(@Sender CommandSender sender) {
        if (this.configuration.chatStatus == ChatStatus.ENABLED) {
            this.multification.create().viewer(sender).notice(msg -> msg.messages.chatAlreadyEnabled).send();
            return;
        }
        this.multification.viewer(sender, msg -> msg.messages.chatEnabled);
        this.configuration.chatStatus = ChatStatus.ENABLED;
        this.configuration.save();

    }

    @Execute(name = "off")
    @Description("Disables chat")
    void disableChat(@Sender CommandSender sender) {
        if (this.configuration.chatStatus == ChatStatus.DISABLED) {
            this.multification.create().viewer(sender).notice(msg -> msg.messages.chatAlreadyDisabled).send();
            return;
        }
        this.multification.viewer(sender, msg -> msg.messages.chatDisabled);
        this.configuration.chatStatus = ChatStatus.DISABLED;
        this.configuration.save();
    }

    @Execute(name = "premium")
    @Description("Enables premium chat")
    void premiumChat(@Sender CommandSender sender) {
        if (this.configuration.chatStatus == ChatStatus.PREMIUM) {
            this.multification.create().viewer(sender).notice(msg -> msg.messages.chatPremiumDisabled).send();
            this.configuration.chatStatus = ChatStatus.ENABLED;
            return;
        }
        this.multification.viewer(sender, msg -> msg.messages.chatPremiumEnabled);
        this.configuration.chatStatus = ChatStatus.PREMIUM;
        this.configuration.save();
    }


    @Execute(name = "slowdown")
    @Description("Enables cooldown for chat")
    void slowdownChat(@Sender CommandSender sender, @Arg Duration duration) {
        if (duration.isNegative()) {
            this.multification.create().viewer(sender).notice(msg -> msg.messages.numberIsNegative).send();
            return;
        }

        if (duration.isZero()) {
            this.chatService.setChatDelay(Duration.ZERO);
            this.multification.create().viewer(sender).notice(msg -> msg.messages.chatSlowModeDisabled).send();
            this.configuration.save();
            return;
        }

        this.chatService.setChatDelay(duration);
        this.multification.create().viewer(sender)
                .notice(msg -> msg.messages.chatSlowModeSet)
                .placeholder("{DURATION}", DurationUtil.format(duration, true))
                .send();
        this.configuration.save();

    }

    @Execute(name = "slowdown 0")
    @Description("Disables cooldown for chat")
    void slowdownZero(@Sender CommandSender sender) {
        this.chatService.setChatDelay(Duration.ZERO);
        this.multification.create().viewer(sender).notice(msg -> msg.messages.chatSlowModeDisabled).send();
        this.configuration.save();
    }
}