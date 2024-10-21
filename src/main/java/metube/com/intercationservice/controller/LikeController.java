package metube.com.intercationservice.controller;

import lombok.RequiredArgsConstructor;
import metube.com.intercationservice.domian.dto.request.LIkeReq;
import metube.com.intercationservice.domian.dto.response.LikeRes;
import metube.com.intercationservice.domian.entity.LikeEntity;
import metube.com.intercationservice.service.like.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeRes> create(@RequestBody LIkeReq like) {
        LikeRes like1 = likeService.create(like);
        return ResponseEntity.ok(like1);
    }

    @GetMapping("{id}")
    public ResponseEntity<LikeEntity> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(likeService.findById(id));
    }


    @GetMapping
    public ResponseEntity<List<LikeRes>> findAll() {
        return ResponseEntity.ok(likeService.findAll());
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        likeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
