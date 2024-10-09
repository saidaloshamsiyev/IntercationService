package metube.com.intercationservice.controller;

import metube.com.intercationservice.domian.dto.request.ReportReq;
import metube.com.intercationservice.domian.dto.response.ReportRes;
import metube.com.intercationservice.domian.entity.ReportEntity;
import metube.com.intercationservice.service.report.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/report")
public class ReportController {

    private ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportRes> create(@RequestBody ReportReq report) {
        ReportRes report1 = reportService.createReport(report);
        return ResponseEntity.ok(report1);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReportEntity> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(reportService.findReportById(id));
    }


    @GetMapping
    public ResponseEntity<List<ReportRes>> findAll() {
        return ResponseEntity.ok(reportService.findAllReports());
    }

}
