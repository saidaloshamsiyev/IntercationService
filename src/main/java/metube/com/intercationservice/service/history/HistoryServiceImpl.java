package metube.com.intercationservice.service.history;

import lombok.RequiredArgsConstructor;
import metube.com.intercationservice.clients.VideoServiceClient;
import metube.com.intercationservice.domian.dto.request.HistoryReq;
import metube.com.intercationservice.domian.dto.response.HistoryRes;
import metube.com.intercationservice.domian.dto.response.VideoResponse;
import metube.com.intercationservice.domian.entity.HistoryEntity;
import metube.com.intercationservice.exception.BaseException;
import metube.com.intercationservice.repository.HistoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private HistoryRepository historyRepository;
    private final VideoServiceClient videoServiceClient;

    @Override
    public HistoryRes createHistory(HistoryReq historyReq) {
        //vedio tekshirish
        VideoResponse videoResponse = videoServiceClient.getVideo(historyReq.getVideoId());
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }

        // Agar foydalanuvchi bu videoni oldin ko'rgan bo'lsa, exception tashlash
        historyRepository.findByUserIdAndVideoId(historyReq.getUserId(), historyReq.getVideoId())
                .ifPresent(history -> {
                    throw new BaseException("This video was previously archived", HttpStatus.ALREADY_REPORTED.value());
                });

        // Agar videoni oldin ko'rmagan bo'lsa, yangisini yaratib saqlash
        HistoryEntity newHistory = HistoryEntity.builder()
                .userId(historyReq.getUserId())
                .videoId(historyReq.getVideoId())
                .watchedTime(historyReq.getWatchedTime())
                .build();

        HistoryEntity save = historyRepository.save(newHistory);

        return HistoryRes.builder()
                .id(save.getId())
                .userId(save.getUserId())
                .videoId(save.getVideoId())
                .watchedTime(save.getWatchedTime())
                .build();
    }






    @Override
    public HistoryRes findHistoryById(UUID id) {
        HistoryEntity history = historyRepository.findById(id)
                .orElseThrow(() -> new BaseException("History not found", HttpStatus.NOT_FOUND.value()));

        //vedio tekshirish
        VideoResponse videoResponse = videoServiceClient.getVideo(history.getVideoId());
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }

         return HistoryRes.builder()
                 .id(history.getId())
                 .userId(history.getUserId())
                 .videoId(history.getVideoId())
                 .watchedTime(history.getWatchedTime())
                 .build();
    }

    @Override
    public List<HistoryRes> findAllHistory() {
        return historyRepository.findAllBy();
    }


    @Override
    public HistoryRes deleteHistory(UUID id) {
        HistoryRes historyById = findHistoryById(id);

        //vedio tekshirish
        VideoResponse videoResponse = videoServiceClient.getVideo(historyById.getVideoId());
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }

        historyRepository.deleteById(id);
        return historyById;
    }
}
