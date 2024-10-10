package metube.com.intercationservice.clients;

import metube.com.intercationservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "VIDEO-SERVICE",configuration = FeignConfig.class)
public interface VideoServiceClient {

}
