package mailservice.client;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "identity-service", url = "http://localhost:9001")
public interface UserClient {
}
