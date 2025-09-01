package pl.kowalski.chat;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.Cache;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public abstract class ChatService {
    private final com.github.benmanes.caffeine.cache.Cache<Object, Object> slowdown;

    abstract Duration chatDelay();
    abstract ChatService chatDelay(Duration delay);

    public ChatService(Cache<UUID, Instant> slowdown) {
        this.slowdown = Caffeine.newBuilder()
                .expireAfterWrite(chatDelay().plus(Duration.ofSeconds(10)))
                .build();
    }
}
