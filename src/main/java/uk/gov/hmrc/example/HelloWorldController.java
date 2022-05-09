package uk.gov.hmrc.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class HelloWorldController {

  @Autowired
  private ServiceConnector serviceConnector;

  @GetMapping("/hello-world")
  public Mono<String> world() {
    return serviceConnector.helloWorld();
  }

  @GetMapping("/hello-application")
  public Mono<String> application() {
    return serviceConnector.helloApplication();
  }
  
  @GetMapping("/hello-user")
  public Mono<String> user() {
    return serviceConnector.helloUser();
  }
}