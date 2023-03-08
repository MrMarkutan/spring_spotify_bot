package chatgpt.tg.bot.SpotifyBotChatGPT.bot;

import chatgpt.tg.bot.SpotifyBotChatGPT.spotify.SpotifyService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BotMenu {

    private static final String START_COMMAND = "/start";
    private static final String HELP_COMMAND = "/help";
    private static final String SEARCH_COMMAND = "/search";
    private final List<BotCommand> botCommandList;
    private final SpotifyService spotifyService;

    public BotMenu(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
        botCommandList = createMenu();
    }

    public List<BotCommand> getBotCommandList() {
        return botCommandList;
    }

    private List<BotCommand> createMenu() {
        List<BotCommand> list = new ArrayList<>();
        list.add(new BotCommand(START_COMMAND, "Get a welcome message."));
        list.add(new BotCommand(HELP_COMMAND, "Show this message."));
        list.add(new BotCommand(SEARCH_COMMAND, "Enter command and name of the song you want to find."));
        return list;
    }

    public SendMessage parseInput(String chatId, String messageText) {
        SendMessage replyMessage = new SendMessage();
        replyMessage.setChatId(chatId);

//        replyMessage.setReplyMarkup(inline);


        if (messageText.startsWith(START_COMMAND)) {
            replyMessage.setText("Welcome to SpotifyBot! This bot allows you to search for tracks on Spotify." +
                    " To search for a track, simply send a message with the name of the track you're looking for.");
        } else if (messageText.startsWith(HELP_COMMAND)) {
            replyMessage.setText("To search for a track, simply send a message with the " +
                    "name of the track you're looking for.");
        } else if (messageText.startsWith(SEARCH_COMMAND)) {
            if (messageText.contains(" ")) {
                searchTracks(replyMessage, messageText.substring(messageText.indexOf(" ")).trim());
            }
            else {
                replyMessage.setText("Enter: /search <track_name>. (Case sensitive).");
            }
        } else {
            replyMessage.setText("Unknown command");
        }
        return replyMessage;
    }

    private void searchTracks(SendMessage message, String trackName) {
        List<Track> tracks = spotifyService.findTracks(trackName);
        if (tracks.isEmpty()) {
            message.setText("Couldn't find any track with this name. Try to search another name.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Track track : tracks) {
                sb.append(track.getName())
                        .append(" - ")
                        .append(Arrays.stream(track.getArtists())
                                .map(ArtistSimplified::getName)
                                .collect(Collectors.toList()))
                        .append("\n")
                        .append(track.getExternalUrls().get("spotify"))
                        .append("\n");
            }
            message.setText(sb.toString());
        }
    }
}
