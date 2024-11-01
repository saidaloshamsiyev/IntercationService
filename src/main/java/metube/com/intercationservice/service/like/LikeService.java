package metube.com.intercationservice.service.like;

import metube.com.intercationservice.domian.dto.request.LIkeReq;
import metube.com.intercationservice.domian.dto.response.LikeRes;
import metube.com.intercationservice.domian.dto.response.VideoResponse;
import metube.com.intercationservice.domian.entity.LikeEntity;

import java.util.List;
import java.util.UUID;

public interface LikeService {
    LikeRes create(LIkeReq lIkeReq);
    LikeRes findById(UUID id);
    void delete(UUID id);
    List<LikeRes> findAllByVideoId(UUID videoId);

    List<UUID> youLikeVideos(UUID id);
}
