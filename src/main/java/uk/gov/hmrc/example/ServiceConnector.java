/*
 * Copyright 2015 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.example;

import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ServiceConnector {

  @Autowired
  private WebClient webClient;

  private final MediaType mt = MediaType.parseMediaType("application/vnd.hmrc.1.0+json");

  public Mono<String> helloWorld() {
    return webClient
      .get()
      .uri("/world")
      .accept(mt) 
      .retrieve()
      .bodyToMono(String.class);
  }

  public Mono<String> helloApplication() {
    return webClient
      .get()
      .uri(t -> t.path("/application").build())
      .accept(mt) 
      .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("app"))
      .retrieve()
      .bodyToMono(String.class);
  }

  public Mono<String> helloUser() {
    return webClient
      .get()
      .uri(t -> t.path("/user").build())
      .accept(mt) 
      .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("user"))
      .retrieve()
      .bodyToMono(String.class);
  }
}
