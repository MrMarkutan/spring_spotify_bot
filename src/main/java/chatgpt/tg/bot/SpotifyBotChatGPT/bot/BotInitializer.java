package chatgpt.tg.bot.SpotifyBotChatGPT.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class BotInitializer {
    private final SpotifyBot spotifyBot;

    public BotInitializer(SpotifyBot spotifyBot) {
        this.spotifyBot = spotifyBot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            telegramBotsApi.registerBot(spotifyBot);
            log.info("Bot was launched!");
        } catch (TelegramApiException e) {
            log.error("Error on registering bot + " + e.getMessage());
        }
    }
}
