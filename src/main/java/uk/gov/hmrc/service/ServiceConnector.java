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

import com.google.common.base.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import uk.gov.hmrc.model.UnauthorizedException;

import java.io.IOException;

@Component
public class ServiceConnector {

    private final HttpClient client;

    public ServiceConnector() {
        client = HttpClientBuilder.create().build();;
    }

    public String get(String url, String acceptHeader, Optional<String> bearerToken) throws UnauthorizedException {
        HttpGet request = new HttpGet(url);
        request.addHeader("Accept", acceptHeader);
        if (bearerToken.isPresent()) {
            request.addHeader("Authorization", "Bearer " + bearerToken.get());
        }

        try {
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == 401) {
                throw new UnauthorizedException();
            }
            return EntityUtils.toString(response.getEntity());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
