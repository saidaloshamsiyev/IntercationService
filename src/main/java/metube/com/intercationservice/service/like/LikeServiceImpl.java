package metube.com.intercationservice.service.like;

import lombok.RequiredArgsConstructor;
import metube.com.intercationservice.clients.VideoServiceClient;
import metube.com.intercationservice.domian.dto.request.LIkeReq;
import metube.com.intercationservice.domian.dto.response.LikeRes;
import metube.com.intercationservice.domian.dto.response.VideoResponse;
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
    private final VideoServiceClient videoServiceClient;

    @Override
    public LikeRes create(LIkeReq lIkeReq) {

        //vedio tekshirish
        VideoResponse videoResponse = videoServiceClient.getVideo(lIkeReq.getVideoId());
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }

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
        LikeEntity like = likeRepository.findById(id)
                .orElseThrow(() -> new BaseException("You have not pushed like", HttpStatus.NOT_FOUND.value()));

        //vedio tekshirish
        VideoResponse videoResponse = videoServiceClient.getVideo(like.getVideoId());
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }

        return like;
    }

    @Override
    public List<LikeRes> findAll() {
        return likeRepository.findAllBy();
    }

    @Override
    public void delete(UUID id) {
        LikeEntity byId = findById(id);

        //vedio tekshirish
        VideoResponse videoResponse = videoServiceClient.getVideo(byId.getVideoId());
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }

        likeRepository.deleteById(id);
    }
}
