package chatgpt.tg.bot.SpotifyBotChatGPT.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class SpotifyBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final BotMenu menu;

    public SpotifyBot(BotConfig botConfig, BotMenu menu) {
        this.botConfig = botConfig;
        this.menu = menu;
        initBotCommandMenu();

    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }
    }

    private void handleMessage(Message message) {
        if (message.hasText()) {
            sendMessage(String.valueOf(message.getChatId()), message.getText());
        }
    }

    private void sendMessage(String chatId, String messageText) {
        send(menu.parseInput(chatId, messageText));
    }

    private void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void initBotCommandMenu() {
        try {
            this.execute(new SetMyCommands(menu.getBotCommandList(), new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error initializing bot`s command menu: " + e.getMessage());
        }

    }
}
