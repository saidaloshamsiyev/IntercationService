package metube.com.intercationservice.controller;

import metube.com.intercationservice.domian.dto.request.HistoryReq;
import metube.com.intercationservice.domian.dto.response.HistoryRes;
import metube.com.intercationservice.service.history.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/history")
public class HistoryController {
    private HistoryService historyService;

    @PostMapping
    public ResponseEntity<HistoryRes> create(@RequestBody HistoryReq history) {
        HistoryRes history1 = historyService.createHistory(history);
        return ResponseEntity.ok(history1);
    }

    @GetMapping("{id}")
    public ResponseEntity<HistoryRes> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(historyService.findHistoryById(id));
    }


    @GetMapping
    public ResponseEntity<List<HistoryRes>> findAll() {
        return ResponseEntity.ok(historyService.findAllHistory());
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        historyService.deleteHistory(id);
        return ResponseEntity.noContent().build();
    }
}
