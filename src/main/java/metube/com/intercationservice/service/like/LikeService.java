package metube.com.intercationservice.service.like;

import metube.com.intercationservice.domian.dto.request.LIkeReq;
import metube.com.intercationservice.domian.dto.response.LikeRes;
import metube.com.intercationservice.domian.entity.LikeEntity;

import java.util.List;
import java.util.UUID;

public interface LikeService {
    LikeRes create(LIkeReq lIkeReq);
    LikeEntity findById(UUID id);
    List<LikeRes> findAll();
    void delete(UUID id);
}
