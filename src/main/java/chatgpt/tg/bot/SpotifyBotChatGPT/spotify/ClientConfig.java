package chatgpt.tg.bot.SpotifyBotChatGPT.spotify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class ClientConfig {
    @Value("${spotify.api.key}")
    String clientId;
    @Value("${spotify.api.secret}")
    String clientSecret;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
