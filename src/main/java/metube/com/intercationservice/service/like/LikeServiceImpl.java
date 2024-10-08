package metube.com.intercationservice.service.like;

import lombok.RequiredArgsConstructor;
import metube.com.intercationservice.repository.LikeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
}
