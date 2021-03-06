package com.skybot.irc.config;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TwitchConfiguration {

    private final String TWITCH_CLIENT_ID_KEY = "twitch_client_id";
    private final String TWITCH_CLIENT_SECRET_KEY = "twitch_client_secret";
    private final String HELIX_CREDENTIALS_KEY = "helix";
    private final String HELIX_CREDENTIALS_REFRESH_KEY = "helix_refresh";

    @Autowired
    SkyBotProperties skyBotProperties;

    //TODO maybe make this session scoped and configured after a login
    @Bean
    public TwitchClient twitchClient() {
        log.info("Configuring twitch client.");

        TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();

        OAuth2Credential credential = new OAuth2Credential(
                "twitch",
                skyBotProperties.getCredentials().get(HELIX_CREDENTIALS_KEY)
        );

        return clientBuilder
                .withClientId(skyBotProperties.getApi().get(TWITCH_CLIENT_ID_KEY))
                .withClientSecret(skyBotProperties.getApi().get(TWITCH_CLIENT_SECRET_KEY))
                .withChatAccount(credential)
                .withEnableChat(true)
                .withEnableHelix(true)
                .build();
    }
}
