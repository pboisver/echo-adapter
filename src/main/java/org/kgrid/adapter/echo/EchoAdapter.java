package org.kgrid.adapter.echo;


import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.kgrid.adapter.api.ActivationContext;
import org.kgrid.adapter.api.Adapter;
import org.kgrid.adapter.api.Executor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class EchoAdapter implements Adapter {

  private final String status = "UP";
  private ActivationContext context;

  RestTemplate restTemplate = new RestTemplateBuilder().build();

  @GetMapping("/echo-adapter")
  public String whoAmI() {
    return this.getClass().getCanonicalName();
  }

  @PostMapping("/echo-adapter")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<String> whatsThat(HttpServletRequest request) {
    URI local = URI.create(request.getRequestURL().toString());
    final URI echoer = local.resolve("/echo-adapter");
    ResponseEntity<String> response = restTemplate
        .getForEntity(echoer, String.class);
    return response;
  }

  @Override
  public List<String> getEngines() {
    return List.of("python", "node", "echo");
  }

  @Override
  public void initialize(ActivationContext context) {
    this.context = context;
  }

  @Override
  public Executor activate(URI uri, URI uri1, JsonNode jsonNode) {
    return new Executor() {
      @Override
      public Object execute(Object o, String s) {
        return o;
      }
    };
  }

  @Override
  public String status() {
    return this.status;
  }
}
