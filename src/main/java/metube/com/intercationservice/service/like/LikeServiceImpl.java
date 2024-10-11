package metube.com.intercationservice.service.like;

import lombok.RequiredArgsConstructor;
import metube.com.intercationservice.domian.dto.request.LIkeReq;
import metube.com.intercationservice.domian.dto.response.LikeRes;
import metube.com.intercationservice.domian.entity.LikeEntity;
import metube.com.intercationservice.exception.BaseException;
import metube.com.intercationservice.repository.LikeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;

    @Override
    public LikeRes create(LIkeReq lIkeReq) {
        likeRepository.findByUserIdAndVideoId(lIkeReq.getUserId(), lIkeReq.getVideoId())
                .ifPresent(history -> {
                    throw new BaseException("You have already clicked like", HttpStatus.ALREADY_REPORTED.value());
                });
        LikeEntity build = LikeEntity.builder()
                .userId(lIkeReq.getUserId())
                .videoId(lIkeReq.getVideoId())
                .build();
        LikeEntity save = likeRepository.save(build);
        return LikeRes.builder()
                .id(save.getId())
                .userId(save.getUserId())
                .videoId(save.getVideoId())
                .build();
    }

    @Override
    public LikeEntity findById(UUID id) {
        return likeRepository.findById(id)
                .orElseThrow(() -> new BaseException("You have not clicked like", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public List<LikeRes> findAll() {
        return likeRepository.findAllBy();
    }

    @Override
    public void delete(UUID id) {
        findById(id);
        likeRepository.deleteById(id);
    }
}
