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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final VideoServiceClient videoServiceClient;

    @Override
    public LikeRes create(LIkeReq lIkeReq) {

        checkVideoId(lIkeReq.getVideoId());

        likeRepository.findByUserIdAndVideoId(lIkeReq.getUserId(), lIkeReq.getVideoId())
                .ifPresent(history -> {
                    throw new BaseException("You have already clicked like", HttpStatus.ALREADY_REPORTED.value());
                });
        LikeEntity build = LikeEntity.builder()
                .userId(lIkeReq.getUserId())
                .videoId(lIkeReq.getVideoId())
                .build();
        LikeEntity save = likeRepository.save(build);
        return mapToLikeRes(save);
    }

    @Override
    public LikeRes findById(UUID id) {
        LikeEntity like = likeRepository.findById(id)
                .orElseThrow(() -> new BaseException("You have not pushed like", HttpStatus.NOT_FOUND.value()));

        checkVideoId(like.getVideoId());

        return mapToLikeRes(like);
    }


    @Override
    public void delete(UUID id) {
        LikeRes byId = findById(id);

        checkVideoId(byId.getVideoId());

        likeRepository.deleteById(id);
    }

    @Override
    public List<LikeRes> findAllByVideoId(UUID videoId) {

        checkVideoId(videoId);

        List<LikeEntity> list = likeRepository.findAllByVideoId(videoId);

        return list.stream()
                .map(this::mapToLikeRes)
                .collect(Collectors.toList());
    }

    @Override
    public List<UUID> youLikeVideos(UUID userId) {
        List<LikeEntity> list = likeRepository.findAllByUserId(userId);
        List<UUID> videoIds = new ArrayList<>();

        for (LikeEntity likeEntity : list) {
            videoIds.add(likeEntity.getVideoId());
        }
        return videoIds;
    }


    private LikeRes mapToLikeRes(LikeEntity likeEntity) {
        LikeRes likeRes = new LikeRes();
        likeRes.setUserId(likeEntity.getUserId());
        likeRes.setVideoId(likeEntity.getVideoId());
        return likeRes;
    }

    private void checkVideoId(UUID videoId) {
        VideoResponse videoResponse = videoServiceClient.getVideo(videoId);
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }
    }
}
