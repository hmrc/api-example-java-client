package uk.gov.hmrc.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.AuthenticatedPrincipalServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OAuthClientConfiguration {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    return http.oauth2Client(Customizer.withDefaults()).build();
  }

  @Bean
  ServerOAuth2AuthorizedClientExchangeFilterFunction clientExchangeFunction(ReactiveClientRegistrationRepository clientRegistrations) {
    ReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
    
    ServerOAuth2AuthorizedClientRepository authClientRepository = new AuthenticatedPrincipalServerOAuth2AuthorizedClientRepository(clientService);

    ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider =
      ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
        .authorizationCode()
        .refreshToken()
        .clientCredentials()
        .build();

    AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);
		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
    
    ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2Client = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, authClientRepository);
    return oauth2Client;
  }

  @Bean
  WebClient webClient(ServerOAuth2AuthorizedClientExchangeFilterFunction oauth) {
    return WebClient.builder()
      .baseUrl("https://api.service.hmrc.gov.uk/hello")
      .filter(oauth)
      .build();
  }
}