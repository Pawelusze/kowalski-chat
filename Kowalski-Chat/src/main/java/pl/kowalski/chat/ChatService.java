package pl.kowalski.chat;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class ChatService {

    private final Cache<UUID, Instant> slowdown;
    private volatile Duration chatDelay = Duration.ZERO;

    public ChatService() {
        this.slowdown = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(15))
                .build();
    }

    public void setChatDelay(Duration delay) {
        if (delay == null || delay.isNegative()) {
            this.chatDelay = Duration.ZERO;
            return;
        }
        this.chatDelay = delay;
    }

    public boolean hasSlowedChat(UUID userUuid) {
        if (this.chatDelay.isZero()) {
            return false;
        }
        Instant unlockAt = this.slowdown.asMap().get(userUuid);
        return unlockAt != null && Instant.now().isBefore(unlockAt);
    }

    public void markUseChat(UUID userUuid) {
        Duration delay = this.chatDelay;
        if (delay.isZero()) {
            return;
        }
        this.slowdown.put(userUuid, Instant.now().plus(delay));
    }

    public Duration getRemainingTime(UUID userUuid) {
        Instant unlockMoment = this.slowdown.asMap().get(userUuid);

        if (unlockMoment == null) {
            return Duration.ZERO;
        }

        Duration remaining = Duration.between(Instant.now(), unlockMoment);
        return remaining.isNegative() ? Duration.ZERO : remaining;
    }
}
