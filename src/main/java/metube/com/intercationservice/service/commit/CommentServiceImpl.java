package metube.com.intercationservice.service.commit;

import lombok.RequiredArgsConstructor;
import metube.com.intercationservice.domian.dto.request.CommitReq;
import metube.com.intercationservice.domian.dto.response.CommitRes;
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
    @Override
    public CommitRes createCommit(CommitReq commitReq) {
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

        commit.setComment(commitReq.getComment());

        commitRepository.save(commit);
    }

    @Override
    public void deleteCommit(UUID id) {
        findById(id);
        commitRepository.deleteById(id);
    }
}
