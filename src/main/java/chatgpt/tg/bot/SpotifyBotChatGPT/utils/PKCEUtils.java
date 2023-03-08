package chatgpt.tg.bot.SpotifyBotChatGPT.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class PKCEUtils {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generateCodeVerifier() {
        byte[] verifierBytes = new byte[64];
        SECURE_RANDOM.nextBytes(verifierBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(verifierBytes);
    }
}
