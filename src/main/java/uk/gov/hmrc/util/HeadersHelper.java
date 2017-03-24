/*
 * Copyright 2017 HM Revenue & Customs
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

package uk.gov.hmrc.util;

import org.apache.http.client.methods.HttpGet;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;

import java.util.UUID;

public class HeadersHelper {

    public static void setHeaders(OAuthClientRequest request) {
        request.addHeader("X-Request-ID", "govuk-tax-" + UUID.randomUUID().toString());
        request.addHeader("User-Agent", "api-example-java-client");
    }

    public static void setHeaders(HttpGet request) {
        request.addHeader("X-Request-ID", "govuk-tax-" + UUID.randomUUID().toString());
        request.addHeader("User-Agent", "api-example-java-client");
    }
}
