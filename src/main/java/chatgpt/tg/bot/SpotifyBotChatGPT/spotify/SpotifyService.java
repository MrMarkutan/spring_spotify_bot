package chatgpt.tg.bot.SpotifyBotChatGPT.spotify;

import chatgpt.tg.bot.SpotifyBotChatGPT.spotify.auth.SpotifyAccessTokenProvider;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.browse.GetCategoryRequest;
import se.michaelthelin.spotify.requests.data.browse.GetCategorysPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SpotifyService {
    private SpotifyApi spotifyApi;

    private final SpotifyAccessTokenProvider accessTokenProvider;

    public SpotifyService(SpotifyAccessTokenProvider accessTokenProvider) {
        this.accessTokenProvider = accessTokenProvider;
    }

    @PostConstruct
    public void init() {
        this.spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessTokenProvider.getAccessToken())
                .build();
    }

    public List<Track> findTracks(String trackName) {
        SearchTracksRequest tracksRequest = spotifyApi.searchTracks(trackName).build();
        List<Track> trackList = new ArrayList<>();
        try {
            Track[] tracks = tracksRequest.execute().getItems();
            for (Track track : tracks) {
                if (track.getName().contains(trackName)) {
                    trackList.add(track);
                }
            }
            log.info("Tracks found - " + trackList.size());

            return trackList;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Track Request has failed: " + e.getMessage());
        }
        return trackList;
    }
}
