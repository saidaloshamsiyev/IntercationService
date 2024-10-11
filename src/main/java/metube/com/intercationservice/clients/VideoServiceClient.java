package metube.com.intercationservice.clients;

import metube.com.intercationservice.config.FeignConfig;
import metube.com.intercationservice.domian.dto.response.VideoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "VIDEO-SERVICE",configuration = FeignConfig.class)
public interface VideoServiceClient {
    @GetMapping("/get-video/{id}")
    VideoResponse getVideo(@PathVariable UUID id);
}
