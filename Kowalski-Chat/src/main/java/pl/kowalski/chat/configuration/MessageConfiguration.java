package pl.kowalski.chat.configuration;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

public class MessageConfiguration extends OkaeriConfig {

    @Comment("# Wiadomosc gdy gracz nie posiada odpowiednich uprawnien")
    public Notice noPermission = Notice.builder()
            .chat("<red>Nie posiadasz odpowiednich uprawnien!")
            .build();

    @Comment("# Wiadomosc gdy chat jest juz wlaczony")
    public Notice chatAlreadyEnabled = Notice.builder()
            .chat("<red>Chat jest już włączony!")
            .build();

    @Comment("# Wiadomosc gdy chat jest juz wylaczony")
    public Notice chatAlreadyDisabled = Notice.builder()
            .chat("<red>Chat jest już wyłączony!")
            .build();

    @Comment("# Wiadomosc wysylana gdy włączamy chat")
    public Notice chatEnabled = Notice.builder()
            .chat("<green>Chat został włączony!")
            .build();

    @Comment("# Wiadomosc wysylana gdy wyłączamy chat")
    public Notice chatDisabled = Notice.builder()
            .chat("<red>Chat został wyłączony!")
            .build();

    @Comment("# Wiadomosc gdy gracz probuje wyslac wiadomosc na wylaczony chacie")
    public Notice chatIsDisabled = Notice.builder()
            .chat("<red>Chat aktualnie jest włączony!")
            .build();

    @Comment("# Wiadomosc gdy gracz probuje wyslac wiadomosc na chacie premium")
    public Notice chatOnlyPremium = Notice.builder()
            .chat("<red>Chat aktualnie jest włączony wyłącznie dla rang <gold>premium<red>!")
            .build();

    @Comment("# Wiadomosc gdy wlaczamy czat premium")
    public Notice chatPremiumEnabled = Notice.builder()
            .chat("<red>Wlaczono chat dla rang <gold>premium<red>!")
            .build();

    @Comment("# Wiadomosc wylaczajaca czat premium")
    public Notice chatPremiumDisabled = Notice.builder()
            .chat("<red>Wylaczono chat dla rang <gold>premium<red>!")
            .build();

    @Comment("# Wiadomosc gdy liczba jest ujemna")
    public Notice numberIsNegative = Notice.builder()
            .chat("<red>Liczba nie może być ujemna!")
            .build();

    @Comment("# Wiadomosc wylaczajaca tryb slowmode")
    public Notice chatSlowModeDisabled = Notice.builder()
            .chat("<green>Chat już nie posiada cooldownu!")
            .build();

    @Comment("# Wiadomosc wysylana gdy ustawilismy slowmode na X cooldown")
    public Notice chatSlowModeSet = Notice.builder()
            .chat("<green>Ustawiono tryb slowdown na <dark_green>{DURATION}")
            .build();

    @Comment("# Wiadomosc wysylana gdy gracz probuje napsiac wiaodmosc ale cooldown nie pozwala")
    public Notice slowModeCooldown = Notice.builder()
            .chat("<red>Chwila! Poczekaj jeszcze <dark_red>{DURATION}")
            .build();

}