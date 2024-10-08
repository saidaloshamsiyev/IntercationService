package metube.com.intercationservice.repository;

import metube.com.intercationservice.domian.dto.response.LikeRes;
import metube.com.intercationservice.domian.entity.HistoryEntity;
import metube.com.intercationservice.domian.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, UUID> {
    Optional<LikeEntity> findByUserIdAndVideoId(UUID userId, UUID videoId);

    List<LikeRes> findAllBy();
}
