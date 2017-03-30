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

package uk.gov.hmrc.service;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.hmrc.model.Token;

@Component
public class OauthService {

    private final OAuthClient oAuthClient;

    public final String authorizeUrl;
    public final String tokenUrl;

    @Value("${clientId}")
    private String clientId;
    @Value("${clientSecret}")
    private String clientSecret;
    @Value("${callback.url}")
    private String callbackUrl;

    public OauthService() {
        this.oAuthClient = new OAuthClient(new URLConnectionClient());
        authorizeUrl = "https://api.service.hmrc.gov.uk/oauth/authorize";
        tokenUrl = "https://api.service.hmrc.gov.uk/oauth/token";
    }

    public Token getToken(String code) {
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(tokenUrl)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setRedirectURI(callbackUrl)
                    .setCode(code)
                    .buildBodyMessage();

            return fetchToken(request);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Token refreshToken(String refreshToken) {
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(tokenUrl)
                    .setGrantType(GrantType.REFRESH_TOKEN)
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setRefreshToken(refreshToken)
                    .buildBodyMessage();

            return fetchToken(request);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Token fetchToken(OAuthClientRequest tokenRequest) throws OAuthProblemException, OAuthSystemException {
        OAuthJSONAccessTokenResponse tokenResponse = oAuthClient.accessToken(tokenRequest);
        return new Token(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());
    }
}
