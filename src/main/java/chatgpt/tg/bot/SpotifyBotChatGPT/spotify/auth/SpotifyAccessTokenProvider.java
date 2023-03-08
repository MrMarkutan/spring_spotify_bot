package chatgpt.tg.bot.SpotifyBotChatGPT.spotify.auth;

import chatgpt.tg.bot.SpotifyBotChatGPT.spotify.ClientConfig;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

@Slf4j
@Component
public class SpotifyAccessTokenProvider {
    private final ClientConfig clientConfig;
    private SpotifyApi spotifyApi;

    public SpotifyAccessTokenProvider(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    @PostConstruct
    void init() {
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientConfig.getClientId())
                .setClientSecret(clientConfig.getClientSecret())
                .build();
    }

    public String getAccessToken() {
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        ClientCredentials clientCredentials = null;
        try {
            clientCredentials = clientCredentialsRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
        }
        return clientCredentials.getAccessToken();
    }
}
