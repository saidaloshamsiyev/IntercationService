package metube.com.intercationservice.service.commit;

import lombok.RequiredArgsConstructor;
import metube.com.intercationservice.clients.VideoServiceClient;
import metube.com.intercationservice.domian.dto.request.CommitReq;
import metube.com.intercationservice.domian.dto.response.CommitRes;
import metube.com.intercationservice.domian.dto.response.VideoResponse;
import metube.com.intercationservice.domian.entity.CommitEntity;
import metube.com.intercationservice.exception.BaseException;
import metube.com.intercationservice.repository.CommitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommitServiceImpl implements CommitService {
    private final CommitRepository commitRepository;
    private final VideoServiceClient videoServiceClient;
    @Override
    public CommitRes createCommit(CommitReq commitReq) {

        VideoResponse videoResponse = videoServiceClient.getVideo(commitReq.getVideoId());
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }

        CommitEntity build = CommitEntity.builder()
                .userId(commitReq.getUserId())
                .comment(commitReq.getComment())
                .videoId(commitReq.getVideoId())
                .build();
        CommitEntity save = commitRepository.save(build);

        return CommitRes.builder()
                        .id(save.getId())
                                .userId(save.getUserId())
                                        .comment(save.getComment())
                                                .videoId(save.getVideoId())
                                                        .likes(save.getLikes())
                .build();
    }

    @Override
    public CommitRes findById(UUID id) {
        CommitEntity commit = commitRepository.findById(id)
                .orElseThrow(() -> new BaseException("Commit not found", HttpStatus.NOT_FOUND.value()));

        //vedio tekshirish
        VideoResponse videoResponse = videoServiceClient.getVideo(commit.getVideoId());
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }

        return CommitRes.builder()
                .id(commit.getId())
                .userId(commit.getUserId())
                .comment(commit.getComment())
                .videoId(commit.getVideoId())
                .likes(commit.getLikes())
                .build();
    }

    @Override
    public List<CommitRes> findAll() {
        return commitRepository.findAllBy();
    }

    @Override
    public void updateCommit(UUID id, CommitReq commitReq) {
        CommitEntity commit = commitRepository.findById(id)
                .orElseThrow(() -> new BaseException("Commit not found", HttpStatus.NOT_FOUND.value()));

        //vedio tekshirish
        VideoResponse videoResponse = videoServiceClient.getVideo(commit.getVideoId());
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }

        commit.setComment(commitReq.getComment());

        commitRepository.save(commit);
    }

    @Override
    public void deleteCommit(UUID id) {
        CommitRes byId = findById(id);

        //vedio tekshirish
        VideoResponse videoResponse = videoServiceClient.getVideo(byId.getVideoId());
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }

        commitRepository.deleteById(id);
    }
}
