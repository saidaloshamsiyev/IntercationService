package metube.com.intercationservice.service.like;

import lombok.RequiredArgsConstructor;
import metube.com.intercationservice.clients.VideoServiceClient;
import metube.com.intercationservice.domian.dto.request.LIkeVideoReq;
import metube.com.intercationservice.domian.dto.request.LikeCommitReq;
import metube.com.intercationservice.domian.dto.response.LikeCommitRes;
import metube.com.intercationservice.domian.dto.response.LikeVideoRes;
import metube.com.intercationservice.domian.dto.response.VideoResponse;
import metube.com.intercationservice.domian.entity.LikeEntity;
import metube.com.intercationservice.exception.BaseException;
import metube.com.intercationservice.repository.LikeRepository;
import metube.com.intercationservice.service.commit.CommitServiceImpl;
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
    private final CommitServiceImpl commitService;

    @Override
    public LikeVideoRes createVideoLike(LIkeVideoReq lIkeVideoReq) {

        checkVideoId(lIkeVideoReq.getVideoId());

        likeRepository.findByUserIdAndVideoId(lIkeVideoReq.getUserId(), lIkeVideoReq.getVideoId())
                .ifPresent(history -> {
                    throw new BaseException("You have already clicked like", HttpStatus.ALREADY_REPORTED.value());
                });
        LikeEntity build = LikeEntity.builder()
                .userId(lIkeVideoReq.getUserId())
                .videoId(lIkeVideoReq.getVideoId())
                .build();
        LikeEntity save = likeRepository.save(build);
        return mapToLikeRes(save);
    }

    @Override
    public LikeCommitRes createCommitLike(LikeCommitReq likeCommitReq) {
        commitService.findById(likeCommitReq.getCommitId());


        likeRepository.findByUserIdAndVideoId(likeCommitReq.getUserId(), likeCommitReq.getCommitId())
                .ifPresent(history -> {
                    throw new BaseException("You have already clicked like", HttpStatus.ALREADY_REPORTED.value());
                });
        LikeEntity build = LikeEntity.builder()
                .userId(likeCommitReq.getUserId())
                .videoId(likeCommitReq.getCommitId())
                .build();
        LikeEntity save = likeRepository.save(build);

        return LikeCommitRes.builder()
                .commitId(save.getVideoId())
                .userId(save.getUserId())
                .build();
    }

    @Override
    public LikeVideoRes findById(UUID id) {
        LikeEntity like = likeRepository.findById(id)
                .orElseThrow(() -> new BaseException("You have not pushed like", HttpStatus.NOT_FOUND.value()));

        checkVideoId(like.getVideoId());

        return mapToLikeRes(like);
    }


    @Override
    public void delete(UUID id) {
        LikeVideoRes byId = findById(id);

        checkVideoId(byId.getVideoId());

        likeRepository.deleteById(id);
    }

    @Override
    public List<LikeVideoRes> findAllByVideoId(UUID videoId) {

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


    private LikeVideoRes mapToLikeRes(LikeEntity likeEntity) {
        LikeVideoRes likeVideoRes = new LikeVideoRes();
        likeVideoRes.setUserId(likeEntity.getUserId());
        likeVideoRes.setVideoId(likeEntity.getVideoId());
        return likeVideoRes;
    }

    private void checkVideoId(UUID videoId) {
        VideoResponse videoResponse = videoServiceClient.getVideo(videoId);
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }
    }
}
