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
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/create")
    public ResponseEntity<LikeRes> create(@RequestBody LIkeReq like) {
        LikeRes like1 = likeService.create(like);
        return ResponseEntity.ok(like1);
    }

    @GetMapping("/findById{id}")
    public ResponseEntity<LikeRes> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(likeService.findById(id));
    }


    @GetMapping("/findAllLikeByVideoId{id}")
    public ResponseEntity<List<LikeRes>> findAllLikeByVideoId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(likeService.findAllByVideoId(id));
    }


    @DeleteMapping("/delete{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        likeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
