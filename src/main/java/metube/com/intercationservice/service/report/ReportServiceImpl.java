package metube.com.intercationservice.service.report;

import lombok.RequiredArgsConstructor;
import metube.com.intercationservice.domian.dto.request.ReportReq;
import metube.com.intercationservice.domian.dto.response.ReportRes;
import metube.com.intercationservice.domian.entity.ReportEntity;
import metube.com.intercationservice.exception.BaseException;
import metube.com.intercationservice.repository.ReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final ReportRepository reportRepository;

    @Override
    public ReportRes createReport(ReportReq reportReq) {
        ReportEntity build = ReportEntity.builder()
                .userId(reportReq.getUserId())
                .videoId(reportReq.getVideoId())
                .type(reportReq.getType())
                .build();
        ReportEntity save = reportRepository.save(build);
        return ReportRes.builder()
                .id(save.getId())
                .userId(save.getUserId())
                .videoId(save.getVideoId())
                .type(save.getType())
                .build();
    }

    @Override
    public ReportEntity findReportById(UUID id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new BaseException("Report not found", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public List<ReportRes> findAllReports() {
        return reportRepository.findAllBy();
    }
}