package metube.com.intercationservice.service.report;

import lombok.RequiredArgsConstructor;
import metube.com.intercationservice.clients.VideoServiceClient;
import metube.com.intercationservice.domian.dto.request.ReportReq;
import metube.com.intercationservice.domian.dto.response.ReportRes;
import metube.com.intercationservice.domian.dto.response.VideoResponse;
import metube.com.intercationservice.domian.entity.ReportEntity;
import metube.com.intercationservice.exception.BaseException;
import metube.com.intercationservice.repository.ReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final ReportRepository reportRepository;
    private final VideoServiceClient videoServiceClient;

    @Override
    public ReportRes createReport(ReportReq reportReq) {

        //vedio tekshirish
        VideoResponse videoResponse = videoServiceClient.getVideo(reportReq.getVideoId());
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }

        ReportEntity build = ReportEntity.builder()
                .userId(reportReq.getUserId())
                .videoId(reportReq.getVideoId())
                .type(reportReq.getType())
                .build();
        ReportEntity save = reportRepository.save(build);
        return mapToReportRes(save);
    }

    @Override
    public ReportEntity findReportById(UUID id) {
        ReportEntity reportNotFound = reportRepository.findById(id)
                .orElseThrow(() -> new BaseException("Report not found", HttpStatus.NOT_FOUND.value()));

        //vedio tekshirish
        VideoResponse videoResponse = videoServiceClient.getVideo(reportNotFound.getVideoId());
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }

        return reportNotFound;
    }

    @Override
    public List<ReportRes> findAllByRepostsByVideoId(UUID videoId) {
        //vedio tekshirish
        VideoResponse videoResponse = videoServiceClient.getVideo(videoId);
        if (videoResponse == null) {
            throw new BaseException("Video not found", HttpStatus.NOT_FOUND.value());
        }

        List<ReportEntity> list = reportRepository.findAllByVideoId(videoId);
        return list.stream()
                .map(this::mapToReportRes)
                .collect(Collectors.toList());
    }



    private ReportRes mapToReportRes(ReportEntity reportEntity) {
        ReportRes reportRes = new ReportRes();
        reportRes.setUserId(reportEntity.getUserId());
        reportRes.setVideoId(reportEntity.getVideoId());
        reportRes.setType(reportEntity.getType());
        return reportRes;
    }

}