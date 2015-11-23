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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.hmrc.model.UnauthorizedException;

@Component
public class HelloWorldService {

    private final ServiceConnector serviceConnector;

    @Value("${serverToken}")
    private String serverToken;

    @Autowired
    public HelloWorldService(ServiceConnector serviceConnector) {
        this.serviceConnector = serviceConnector;
    }

    public String helloWorld() {
        try {
            return serviceConnector.get(
                    "https://api.service.hmrc.gov.uk/hello/world",
                    "application/vnd.hmrc.1.0+json",
                    Optional.absent());
        } catch (UnauthorizedException e) {
            throw new RuntimeException(e);
        }
    }

    public String helloApplication() throws UnauthorizedException {
        return serviceConnector.get(
                "https://api.service.hmrc.gov.uk/hello/application",
                "application/vnd.hmrc.1.0+json",
                Optional.of(serverToken));
    }

    public String helloUser(String accessToken) throws UnauthorizedException {
        return serviceConnector.get(
                "https://api.service.hmrc.gov.uk/hello/user",
                "application/vnd.hmrc.1.0+json",
                Optional.of(accessToken));
    }
}
